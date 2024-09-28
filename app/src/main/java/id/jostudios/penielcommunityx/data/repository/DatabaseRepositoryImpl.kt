package id.jostudios.penielcommunityx.data.repository

import id.jostudios.penielcommunityx.data.remote.DatabaseAPI
import id.jostudios.penielcommunityx.domain.model.CredentialModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import com.google.gson.reflect.TypeToken
import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.RolesEnum
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import java.util.Dictionary
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private var dbAPI: DatabaseAPI
): DatabaseRepository {
    override suspend fun createUser(userModel: UserModel) {
        dbAPI.write("users/${userModel.id}", userModel);
    }

    override suspend fun createCredential(credentialModel: CredentialModel) {
        dbAPI.write("credentials/${credentialModel.id}", credentialModel);
    }

    override suspend fun getUsers(): List<UserModel> {
        val raw = dbAPI.read("users");
        val obj = dbAPI.gson.fromJson<Map<String, UserModel>>(raw, object: TypeToken<Map<String, UserModel>>(){}.type);
        val memberList = obj.values.toList();

        return memberList;
    }

    override suspend fun getUserById(id: String): UserModel {
        val raw = dbAPI.read("users/${id}");
        val obj = dbAPI.gson.fromJson<UserModel>(raw, UserModel::class.java);

        return obj;
    }

    override suspend fun getCurrentBanner(): String {
        return dbAPI.read("_currentBanner");
    }

    override suspend fun getGlobalAppVersion(): String {
        return dbAPI.read("_appVersion");
    }

    override suspend fun updateUserById(id: String, userModel: UserModel) {
        dbAPI.database.child("users/${id}").setValue(userModel);
    }

    override suspend fun updatePermission(id: String, permission: String) {
        dbAPI.database.child("users/${id}/permissions").setValue(permission);
    }

    override suspend fun updateDisplayName(id: String, name: String) {
        dbAPI.database.child("users/${id}/displayName").setValue(name);
    }

    override suspend fun updateProfilePicture(id: String, profile: String) {
        dbAPI.database.child("users/${id}/profilePicture").setValue(profile);
    }

    override suspend fun updateGroup(id: String, groups: List<GroupsEnum>) {
        dbAPI.database.child("users/${id}/groups").setValue(groups);
    }

    override suspend fun updateBirthDate(id: String, date: Long) {
        dbAPI.database.child("users/${id}/birthDate").setValue(date);
    }

    override suspend fun updateEmail(id: String, email: String) {
        dbAPI.database.child("users/${id}/email").setValue(email);
    }

    override suspend fun updatePhoneNumber(id: String, number: String) {
        dbAPI.database.child("users/${id}/phoneNumber").setValue(number);
    }

    override suspend fun updateRole(id: String, role: RolesEnum) {
        dbAPI.database.child("users/${id}/role").setValue(role.name);
    }
}