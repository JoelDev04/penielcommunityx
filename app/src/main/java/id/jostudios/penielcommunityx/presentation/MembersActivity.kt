package id.jostudios.penielcommunityx.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import id.jostudios.penielcommunityx.presentation.activities.members_activity.MembersActivity
import id.jostudios.penielcommunityx.presentation.activities.members_activity.MembersViewModel
import id.jostudios.penielcommunityx.presentation.screens.home_screen.HomeViewModel
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

@AndroidEntryPoint
class MembersActivity: ComponentActivity() {

    private val viewmodel: MembersViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
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
    fun Application() {
        MembersActivity(viewmodel);
    }
}