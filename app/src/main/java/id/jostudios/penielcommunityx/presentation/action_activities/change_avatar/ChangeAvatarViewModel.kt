package id.jostudios.penielcommunityx.presentation.action_activities.change_avatar

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.ContentManager
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChangeAvatarViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository,
    private val storageRepo: StorageRepository
): ViewModel() {
    private var _state: MutableState<ChangeAvatarState> = mutableStateOf(ChangeAvatarState());

    var state: State<ChangeAvatarState> = _state;

    fun loadAvatar() {
        viewModelScope.launch {
            loadingState(true);

            val profilePicture = States.currentUser.value?.profilePicture!!;
            val avatarUri = storageRepo.getProfilePicture(profilePicture);

            _state.value = _state.value.copy(
                currentAvatarUri = avatarUri
            );

            loadingState(false);
        }
    }

    fun setCurrentUri(value: Uri) {
        _state.value = _state.value.copy(
            currentAvatarUri = value
        );
    }

    fun removeAvatar() {
        viewModelScope.launch {
            loadingState(true);

            val blankProfileUri = storageRepo.getProfilePicture("blank.png");
            dbRepo.updateProfilePicture(States.currentUser.value?.id!!, "blank.png");

            setCurrentUri(blankProfileUri!!);

            loadingState(false);
        }
    }

    fun uploadAvatar(context: Context, imgUri: Uri) {
        viewModelScope.launch {
            loadingState(true);

            val imgFile = ContentManager.fileFromContentUri(context, imgUri);
            val imgName =
                "Profile_${States.currentUser.value?.name}_${imgFile.name}";

            val newAvatarUri = storageRepo.postProfilePicture(imgUri, imgName);
            dbRepo.updateProfilePicture(States.currentUser.value?.id!!, imgName);

            setCurrentUri(newAvatarUri!!);

            loadingState(false);
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(
            isError = null
        );
    }

    private fun loadingState(isLoading: Boolean) {
        _state.value = _state.value.copy(
            isLoading = isLoading
        );
    }
}