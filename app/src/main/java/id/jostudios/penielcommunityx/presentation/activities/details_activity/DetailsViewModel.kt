package id.jostudios.penielcommunityx.presentation.activities.details_activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.use_case.get_member.GetMemberUseCase
import id.jostudios.penielcommunityx.domain.use_case.get_profile_picture.GetProfilePictureUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMemberUseCase: GetMemberUseCase,
    private val getProfilePictureUseCase: GetProfilePictureUseCase
): ViewModel() {
    private var _state: MutableState<DetailsState> = mutableStateOf(DetailsState());
    public var state: State<DetailsState> = _state;

    fun closeError() {
        _state.value = _state.value.copy(
            error = null
        )
    }

    fun setUserID(value: String?) {
        _state.value = _state.value.copy(
            userID = value
        );
    }

    fun fetchUser() {
        if (_state.value.userID == null) {
            _state.value = _state.value.copy(
                error = "No valid id!"
            );
            return;
        }

        getMemberUseCase(_state.value.userID!!).onEach { res ->
            when(res) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        userModel = res.data
                    );

                    fetchUserProfile();
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message
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

    private fun fetchUserProfile() {
        getProfilePictureUseCase(_state.value.userModel?.profilePicture!!).onEach { res ->
            when(res) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        profileUri = res.data
                    );
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = res.message
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
}