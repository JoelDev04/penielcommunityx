package id.jostudios.penielcommunityx.domain.use_case.get_banner

import android.net.Uri
import id.jostudios.penielcommunityx.common.Resource
import id.jostudios.penielcommunityx.data.remote.StorageAPI
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(
    private val storageRepo: StorageRepository
) {
    operator fun invoke(bannerName: String): Flow<Resource<Uri>> = flow {
        try {
            emit(Resource.Loading());
            val banner = storageRepo.getBanner(bannerName);

            if (banner == null) {
                emit(Resource.Error("Banner not found!"));
                return@flow;
            }

            emit(Resource.Success(banner));
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error. Please contact developer team!"));
        } catch (e: IOException) {
            emit(Resource.Error("Server error!"));
        }
    }
}