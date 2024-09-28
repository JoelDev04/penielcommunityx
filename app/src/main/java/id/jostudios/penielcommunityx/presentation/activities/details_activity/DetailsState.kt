package id.jostudios.penielcommunityx.presentation.activities.details_activity

import android.net.Uri
import id.jostudios.penielcommunityx.domain.model.UserModel

data class DetailsState(
    val isLoading: Boolean = false,
    val userModel: UserModel? = null,
    val error: String? = null,
    val profileUri: Uri? = null,
    val userID: String? = null
)
