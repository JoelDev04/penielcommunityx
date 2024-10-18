package id.jostudios.penielcommunityx.domain.repository

import id.jostudios.penielcommunityx.domain.enums.GroupsEnum
import id.jostudios.penielcommunityx.domain.enums.RolesEnum
import id.jostudios.penielcommunityx.domain.model.CredentialModel
import id.jostudios.penielcommunityx.domain.model.DiakoniaModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.presentation.activities.diakonia.Diakonia

interface DatabaseRepository {

    // Create Operations \\
    suspend fun createUser(userModel: UserModel);

    suspend fun createCredential(credentialModel: CredentialModel);

    suspend fun createDiakonia(id: String);
    // Create Operations \\

    // Read Operations \\
    suspend fun getUsers(): List<UserModel>;

    suspend fun getDiakoniaMembers(): Map<String, List<DiakoniaModel>>;

    suspend fun getUserById(id: String): UserModel;

    suspend fun getCurrentBanner(): String;

    suspend fun getGlobalAppVersion(): String;
    // Read Operations \\

    // Update Operations \\
    suspend fun updateUserById(id: String, userModel: UserModel);

    suspend fun updatePermission(id: String, permission: String);

    suspend fun updateDiakonia(id: String, transaction: DiakoniaModel);

    suspend fun updateDisplayName(id: String, name: String);

    suspend fun updateProfilePicture(id: String, profile: String);

    suspend fun updateGroup(id: String, groups: List<GroupsEnum>);

    suspend fun updateBirthDate(id: String, date: Long);

    suspend fun updateEmail(id: String, email: String);

    suspend fun updatePhoneNumber(id: String, number: String);

    suspend fun updateRole(id: String, role: RolesEnum);
    // Update Operations \\

    // Delete Operations \\

    // Delete Operations \\
}