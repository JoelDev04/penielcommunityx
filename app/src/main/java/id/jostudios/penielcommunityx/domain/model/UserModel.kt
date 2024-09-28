package id.jostudios.penielcommunityx.domain.model

import android.util.Log
import id.jostudios.penielcommunityx.common.Crypto
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.domain.enums.RolesEnum

data class UserModel(
    val id: String,
    val name: String,
    var displayName: String,
    val birthDate: Long = 0,
    var permissions: String = "",
    var groups: List<GroupsEnum> = listOf(
        GroupsEnum.Jemaat
    ),
    val role: RolesEnum = RolesEnum.Member,
    val email: String = "",
    val phoneNumber: String = "",
    var profilePicture: String = "blank.png",
    val isDisplayEmail: Boolean = true,
    val isDisplayPhone: Boolean = true,
    val realName: String = ""
) {
    private var defaultPermission =
        PermissionsEnum.ViewFeed.bit or
        PermissionsEnum.ViewMembers.bit or
        PermissionsEnum.ViewGroups.bit or
        PermissionsEnum.ViewRenungan.bit;

    fun setPermission() {
        if (permissions == "") {
            val encrypted = Crypto.Encrypt("PermSet-${defaultPermission}");
            permissions = encrypted;
        }
    }

    fun debugPermission() {
        val permSet = PermissionsEnum.ViewFeed.bit or
                PermissionsEnum.ViewMembers.bit or
                PermissionsEnum.ViewGroups.bit or
                PermissionsEnum.ViewRenungan.bit;
        val encrypted = Crypto.Encrypt("PermSet-${permSet}");
        Log.d("PermissionManager", "Default Perms : $permSet");
        Log.d("PermissionManager", "Default Encrypted : ${encrypted}");
    }
}