package id.jostudios.penielcommunityx.domain.use_case.signup

import id.jostudios.penielcommunityx.common.Crypto
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.model.CredentialModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val authRepo: AuthRepository,
    private val dbRepo: DatabaseRepository
) {
    operator fun invoke(username: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading());

            val uid = authRepo.signup(username, password);
            val userModel = UserModel(
                id = uid,
                name = username,
                displayName = username
            );
            userModel.setPermission();

            val credentialModel = CredentialModel(
                id = uid,
                name = username,
                password = Crypto.Encrypt(password)
            );

            dbRepo.createUser(userModel);
            dbRepo.createCredential(credentialModel);

            emit(Resource.Success(uid));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}