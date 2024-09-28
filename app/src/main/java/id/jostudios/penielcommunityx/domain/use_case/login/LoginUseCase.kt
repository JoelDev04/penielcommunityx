package id.jostudios.penielcommunityx.domain.use_case.login

import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(username: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading());
            val uid = repo.login(username, password);
            emit(Resource.Success(uid));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}