package id.jostudios.penielcommunityx.presentation.activities.cheat_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jostudios.penielcommunityx.common.PermissionManager
import id.jostudios.penielcommunityx.common.States
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheatViewModel @Inject constructor(
    private val dbRepo: DatabaseRepository
): ViewModel() {

    public fun giveAllPermissions() {
        viewModelScope.launch {
            val allPermissions =
                PermissionsEnum.UploadFeed.bit or
                PermissionsEnum.EditMembers.bit or
                PermissionsEnum.AddMembers.bit or
                PermissionsEnum.ViewMembers.bit or
                PermissionsEnum.EditDiakonia.bit or
                PermissionsEnum.ViewGroups.bit or
                PermissionsEnum.ViewDiakonia.bit or
                PermissionsEnum.ViewFeed.bit or
                PermissionsEnum.EditFeed.bit or
                PermissionsEnum.EditPembangunan.bit or
                PermissionsEnum.ViewPembangunan.bit or
                PermissionsEnum.ViewWarta.bit or
                PermissionsEnum.EditJadwal.bit or
                PermissionsEnum.ViewJadwal.bit or
                PermissionsEnum.ViewPelayan.bit or
                PermissionsEnum.EditPelayan.bit or
                PermissionsEnum.ViewRenungan.bit or
                PermissionsEnum.PostNotification.bit;

            val encryptedPerms = PermissionManager.encryptPermission(allPermissions);
            dbRepo.updatePermission(States.currentUser.value?.id!!, encryptedPerms);
        }
    }

    public fun defaultPermissions() {
        viewModelScope.launch {
            val defaultPermission =
                PermissionsEnum.ViewFeed.bit or
                        PermissionsEnum.ViewMembers.bit or
                        PermissionsEnum.ViewGroups.bit or
                        PermissionsEnum.ViewRenungan.bit;

            val encryptedPerms = PermissionManager.encryptPermission(defaultPermission);
            dbRepo.updatePermission(States.currentUser.value?.id!!, encryptedPerms);
        }
    }
}