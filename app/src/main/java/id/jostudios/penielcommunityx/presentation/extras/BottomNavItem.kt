package id.jostudios.penielcommunityx.presentation.extras

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val text: String, val icon: ImageVector) {
    object homeNav: BottomNavItem("/home_screen", "Home",
        Icons.Default.Home
    );
    object feedNav: BottomNavItem("/feed_screen", "Feed",
        Icons.Default.Star
    );
    object settinNav: BottomNavItem("/setting_screen", "Settings",
        Icons.Default.Settings
    );
}