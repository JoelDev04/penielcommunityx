package id.jostudios.penielcommunityx.presentation.action_activities.change_avatar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import id.jostudios.penielcommunityx.presentation.ui.theme.PenielCommunityXTheme

class ChangeAvatar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PenielCommunityXTheme(
                window = window
            ) {
                Text("Change Avatar");
            }
        }
    }
}