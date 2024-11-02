package id.jostudios.penielcommunityx.data.repository

import id.jostudios.penielcommunityx.data.cache.dao.CacheControlDao
import id.jostudios.penielcommunityx.data.cache.model.CacheControlModel
import id.jostudios.penielcommunityx.domain.repository.CacheControlRepository
import javax.inject.Inject

class CacheControlRepositoryImpl @Inject constructor(
    private val cacheControlDao: CacheControlDao
) : CacheControlRepository {
    override suspend fun insert(cache: CacheControlModel) {
        cacheControlDao.insert(cache);
    }

    override suspend fun update(cache: CacheControlModel) {
        cacheControlDao.update(cache);
    }

    override suspend fun delete(cache: CacheControlModel) {
        cacheControlDao.delete(cache);
    }

    override suspend fun getValue(name: String): CacheControlModel {
        return cacheControlDao.getValue(name);
    }
}