package id.jostudios.penielcommunityx.presentation.activities.login_activity

data class LoginState(
    var username: String = "",
    var password: String = "",
    var error: String? = null,
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false
)