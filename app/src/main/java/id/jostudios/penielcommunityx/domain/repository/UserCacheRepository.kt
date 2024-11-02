package id.jostudios.penielcommunityx.domain.repository

import id.jostudios.penielcommunityx.data.cache.model.UserCacheModel

interface UserCacheRepository {
    suspend fun insertUser(model: UserCacheModel);

    suspend fun updateUser(model: UserCacheModel);

    suspend fun deleteUser(model: UserCacheModel);

    suspend fun deleteAllUser();

    suspend fun getCachedUsers(): List<UserCacheModel>;

    suspend fun getCachedUserById(uid: String): UserCacheModel;

    suspend fun getCachedUserByName(username: String): UserCacheModel;
}