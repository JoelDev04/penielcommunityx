package id.jostudios.penielcommunityx.data.repository

import android.net.Uri
import androidx.core.net.toFile
import id.jostudios.penielcommunityx.data.remote.StorageAPI
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import java.io.File
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageAPI: StorageAPI
): StorageRepository {
    override suspend fun getImage(child: String): Uri? {
        return storageAPI.fetchImage(child);
    }

    override suspend fun postImage(
        child: String,
        imgName: String,
        imgUri: Uri
    ): Uri? {
        return storageAPI.uploadImage(child, imgName, imgUri);
    }

    override suspend fun getProfilePicture(imageName: String): Uri? {
        return storageAPI.fetchImage("/profiles/$imageName");
    }

    override suspend fun postProfilePicture(imgUri: Uri): Uri? {
        var file = imgUri.toFile();
        return storageAPI.uploadImage("/profiles", file.name.toString(), imgUri);
    }

    override suspend fun getBanner(imageName: String): Uri? {
        return storageAPI.fetchImage("/banners/$imageName");
    }
}