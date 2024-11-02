package id.jostudios.penielcommunityx.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.initialize
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.presentation.activities.login_activity.LoginViewModel
import id.jostudios.penielcommunityx.presentation.extras.BottomNavItem
import id.jostudios.penielcommunityx.presentation.extras.Screen
import id.jostudios.penielcommunityx.presentation.screens.feed_screen.FeedScreen
import id.jostudios.penielcommunityx.presentation.screens.home_screen.HomeScreen
import id.jostudios.penielcommunityx.presentation.screens.home_screen.HomeViewModel
import id.jostudios.penielcommunityx.presentation.screens.setting_screen.SettingScreen
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme
import id.jostudios.penielcommunityx.presentation.ui.theme.matchaSage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels();

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        checkAuth();
        checkPerms();

        setContent {
            PenielCommunityXTheme(window = window) {
                Application();
            }
        }
        Log.d("MainActivity", "Permission debug : ${PermissionsEnum.UploadFeed}");
    }

    private fun checkAuth() {
        FirebaseApp.initializeApp(this);

        var user = Firebase.auth.currentUser;

        if (user == null) {
            Log.d("MainActivity", "User not found! Please login.");

            val loginActivity = Intent(applicationContext, LoginActivity::class.java);
            startActivity(loginActivity);
            finish();

            return;
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPerms() {
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            val perms = arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.ACCESS_MEDIA_LOCATION)
            ActivityCompat.requestPermissions(this@MainActivity, perms, 123);
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123) {
            Log.d("PermissionSystem", "Permissions : ${permissions[0].toString()}");
            Log.d("PermissionSystem", "Permissions : ${permissions[1].toString()}");
            Log.d("PermissionSystem", "Permissions : ${grantResults[0].toString()}");
            Log.d("PermissionSystem", "Permissions : ${grantResults[1].toString()}");
        }
    }

    @Composable
    private fun Application() {
        val navState = rememberNavController();
        val navBackState = navState.currentBackStackEntryAsState();
        val navRoute = navBackState.value?.destination?.route;

        Scaffold(bottomBar = {
            if (navRoute != null && !navRoute.contains("/page")) {
                BottomBar(navState);
            }
        }) { innerPadding ->
            Navigation(Modifier.padding(innerPadding), navState);
        }
    }

    @Composable
    private fun BottomBar(navController: NavHostController) {
        BottomNavigation(backgroundColor = matchaSage) {
            val navBackState = navController.currentBackStackEntryAsState();
            val navRoute = navBackState.value?.destination?.route;

            val bottomNavItems = listOf(
                BottomNavItem.feedNav,
                BottomNavItem.homeNav,
                BottomNavItem.settinNav
            )

            bottomNavItems.forEach { item ->
                BottomNavigationItem(
                    selected = item.route == navRoute,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(item.route) {}
                    },
                    icon = {
                        Icon(item.icon, contentDescription = item.text, tint = Color.White);
                    },
                    label = {
                        Text(text = item.text, color = Color.White)
                    }
                );
            }
        }
    }

    @Composable
    private fun Navigation(modifier: Modifier, navController: NavHostController) {
        val density = LocalDensity.current;

        NavHost(navController = navController, startDestination = Screen.homeScreen.route,
            enterTransition = {
                slideInVertically {
                    with(density) {
                        -1000.dp.roundToPx();
                    }
                }
            },
            exitTransition = {
                slideOutVertically {
                    with(density) {
                        1000.dp.roundToPx();
                    }
                }
            }
        ) {
            // Main Screens
            composable(Screen.feedScreen.route) {
                FeedScreen();
            }
            composable(Screen.homeScreen.route) {
                HomeScreen(this@MainActivity, homeViewModel);
            }
            composable(Screen.settingScreen.route) {
                SettingScreen();
            }
        }
    }
}