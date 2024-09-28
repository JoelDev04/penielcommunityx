package id.jostudios.penielcommunityx.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.presentation.activities.cheat_activity.CheatActivity
import id.jostudios.penielcommunityx.presentation.activities.cheat_activity.CheatViewModel
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class CheatActivity : ComponentActivity() {

    private val viewModel: CheatViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenielCommunityXTheme(window = window) {
                Application();
            }
        }
    }

    @Composable
    fun Application() {
        Scaffold { paddingValues ->
            val padding = paddingValues
            CheatActivity(viewModel);
        }
    }
}