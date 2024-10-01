package id.jostudios.penielcommunityx.presentation.screens.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.listeners.interfaces.UserChangedListenter
import id.jostudios.penielcommunityx.domain.listeners.remotes.UserChangedRemote
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.use_case.get_banner.GetBannerUseCase
import id.jostudios.penielcommunityx.domain.use_case.get_profile_picture.GetProfilePictureUseCase
import id.jostudios.penielcommunityx.domain.use_case.get_version.GetVersionUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository,
    private val authRepo: AuthRepository,

    private val dbRemoteListener: UserChangedRemote,

    // Use cases
    private val getUserProfilePictureUseCase: GetProfilePictureUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getVersionUseCase: GetVersionUseCase
): ViewModel() {

    private val _state: MutableState<HomeState> = mutableStateOf(HomeState());
    public val state: State<HomeState> = _state;

    init {
        viewModelScope.launch {
            checkAppVersion();
            fetchUser();
            fetchBanner();
        }
    }

    fun closeError() {
        _state.value = _state.value.copy(
            error = null
        );
    }

    private fun checkAppVersion() {
        getVersionUseCase().onEach { res ->
            when(res) {
                is Resource.Success -> {
                    Log.d("CheckAppVersion", "Global App Version : ${res.data}");
                    Log.d("CheckAppVersion", "Local App Version : ${States.APP_VERSION}");

                    if (res.data != States.APP_VERSION) {
                        _state.value = _state.value.copy(
                            error = "Invalid app version! Please update your app to the latest version.",
                            isValidVersion = false,
                            isLoading = false
                        );
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "There's an error while fetching app version! \nError : ${res.message}"
                    );
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    );
                }
            }
        }.launchIn(viewModelScope);
    }

    private fun fetchBanner() {
        viewModelScope.launch {
            try {
                val currentBanner = dbRepo.getCurrentBanner().replace("\"", "");
                Log.d("HomeViewModel", "Banner name : $currentBanner");
                fetchBannerUri(currentBanner);

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error!"
                );
            }
        }
    }

    private fun fetchBannerUri(bannerName: String) {
        getBannerUseCase(bannerName).onEach { res ->
            when(res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    );
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message
                    );
                    Log.d("HomeViewModel", "Error Banner : ${res.message}")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        bannerUri = res.data,
                        isLoading = false
                    );
                }
            }
        }.launchIn(viewModelScope);
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            );

            try {
                val user = dbRepo.getUserById(authRepo.getAuth().currentUser?.uid!!);

                if (States.isDevelopment) {
                    user.debugPermission();
                }

                _state.value = _state.value.copy(
                    displayName = user.displayName,
                    profilePicture = user.profilePicture,
                    isLoading = false
                );

                States.permissions.value = user.permissions;
                user.permissions = "0";
                States.currentUser.value = user;

                Log.d("HomeViewModel", "State Perms : ${States.permissions.value}");
                Log.d("HomeViewModel", "User Perms : ${user.permissions}");

                fetchUserProfilePicture();
                listener(user.id);
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error!"
                );
            }
        }
    }

    private fun listener(userID: String) {
        dbRemoteListener(userID, object : UserChangedListenter {
            override fun onUserPermissionsChanged(data: String) {
                States.permissions.value = data;

                val decrypted = PermissionManager.decryptPermission();

                Log.d("HomeViewModel", "State Perms : ${States.permissions.value}");
                Log.d("HomeViewModel", "Decrypted Perms : ${decrypted}");
                Log.d("HomeViewModel", "Raw Perms : ${PermissionManager.getRawPermissions(decrypted)}");
            }

            override fun onUserGroupChanged(data: List<GroupsEnum>) {
                States.currentUser.value = States.currentUser.value?.copy(
                    groups = data
                );
            }

            override fun onUserNameChanged(data: String) {
                States.currentUser.value = States.currentUser.value?.copy(
                    displayName = data
                );
                _state.value = _state.value.copy(
                    displayName = data
                )
            }

            override fun onUserProfileChanged(data: String) {
                States.currentUser.value = States.currentUser.value?.copy(
                    profilePicture = data
                );
                _state.value = _state.value.copy(
                    profilePicture = data
                )
                fetchUserProfilePicture();
            }
        });
    }

    private fun fetchUserProfilePicture() {
        getUserProfilePictureUseCase(state.value.profilePicture).onEach { res ->
            when(res) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    );
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message
                    );
                    Log.d("HomeViewModel", "Error User : ${res.message}")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        profileUri = res.data
                    )
                }
            }

        }.launchIn(viewModelScope);
    }

    public fun signOut() {
        viewModelScope.launch {
            authRepo.signOut();
        }
    }
}