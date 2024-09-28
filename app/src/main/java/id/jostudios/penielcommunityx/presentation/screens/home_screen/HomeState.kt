package id.jostudios.penielcommunityx.presentation.screens.home_screen

import android.net.Uri

data class HomeState(
    val isValidVersion: Boolean = true,
    val displayName: String = "",
    val profilePicture: String = "",
    val profileUri: Uri? = null,
    val bannerUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
