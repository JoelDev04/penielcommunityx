package id.jostudios.penielcommunityx.presentation.action_activities.change_avatar

import android.net.Uri

data class ChangeAvatarState(
    val currentAvatarUri: Uri? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
);
