package id.jostudios.penielcommunityx.presentation.extras

sealed class Screen(val route: String) {
    object homeScreen: Screen("/home_screen");
    object feedScreen: Screen("/feed_screen");
    object settingScreen: Screen("/setting_screen");
}