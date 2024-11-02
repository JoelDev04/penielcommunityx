package id.jostudios.penielcommunityx.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.jostudios.penielcommunityx.data.cache.model.UserCacheModel

@Dao
interface UserCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(model: UserCacheModel);

    @Update
    suspend fun updateUser(model: UserCacheModel);

    @Delete
    suspend fun deleteUser(model: UserCacheModel);

    @Query("DELETE FROM UserCaches")
    suspend fun deleteAllUser();

    @Query("SELECT * FROM UserCaches")
    suspend fun getCachedUsers(): List<UserCacheModel>;

    @Query("SELECT * FROM UserCaches WHERE id = :uid")
    suspend fun getCachedUserById(uid: String): UserCacheModel;

    @Query("SELECT * FROM UserCaches WHERE name = :username")
    suspend fun getCachedUserByName(username: String): UserCacheModel;
}