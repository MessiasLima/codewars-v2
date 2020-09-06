package com.messiasjunior.codewarsv2.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.messiasjunior.codewarsv2.datasource.challenge.ChallengeDao
import com.messiasjunior.codewarsv2.datasource.user.UserDao
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.model.UserCompletedChallenge
import com.messiasjunior.codewarsv2.util.typeconverter.ListStringTypeConverter

@Database(
    entities = [User::class, Challenge::class, UserCompletedChallenge::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringTypeConverter::class)
abstract class CodewarsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun challengeDao(): ChallengeDao
}
