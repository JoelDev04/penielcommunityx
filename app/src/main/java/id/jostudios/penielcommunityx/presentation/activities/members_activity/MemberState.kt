package id.jostudios.penielcommunityx.presentation.activities.members_activity

import id.jostudios.penielcommunityx.domain.model.UserModel

data class MemberState(
    val isLoading: Boolean = false,
    val members: List<UserModel>? = null,
    val error: String? = null
)
