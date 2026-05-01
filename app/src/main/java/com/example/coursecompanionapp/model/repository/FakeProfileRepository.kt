package com.example.coursecompanionapp.model.repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepository @Inject constructor() {

    suspend fun getProfile(shouldFail: Boolean = false): ProfileData = withContext(Dispatchers.IO) {
        delay(2000)
        if (shouldFail) throw IllegalStateException("Failed to load profile.")
        ProfileData(
            name = "Medina Alić",
            email = "medina@stu.ibu.edu.ba",
            university = "International Burch University",
            department = "Software Engineering"
        )
    }
}

data class ProfileData(
    val name: String,
    val email: String,
    val university: String,
    val department: String
)