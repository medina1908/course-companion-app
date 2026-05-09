package com.example.coursecompanionapp.model.repository


import com.example.coursecompanionapp.model.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<UserEntity>>
    fun getUserById(id: Int): Flow<UserEntity?>
    suspend fun getUserByEmail(email: String): UserEntity?
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?
    suspend fun insertUser(user: UserEntity): Long
    suspend fun updateUser(user: UserEntity)
    suspend fun deleteUser(user: UserEntity)
}