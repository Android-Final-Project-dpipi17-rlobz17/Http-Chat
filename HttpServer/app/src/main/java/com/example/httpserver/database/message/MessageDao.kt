<<<<<<< HEAD
//package com.example.httpserver.database.message
//
//import androidx.room.Dao
//
//@Dao
//interface MessageDao {
//
//}
=======
package com.example.httpserver.database.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.httpserver.database.user.UserEntity

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: MessageEntity) : Long
}
>>>>>>> 8f0a334d4582ef584f8722a78fa47ce8b68e7680
