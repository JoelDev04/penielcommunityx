package id.jostudios.penielcommunityx.data.cache.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.jostudios.penielcommunityx.data.cache.dao.CacheControlDao
import id.jostudios.penielcommunityx.data.cache.model.CacheControlModel

@Database(
    entities = [CacheControlModel::class],
    version = 1,
    exportSchema = true
)
abstract class CacheControlDatabase: RoomDatabase() {
    abstract fun cacheControlDao(): CacheControlDao;

    companion object {
        @Volatile
        public var INSTANCE: CacheControlDatabase? = null;

        public fun getDatabase(context: Context): CacheControlDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, CacheControlDatabase::class.java, "CacheControlDB").fallbackToDestructiveMigration().build();
                INSTANCE = instance;
                instance;
            }
        }
    }
}