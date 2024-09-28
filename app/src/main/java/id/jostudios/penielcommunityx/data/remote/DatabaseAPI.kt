package id.jostudios.penielcommunityx.data.remote

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import okhttp3.internal.notify

class DatabaseAPI {
    public val database = Firebase.database.reference;
    public val gson = Gson();

    public suspend fun read(child: String): String {
        val raw = database.child(child).get().await().value;
        val obj = gson.toJson(raw);

        return obj;
    }

    public suspend fun write(child: String, data: Any) {
        database.child(child).setValue(data);
    }
}