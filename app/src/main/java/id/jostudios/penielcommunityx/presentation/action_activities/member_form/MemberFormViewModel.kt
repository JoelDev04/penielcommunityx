package id.jostudios.penielcommunityx.presentation.action_activities.member_form

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberFormViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository
): ViewModel() {
    private val _state: MutableState<MemberFormState> = mutableStateOf(MemberFormState());
    private var _uid: String = "";

    public var state: State<MemberFormState> = _state;

    public fun loadUser(uid: String) {
        try {
            viewModelScope.launch {
                _uid = uid;

                if (States.currentUser.value?.id == uid) {
                    Log.d("MemberForm", "Loading Current User!");

                    _state.value = _state.value.copy(
                        currentUser = States.currentUser.value
                    );

                } else {
                    Log.d("MemberForm", "Loading Other User!");
                    val user = dbRepo.getUserById(uid);

                    _state.value = _state.value.copy(
                        currentUser = user
                    );
                }
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.localizedMessage ?: "Unknown error. Please contact developer team!"
            );
        }
    }

    public fun setDisplayName(value: String) {
        _state.value = _state.value.copy(
            displayName = value
        );
    }

    public fun setEmail(value: String) {
        _state.value = _state.value.copy(
            email = value
        );
    }

    public fun setPhoneNumber(value: String) {
        _state.value = _state.value.copy(
            phoneNumber = value
        );
    }

    public fun setBirthDate(value: Long) {
        _state.value = _state.value.copy(
            birthDate = value
        );
    }

    public fun updateDetails() {
        viewModelScope.launch {
            if (_uid != "") {
                // Save non-null values!

                if (_state.value.displayName != null)
                    dbRepo.updateDisplayName(_uid, _state.value.phoneNumber!!);

                if (_state.value.phoneNumber != null)
                    dbRepo.updatePhoneNumber(_uid, _state.value.phoneNumber!!);

                if (_state.value.email != null)
                    dbRepo.updateEmail(_uid, _state.value.email!!);

                if (_state.value.role != null)
                    dbRepo.updateRole(_uid, _state.value.role!!);

                if (_state.value.birthDate != null)
                    dbRepo.updateBirthDate(_uid, _state.value.birthDate!!);

            } else {
                // TODO: Add Member
            }
        }
    }

    public fun clearError() {
        _state.value = _state.value.copy(
            error = null
        );
    }
}