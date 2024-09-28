package id.jostudios.penielcommunityx.domain.listeners.interfaces

import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum

interface UserChangedListenter {
    fun onUserPermissionsChanged(data: String);

    fun onUserGroupChanged(data: List<GroupsEnum>);

    fun onUserNameChanged(data: String);

    fun onUserProfileChanged(data: String);
}