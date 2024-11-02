package id.jostudios.penielcommunityx.data.repository

import id.jostudios.penielcommunityx.data.cache.dao.UserCacheDao
import id.jostudios.penielcommunityx.data.cache.model.UserCacheModel
import id.jostudios.penielcommunityx.domain.repository.UserCacheRepository
import javax.inject.Inject

class UserCacheRepositoryImpl @Inject constructor(
    private val cacheDao: UserCacheDao
) : UserCacheRepository {
    override suspend fun insertUser(model: UserCacheModel) {
        cacheDao.insertUser(model);
    }

    override suspend fun updateUser(model: UserCacheModel) {
        cacheDao.updateUser(model);
    }

    override suspend fun deleteUser(model: UserCacheModel) {
        cacheDao.deleteUser(model);
    }

    override suspend fun deleteAllUser() {
        cacheDao.deleteAllUser();
    }

    override suspend fun getCachedUsers(): List<UserCacheModel> {
        return cacheDao.getCachedUsers();
    }

    override suspend fun getCachedUserById(uid: String): UserCacheModel {
        return cacheDao.getCachedUserById(uid);
    }

    override suspend fun getCachedUserByName(username: String): UserCacheModel {
        return cacheDao.getCachedUserByName(username);
    }
}