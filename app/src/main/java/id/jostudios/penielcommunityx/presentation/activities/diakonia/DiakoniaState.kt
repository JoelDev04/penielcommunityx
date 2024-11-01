package id.jostudios.penielcommunityx.presentation.activities.diakonia

import id.jostudios.penielcommunityx.domain.model.DiakoniaModel

data class DiakoniaState(
    var isLoading: Boolean = false,
    var error: String? = null,
    var diakoniaMembers: Map<String, List<DiakoniaModel>>? = null
);
