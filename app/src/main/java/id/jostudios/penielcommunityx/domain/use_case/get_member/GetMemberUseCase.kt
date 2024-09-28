package id.jostudios.penielcommunityx.domain.use_case.get_member

import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(
    private val dbRepo: DatabaseRepository
) {
    operator fun invoke(userID: String): Flow<Resource<UserModel>> = flow {
        try {
            emit(Resource.Loading());

            val member = dbRepo.getUserById(userID);

            emit(Resource.Success(member));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}