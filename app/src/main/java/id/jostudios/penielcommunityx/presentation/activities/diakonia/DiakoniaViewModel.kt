package id.jostudios.penielcommunityx.presentation.activities.diakonia

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DiakoniaViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository
): ViewModel() {
    private var _state: MutableState<DiakoniaState> = mutableStateOf(DiakoniaState());

    public val state: State<DiakoniaState> = _state;

    public fun loadDiakonia() {
        viewModelScope.launch {
            val diakoniaData = dbRepo.getDiakoniaMembers();
        }
    }

    private fun loadingState(value: Boolean) {
        _state.value = _state.value.copy(
            isLoading = value
        );
    }

    public fun setError(value: String? = null) {
        _state.value = _state.value.copy(
            error = value
        );
    }
}