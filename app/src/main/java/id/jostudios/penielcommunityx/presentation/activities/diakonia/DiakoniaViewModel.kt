package id.jostudios.penielcommunityx.presentation.activities.diakonia

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.domain.model.DiakoniaModel
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiakoniaViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository
): ViewModel() {
    private var _state: MutableState<DiakoniaState> = mutableStateOf(DiakoniaState());

    public val state: State<DiakoniaState> = _state;

    private val usernameMap = mutableStateMapOf<String, String?>();

    public fun loadDiakonia() {
        viewModelScope.launch {
            loadingState(true);

            val diakoniaData = dbRepo.getDiakoniaMembers();
            setDiakoniaMembers(diakoniaData);

            loadingState(false);
        }
    }

    public fun fetchUsername(id: String) {
        viewModelScope.launch {
            val name = dbRepo.getNameByID(id);
            usernameMap[id] = name.replace("\"", "");
        }
    }

    public fun getName(id: String): String? {
        return usernameMap[id];
    }

    private fun loadingState(value: Boolean) {
        _state.value = _state.value.copy(
            isLoading = value
        );
    }

    private fun setDiakoniaMembers(value: Map<String, List<DiakoniaModel>>) {
        _state.value = _state.value.copy(
            diakoniaMembers = value
        );
    }

    public fun setError(value: String? = null) {
        _state.value = _state.value.copy(
            error = value
        );
    }
}