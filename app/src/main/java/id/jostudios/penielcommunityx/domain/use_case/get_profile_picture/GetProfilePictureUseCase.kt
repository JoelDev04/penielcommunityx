package id.jostudios.penielcommunityx.domain.use_case.get_profile_picture

import android.net.Uri
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetProfilePictureUseCase @Inject constructor(
    private val storageRepo: StorageRepository
) {
    operator fun invoke(name: String): Flow<Resource<Uri>> = flow {
        try {
            emit(Resource.Loading());

            var profileUri = storageRepo.getProfilePicture(name);

            if (profileUri == null) {
                emit(Resource.Error("Profile picture not found!"));
                return@flow
            }

            emit(Resource.Success(profileUri));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}