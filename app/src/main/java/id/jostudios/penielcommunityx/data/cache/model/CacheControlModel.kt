package id.jostudios.penielcommunityx.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheControl")
data class CacheControlModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val value: String
)
