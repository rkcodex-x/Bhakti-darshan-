package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BhaktiDatabase : RoomDatabase() {
    abstract fun bhaktiDao(): BhaktiDao

    companion object {
        @Volatile
        private var INSTANCE: BhaktiDatabase? = null

        fun getDatabase(context: Context): BhaktiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BhaktiDatabase::class.java,
                    "bhakti_darshan.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
