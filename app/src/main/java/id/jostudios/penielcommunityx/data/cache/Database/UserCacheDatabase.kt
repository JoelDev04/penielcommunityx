package id.jostudios.penielcommunityx.data.cache.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.jostudios.penielcommunityx.data.cache.dao.UserCacheDao
import id.jostudios.penielcommunityx.data.cache.model.UserCacheModel

@Database(
    entities = [UserCacheModel::class],
    version = 1,
    exportSchema = true
)
abstract class UserCacheDatabase: RoomDatabase() {
    abstract fun userCacheDao(): UserCacheDao;

    companion object {
        @Volatile
        private var INSTANCE: UserCacheDatabase? = null

        fun getDatabase(context: Context): UserCacheDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserCacheDatabase::class.java,
                    "UserCacheDB"
                ).fallbackToDestructiveMigration().build();
                INSTANCE = instance;
                instance;
            }
        }
    }
}