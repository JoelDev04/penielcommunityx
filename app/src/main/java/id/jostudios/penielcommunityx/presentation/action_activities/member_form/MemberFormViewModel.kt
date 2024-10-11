package id.jostudios.penielcommunityx.presentation.action_activities.member_form

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.Crypto
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.enums.RolesEnum
import id.jostudios.penielcommunityx.domain.model.CredentialModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.use_case.signup.SignupUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class MemberFormViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository,
    private val authRepo: AuthRepository
) : ViewModel() {
    private val _state: MutableState<MemberFormState> = mutableStateOf(MemberFormState());
    private var _uid: String = "";

    public var state: State<MemberFormState> = _state;

    public fun loadUser(uid: String) {
        try {
            viewModelScope.launch {
                _uid = uid;

                if (_uid == "") {
                    // Initialize empty from

                    _state.value = _state.value.copy(
                        currentUser = UserModel(
                            id = "",
                            name = "",
                            displayName = ""
                        )
                    );

                    setName("");
                    setPassword("");
                    setDisplayName("");
                    setEmail("");
                    setPhoneNumber("");
                    setRole("");
                    setBirthDate(0);

                    return@launch;
                }

                if (States.currentUser.value?.id == uid) {
                    Log.d("MemberForm", "Loading Current User!");

                    _state.value = _state.value.copy(
                        currentUser = States.currentUser.value
                    );

                    setDisplayName(_state.value.currentUser?.displayName!!);
                    setEmail(_state.value.currentUser?.email!!);
                    setPhoneNumber(_state.value.currentUser?.phoneNumber!!);
                    setBirthDate(_state.value.currentUser?.birthDate!!);

                } else {
                    Log.d("MemberForm", "Loading Other User!");
                    val user = dbRepo.getUserById(uid);

                    _state.value = _state.value.copy(
                        currentUser = user
                    );

                    setDisplayName(_state.value.currentUser?.displayName!!);
                    setEmail(_state.value.currentUser?.email!!);
                    setPhoneNumber(_state.value.currentUser?.phoneNumber!!);
                    setBirthDate(_state.value.currentUser?.birthDate!!);
                }
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                error = e.localizedMessage ?: "Unknown error. Please contact developer team!"
            );
        }
    }

    public fun setName(value: String) {
        _state.value = _state.value.copy(
            name = value
        );
    }

    public fun setPassword(value: String) {
        _state.value = _state.value.copy(
            password = value
        );
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

    public fun setRole(value: String) {
        _state.value = _state.value.copy(
            role = value
        );
    }

    public fun setBirthDate(value: Long) {
        _state.value = _state.value.copy(
            birthDate = value
        );
    }

    public fun updateDetails() {
        if (_uid == "") {
            Log.d("MemberFormViewModel", "Creating new member");

            viewModelScope.launch {
                try {
                    _state.value = _state.value.copy(
                        isLoading = true
                    );

                    val userCredential =
                        authRepo.signup(_state.value.name!!, _state.value.password!!);

                    val newCredential = CredentialModel(
                        id = userCredential,
                        name = _state.value.name!!,
                        password = Crypto.Encrypt(_state.value.password!!)
                    );

                    dbRepo.createCredential(newCredential);

                    val newUser = UserModel(
                        id = userCredential,
                        name = _state.value.name!!,
                        displayName = _state.value.displayName!!,
                        email = if (_state.value.email == null) "" else _state.value.email!!,
                        phoneNumber = if (_state.value.phoneNumber == null) "" else _state.value.phoneNumber!!,
                        role = if(_state.value.role == null) RolesEnum.valueOf(_state.value.role!!) else RolesEnum.Member
                    );
                    newUser.setPermission();

                    dbRepo.createUser(newUser);

                    _state.value = _state.value.copy(
                        isLoading = false
                    );

                    _state.value = _state.value.copy(
                        isClose = true
                    );

                    Log.d("MemberFormViewModel", "Successfully create account with UID : $userCredential");
                } catch (e: Exception) {
                    _state.value = _state.value.copy(
                        error = e.localizedMessage
                    )

                    Log.d("MemberFormViewModel", "Error : ${e.localizedMessage}");
                }
            }
        }

        if (_uid != "") {
            // Save non-null values!

            viewModelScope.launch {
                _state.value = _state.value.copy(
                    isLoading = true
                );

                if (_state.value.displayName != null)
                    dbRepo.updateDisplayName(_uid, _state.value.displayName!!);

                if (_state.value.phoneNumber != null)
                    dbRepo.updatePhoneNumber(_uid, _state.value.phoneNumber!!);

                if (_state.value.email != null)
                    dbRepo.updateEmail(_uid, _state.value.email!!);

                if (_state.value.birthDate != null)
                    dbRepo.updateBirthDate(_uid, _state.value.birthDate!!);

                Log.d("MemberFormViewModel", "Saving current values!");

                _state.value = _state.value.copy(
                    isLoading = false
                );

                _state.value = _state.value.copy(
                    isClose = true
                );
            }

        }


    }

    public fun clearError() {
        _state.value = _state.value.copy(
            error = null
        );
    }
}