package com.evantemplate.cats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.evantemplate.cats.models.Cat

@Database(entities = [Cat::class], version = 1)
abstract class CatDatabase: RoomDatabase(){

    abstract val catDao: CatDao

    companion object{
        private lateinit var INSTANCE: CatDatabase

        fun getInstance(context: Context): CatDatabase{
            synchronized(CatDatabase::class.java){
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    CatDatabase::class.java, "CatsDB").build()
                }
            }
            return INSTANCE
        }
    }
}