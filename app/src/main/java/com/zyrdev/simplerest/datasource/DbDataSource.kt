package com.zyrdev.simplerest.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zyrdev.simplerest.model.User
import com.zyrdev.simplerest.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DbDataSource : RoomDatabase() {

    abstract fun userDao(): UserDao
}