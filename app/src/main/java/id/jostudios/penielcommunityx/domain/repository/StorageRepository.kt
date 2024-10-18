package id.jostudios.penielcommunityx.domain.repository

import android.net.Uri

interface StorageRepository {
    suspend fun getImage(child: String): Uri?

    suspend fun postImage(child: String, imgName: String, imgUri: Uri): Uri?

    suspend fun getProfilePicture(imageName: String): Uri?

    suspend fun postProfilePicture(imgUri: Uri, imgName: String): Uri?

    suspend fun getBanner(imageName: String): Uri?
}