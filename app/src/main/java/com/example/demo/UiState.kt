package com.example.demo

sealed class UiState {
    object Ideal:UiState()
    data class Success(val list:List<String>):UiState()
    data class Error(val errorMessage:String):UiState()
    object Loading:UiState()
}