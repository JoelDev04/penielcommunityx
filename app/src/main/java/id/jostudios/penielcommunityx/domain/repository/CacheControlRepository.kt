package id.jostudios.penielcommunityx.domain.repository

import id.jostudios.penielcommunityx.data.cache.model.CacheControlModel

interface CacheControlRepository {
    suspend fun insert(cache: CacheControlModel);

    suspend fun update(cache: CacheControlModel);

    suspend fun delete(cache: CacheControlModel);

    suspend fun getValue(name: String): CacheControlModel?;
}