package com.messiasjunior.codewarsv2.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.messiasjunior.codewarsv2.datasource.user.UserDao
import com.messiasjunior.codewarsv2.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class CodewarsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
