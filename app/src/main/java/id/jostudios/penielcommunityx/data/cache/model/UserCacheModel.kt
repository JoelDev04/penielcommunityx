package id.jostudios.penielcommunityx.data.cache.model

import androidx.room.Entity
import id.jostudios.penielcommunityx.common.CacheConverter
import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.RolesEnum

@Entity(
    tableName = "UserCaches",
)
data class UserCacheModel(
    val id: String,
    val name: String,
    var displayName: String,
    val birthDate: Long = 0,
    var permissions: String = "",
    var groups: String = CacheConverter.toRawGroup(listOf(
        GroupsEnum.Jemaat
    )),
    val role: String = CacheConverter.toRawRole(
        RolesEnum.Member
    ),
    val email: String = "",
    val phoneNumber: String = "",
    var profilePicture: String = "blank.png",
    val isDisplayEmail: Boolean = true,
    val isDisplayPhone: Boolean = true,
    val realName: String = ""
)
