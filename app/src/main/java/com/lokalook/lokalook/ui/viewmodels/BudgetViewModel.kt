package com.lokalook.lokalook.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lokalook.lokalook.data.remote.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers

class BudgetViewModel : ViewModel() {

    fun fetchRecommendation(budget: Int, numPeople: Int) = liveData(Dispatchers.IO) {
        try {
            val response = RetrofitClient.instance.getRecommendation(budget, numPeople)
            if (response.isSuccessful && response.body() != null) {
                emit(response.body())
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }
}