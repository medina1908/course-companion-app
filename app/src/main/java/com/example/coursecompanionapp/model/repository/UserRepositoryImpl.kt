package com.example.coursecompanionapp.model.repository

import com.example.coursecompanionapp.model.data.local.dao.UserDao
import com.example.coursecompanionapp.model.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    override fun getUserById(id: Int): Flow<UserEntity?> {
        return userDao.getUserById(id)
    }

    override suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserByEmailAndPassword(
        email: String,
        password: String
    ): UserEntity? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }
}