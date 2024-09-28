package id.jostudios.penielcommunityx.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import id.jostudios.penielcommunityx.PenielCommunityApp
import id.jostudios.penielcommunityx.R
import id.jostudios.penielcommunityx.presentation.activities.login_activity.LoginActivity
import id.jostudios.penielcommunityx.presentation.activities.login_activity.LoginViewModel
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenielCommunityXTheme(window = window) {
                Scaffold { padding ->
                    val pad = padding;
                    Application();
                }
            }
        }
    }

    @Composable
    private fun Application() {
        LoginActivity(this, loginViewModel);
    }
}