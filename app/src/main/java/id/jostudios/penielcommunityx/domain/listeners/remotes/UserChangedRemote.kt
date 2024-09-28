package id.jostudios.penielcommunityx.domain.listeners.remotes

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import id.jostudios.penielcommunityx.data.remote.DatabaseAPI
import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum
import id.jostudios.penielcommunityx.domain.listeners.interfaces.UserChangedListenter
import id.jostudios.penielcommunityx.domain.model.UserModel
import javax.inject.Inject

class UserChangedRemote @Inject constructor(
    private val dbAPI: DatabaseAPI,
) {
    operator fun invoke(
        userID: String,
        listener: UserChangedListenter
    ) {
        dbAPI.database.child("users/${userID}").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val key = snapshot.key;
                val value = snapshot.value;

                Log.d("DBRemoteListener", "Key : $key");
                Log.d("DBRemoteListener", "Value : $value");

                when (key) {
                    "permissions" -> {
                        val permissions = snapshot.value as String;
                        listener.onUserPermissionsChanged(permissions);
                    }
                    "displayName" -> {
                        val name = snapshot.value as String;
                        listener.onUserNameChanged(name);
                    }
                    "groups" -> {
                        val groups = snapshot.value as List<GroupsEnum>;
                        listener.onUserGroupChanged(groups);
                    }
                    "profilePicture" -> {
                        val profile = snapshot.value as String;
                        listener.onUserProfileChanged(profile);
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        });
    }
}