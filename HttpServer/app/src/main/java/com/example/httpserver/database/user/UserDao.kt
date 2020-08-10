package com.example.httpserver.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("select * from user_table where nickName = :nickName")
    fun getUserByNickName(nickName: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserEntity) : Long

}