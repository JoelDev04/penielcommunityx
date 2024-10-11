package id.jostudios.penielcommunityx.data.remote

import android.provider.ContactsContract.Data
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import id.jostudios.penielcommunityx.common.Crypto
import id.jostudios.penielcommunityx.domain.model.CredentialModel
import id.jostudios.penielcommunityx.domain.model.UserModel
import kotlinx.coroutines.tasks.await
import okhttp3.internal.notify
import javax.inject.Inject

class AuthAPI {
    private var auth = Firebase.auth;

    public fun getAuth(): FirebaseAuth {
        return auth;
    }

    public suspend fun createAccount(username: String, password: String): String {
        val formattedEmail = "peniel-$username@gmail.com";
        val securedPassword = Crypto.Encrypt(password);
        val currentUser = auth.currentUser;

        val result = auth.createUserWithEmailAndPassword(formattedEmail, securedPassword);
        val task = result.await();
        val user = task.user;

        auth.updateCurrentUser(currentUser!!).await();

        if (user == null) {
            throw Exception("Create user failed!");
        }

        Log.d("Auth", "Signup UID : ${user.uid}");

        return user.uid;
    }

    public suspend fun loginAccount(username: String, password: String): String {
        val formattedEmail = "peniel-$username@gmail.com";
        val securedPassword = Crypto.Encrypt(password);

        val result = auth.signInWithEmailAndPassword(formattedEmail, securedPassword);
        val task = result.await();
        val user = task.user;

        if (user == null) {
            throw Exception("Login user failed!");
        }

        Log.d("Auth", "Login UID : ${user.uid}");

        return user.uid;
    }

    public suspend fun signOut() {
        auth.signOut();
    }
}