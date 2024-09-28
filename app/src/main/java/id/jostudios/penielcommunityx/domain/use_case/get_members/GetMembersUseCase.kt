package id.jostudios.penielcommunityx.domain.use_case.get_members

import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.model.UserModel
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMembersUseCase @Inject constructor(
    private val dbRepo: DatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<UserModel>>> = flow {
        try {
            emit(Resource.Loading());

            val members = dbRepo.getUsers();

            emit(Resource.Success(members));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}