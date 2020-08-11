package com.example.httpserver.database.user

import androidx.room.*

@Dao
interface UserDao {

    @Query("select * from user_table where nickname = :nickName")
    fun getUserByNickName(nickName: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserEntity)
}