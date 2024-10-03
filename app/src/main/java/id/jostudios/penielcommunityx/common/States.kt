package id.jostudios.penielcommunityx.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import id.jostudios.penielcommunityx.domain.model.UserModel

object States {
    public const val APP_VERSION = "24.08.1";
    public var permissions: MutableState<String?> = mutableStateOf(null);

    public const val isDevelopment: Boolean = true; // Adding logging capabilities (Create a custom log global function)
    public val isCheatApp: Boolean = true; // For easier access and debugging process!

    public var currentUser: MutableState<UserModel?> = mutableStateOf(null);
}