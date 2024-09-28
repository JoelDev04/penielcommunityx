package id.jostudios.penielcommunityx.domain.use_case.get_version

import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetVersionUseCase @Inject constructor(
    private val dbRepo: DatabaseRepository
) {
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading());

            val appVersion = dbRepo.getGlobalAppVersion().replace("\"", "");

            emit(Resource.Success(appVersion));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}