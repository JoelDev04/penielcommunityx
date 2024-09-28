package id.jostudios.penielcommunityx.presentation.activities.login_activity

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.use_case.login.LoginUseCase
import id.jostudios.penielcommunityx.domain.use_case.signup.SignupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _state: MutableState<LoginState> = mutableStateOf(LoginState());
    public val state: State<LoginState> = _state;

    public fun setUsername(value: String) {
        _state.value = _state.value.copy(username = value.toLowerCase(Locale.ROOT));
    }

    public fun setPassword(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    public fun login() {
        val values = state.value;
        loginUseCase(values.username, values.password).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false);
                    _state.value = _state.value.copy(isSuccess = true);
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = res.message ?: "Unexpected error!"
                    );

                    withContext(Dispatchers.IO) {
                        Thread.sleep(5000);

                        _state.value = _state.value.copy(
                            error = null
                        );
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    );
                }
            }
        }.launchIn(viewModelScope)
    }

    public fun signup() {
        val values = state.value;
        signupUseCase(values.username, values.password).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false);
                    Log.d("Signup", "Create account success! ${res.data}");
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = res.message ?: "Unexpected error!"
                    );

                    withContext(Dispatchers.IO) {
                        Thread.sleep(5000);

                        _state.value = _state.value.copy(
                            error = null
                        );
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    );
                }
            }
        }.launchIn(viewModelScope)
    }
}