package com.example.myapplication.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Models
data class User(
    val id: String?,
    val name: String,
    val email: String,
    val role: String,
    val idNumber: String,
    val department: String
)

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val user: User
)

data class ProjectRequest(
    val name: String,
    val description: String,
    val domain: String,
    val leaderId: String,
    val mentorId: String?
)

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val domain: String,
    val status: String,
    val progress: Int
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: Map<String, String>): Response<Any>

    @POST("projects")
    suspend fun createProject(@Body request: ProjectRequest): Response<Project>

    @GET("projects")
    suspend fun getProjects(
        @Query("role") role: String,
        @Query("userId") userId: String
    ): Response<List<Project>>
}
