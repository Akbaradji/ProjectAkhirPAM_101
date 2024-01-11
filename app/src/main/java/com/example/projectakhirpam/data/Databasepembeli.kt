package com.example.projectakhirpam.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [pembeli::class, game::class], version = 2, exportSchema = false)
abstract class Databasepembeli: RoomDatabase(){
    abstract fun pembeliDao(): pembeliDao
    abstract fun gameDao(): GameDao

    companion object{
        @Volatile
        private var Instance: Databasepembeli? = null

        fun getDatabase(context: Context) : Databasepembeli {
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context,
                    Databasepembeli::class.java,
                    "pembeli_database")
                    .build()
                    .also { Instance=it}
            })
        }
    }
}