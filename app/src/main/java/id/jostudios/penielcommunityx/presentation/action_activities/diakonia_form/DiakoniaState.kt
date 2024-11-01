package id.jostudios.penielcommunityx.presentation.action_activities.diakonia_form

data class DiakoniaState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val username: String? = null,
    val amountPaid: String? = null,
    val dateTime: Long? = null
);
