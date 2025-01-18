package com.example.sharecircle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, GroupEntity::class, ExpenseEntity::class, PaymentEntity::class, GroupMemberEntity::class, ExpenseDetailEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun expenseDAO(): ExpenseDAO
    abstract fun groupDAO(): GroupDAO
    abstract fun paymentDAO(): PaymentDAO
    abstract fun groupMemberDAO(): GroupMemberDAO
    abstract fun expenseDetailsDAO(): ExpenseDetailDAO

    companion object {

        //The value of a variable annotated with @Volatile is never cache.
        //R/W operations are the from/to main memory.
        @Volatile
        private var INSTANCE: MyDatabase? = null

        // if the INSTANCE is not null, return it, otherwise create a new database instance.
        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, MyDatabase::class.java, "sharecircle")
                    .build()
                    .also { INSTANCE = it }
            }

        }

        fun close() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}