package com.example.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.room.model.User
import com.example.myapplication.room.model.UserDao

/**
 * @date 2021/4/14
 * @author qipeng
 * @desc appDatabase
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}