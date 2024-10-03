package id.jostudios.penielcommunityx.common

import android.util.Log
import id.jostudios.penielcommunityx.domain.enums.PermissionsEnum

object PermissionManager {
//    fun addPermission(perm: PermissionsEnum) {
//        val decrypted = decryptPermission();
//        var rawPermission = decrypted;
//
//        rawPermission = rawPermission or perm.bit;
//
//        val encrypted = encryptPermission(rawPermission);
//        permissions.value = encrypted;
//    }

//    fun removePermission(perm: PermissionsEnum) {
//        val decrypted = decryptPermission();
//        var rawPermission = decrypted;
//
//        rawPermission = rawPermission and perm.bit.inv();
//
//        val encrypted = encryptPermission(rawPermission);
//        permissions.value = encrypted;
//    }

    fun checkPermission(perm: PermissionsEnum): Boolean {
        val decrypted = decryptPermission();
        var rawPermission = decrypted;

        Log.d("PermissionManager", "Raw Permission : $rawPermission");

        return rawPermission and perm.bit != 0;
    }

    fun decryptPermission(): Int {
        if (States.permissions.value == null) {
            //throw Exception("State permission is null!");
            return 0;
        }

        val decrypted = Crypto.Decrypt(States.permissions.value!!);
        val rawPermission = decrypted;

        if (rawPermission == null) {
            throw Exception("Raw permission is null!");
        }

        // Check permission prefix
        if (rawPermission.startsWith("PermSet-", 0)) {
            val permSet = rawPermission.removePrefix("PermSet-").toInt();
            return permSet
        }

        return 0;
    }

    fun encryptPermission(decrypted: Int): String {
        val encrypted = Crypto.Encrypt("PermSet-$decrypted");

        return encrypted;
    }

    fun getRawPermissions(permSet: Int): String {
        return permSet.toString(2).padStart(18, '0');
    }
}