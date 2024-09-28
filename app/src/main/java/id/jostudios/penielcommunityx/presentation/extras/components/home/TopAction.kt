package id.jostudios.penielcommunityx.presentation.extras.components.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.jostudios.penielcommunityx.presentation.LoginActivity
import id.jostudios.penielcommunityx.presentation.MainActivity
import id.jostudios.penielcommunityx.presentation.screens.home_screen.HomeViewModel

@Composable
fun TopAction(
    activity: MainActivity,
    viewModel: HomeViewModel
) {
    Row {
        Icon(Icons.Default.Notifications, contentDescription = "notification", tint = Color.White,
            modifier = Modifier
                .clickable {
                    Toast.makeText(activity, "Checking notification", Toast.LENGTH_LONG).show();
                }
        );
        Spacer(modifier = Modifier.width(10.dp));
        Icon(Icons.Default.ExitToApp, contentDescription = "exit", tint = Color.White,
            modifier = Modifier
                .clickable {
                    Toast.makeText(activity, "Logging out", Toast.LENGTH_LONG).show();

                    viewModel.signOut();

                    val loginActivity = Intent(activity, LoginActivity::class.java);
                    loginActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK;
                    activity.startActivity(loginActivity);
                    activity.finish();
                }
        );
    }
}