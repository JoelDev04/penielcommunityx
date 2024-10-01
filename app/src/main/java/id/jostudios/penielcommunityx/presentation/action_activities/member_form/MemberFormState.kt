package id.jostudios.penielcommunityx.presentation.action_activities.member_form

import id.jostudios.penielcommunityx.domain.enums.RolesEnum
import id.jostudios.penielcommunityx.domain.model.UserModel

data class MemberFormState(
    val currentUser: UserModel? = null,
    val error: String? = null,

    val displayName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val birthDate: Long? = null
);
