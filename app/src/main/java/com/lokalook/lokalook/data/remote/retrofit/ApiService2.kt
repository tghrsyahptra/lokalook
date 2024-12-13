package com.lokalook.lokalook.data.remote.retrofit


import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import com.lokalook.lokalook.data.remote.response.RecommendationResponse

interface ApiService2 {
    @GET("rekomendasi")
    suspend fun getRecommendation(
        @Query("budget") budget: Int,
        @Query("num_people") numPeople: Int
    ): Response<RecommendationResponse>
}