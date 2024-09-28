package id.jostudios.penielcommunityx.data.repository

import com.google.firebase.auth.FirebaseAuth
import id.jostudios.penielcommunityx.data.remote.AuthAPI
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthRepository {
    override fun getAuth(): FirebaseAuth {
        return authAPI.getAuth();
    }

    override suspend fun login(username: String, password: String): String {
        return authAPI.loginAccount(username, password);
    }

    override suspend fun signup(username: String, password: String): String {
        return authAPI.createAccount(username, password);
    }

    override suspend fun signOut() {
        authAPI.signOut();
    }
}