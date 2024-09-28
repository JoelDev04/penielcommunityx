package id.jostudios.penielcommunityx.presentation.activities.members_activity

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import id.jostudios.penielcommunityx.domain.use_case.get_members.GetMembersUseCase
import id.jostudios.penielcommunityx.domain.use_case.get_profile_picture.GetProfilePictureUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val storageRepo: StorageRepository,
    private val getMembersUseCase: GetMembersUseCase
): ViewModel() {

    private val _state: MutableState<MemberState> = mutableStateOf(MemberState());
    val state: State<MemberState> = _state;

    init {
        fetchMembers();
    }

    fun closeError() {
        _state.value = _state.value.copy(
            error = null
        );
    }

    private fun fetchMembers() {
        getMembersUseCase().onEach { res ->
            when(res) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        members = res.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = res.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope);
    }

    public suspend fun fetchUserProfile(name: String): Uri? {
        try {
            return storageRepo.getProfilePicture(name);
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.localizedMessage,
                isLoading = false
            )
            return null;
        }
    }
}