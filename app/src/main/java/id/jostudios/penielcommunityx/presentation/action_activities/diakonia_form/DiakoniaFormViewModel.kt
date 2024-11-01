package id.jostudios.penielcommunityx.presentation.action_activities.diakonia_form

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.TextFormat
import id.jostudios.penielcommunityx.domain.model.DiakoniaModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.presentation.activities.login_activity.LoginActivity
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DiakoniaFormViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository
): ViewModel() {
    private var _state: MutableState<DiakoniaState> = mutableStateOf(DiakoniaState());

    private var _usersState: MutableList<UserModel> = mutableListOf();

    val state: State<DiakoniaState> = _state;

    public fun setId(value: String) {
        _state.value = _state.value.copy(
            username = value
        );
    }

    public fun setLoading(value: Boolean) {
        _state.value = _state.value.copy(
            isLoading = value
        );
    }

    public fun setAmountPaid(value: String) {
        _state.value = _state.value.copy(
            amountPaid = value
        );
    }

    public fun setDateTime(value: Long) {
        _state.value = _state.value.copy(
            dateTime = value
        );
    }

    public fun fetchUserList() {
        viewModelScope.launch {
            setLoading(true);
            _usersState = dbRepo.getUsers().toMutableList();
            setLoading(false);
        }
    }

    public fun saveDiakonia() {
        viewModelScope.launch {
            if (_state.value.username == null && _state.value.amountPaid == null) {
                return@launch;
            }

            val now = Date().time;
            setDateTime(now);

            val diakonia = dbRepo.getDiakoniaMembers();
            val id = _usersState.find { it.name == _state.value.username }?.id;

            Log.d("DiakoniaViewModel", "ID : $id");

            if (id == null) {
                return@launch;
            }

            val amount = _state.value.amountPaid?.replace("Rp ", "")?.replace(",", "")?.toInt();

            val model = DiakoniaModel(
                amountPaid = amount,
                dateTime = now
            );

            if (diakonia[id] != null) {
                // Update data
                val existingData = diakonia[id];
                Log.d("DiakoniaViewModel", "Update data : $existingData");
                dbRepo.updateDiakonia(id, existingData?.size!!, model);
            } else {
                // New Data
                Log.d("DiakoniaViewModel", "Create new data");
                dbRepo.createDiakonia(id);
                dbRepo.updateDiakonia(id, 0, model);
            }
        }
    }
}