package com.mayank.mvvmroomdemo.data

import android.app.Application

class UserRepository(application: Application) {

    private val database = MyDatabase.getInstance(application)
    private val userDao: UserDao = database.userDao

    suspend fun getUser(username: String): User {
        return userDao.getUser(username)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

}