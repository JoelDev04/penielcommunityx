package id.jostudios.penielcommunityx.data.remote

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.util.Date

class StorageAPI {
    private val storage = Firebase.storage.reference;

    public suspend fun fetchImage(child: String): Uri? {
        val storedImg = storage.child(child).downloadUrl;
        val downloadUrl = storedImg.await();

        return downloadUrl;
    }

    public suspend fun uploadImage(child: String, imgName: String, imgUri: Uri): Uri? {
        val date = Date();
        val upPath = storage.child("$child/content_${imgName}");
        val upTask = upPath.putFile(imgUri).await();
        val path = upTask.storage.downloadUrl.await();

        return path;
    }
}