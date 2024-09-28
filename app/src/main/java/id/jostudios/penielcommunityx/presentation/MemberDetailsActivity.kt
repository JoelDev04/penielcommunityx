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
import id.jostudios.penielcommunityx.presentation.activities.details_activity.DetailsActivity
import id.jostudios.penielcommunityx.presentation.activities.details_activity.DetailsViewModel
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class MemberDetailsActivity : ComponentActivity() {

    private val viewModel: DetailsViewModel by viewModels();
    private var userID: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userID = intent.getStringExtra("user_id");

        viewModel.setUserID(userID);
        viewModel.fetchUser();

        setContent {
            PenielCommunityXTheme(window = window) {
                if (userID == null) {
                    Scaffold { padding ->
                        val pad = padding;
                        Text(text = "Error! Member ID not valid.");
                    }
                } else {
                    Scaffold { padding ->
                        val pad = padding;
                        Application();
                    }
                }
            }
        }
    }

    @Composable
    fun Application() {
        DetailsActivity(viewModel);
    }
}

