package id.jostudios.penielcommunityx.domain.repository

import com.google.firebase.auth.FirebaseAuth

interface AuthRepository {
    fun getAuth(): FirebaseAuth;

    suspend fun login(username: String, password: String): String;

    suspend fun signup(username: String, password: String): String;

    suspend fun signOut();
}