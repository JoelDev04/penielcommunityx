package id.jostudios.penielcommunityx.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.jostudios.penielcommunityx.data.cache.model.CacheControlModel

@Dao
interface CacheControlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cache: CacheControlModel);

    @Update
    suspend fun update(cache: CacheControlModel);

    @Delete
    suspend fun delete(cache: CacheControlModel);

    @Query("SELECT * FROM CacheControl WHERE name = :name")
    suspend fun getValue(name: String): CacheControlModel;
}