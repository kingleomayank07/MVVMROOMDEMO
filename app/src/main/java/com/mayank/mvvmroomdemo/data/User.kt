package com.mayank.mvvmroomdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "username")
    val userName: String,

    @ColumnInfo(name = "password")
    val password: String
)