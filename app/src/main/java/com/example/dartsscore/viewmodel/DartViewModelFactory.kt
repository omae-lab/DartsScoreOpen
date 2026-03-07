package com.example.dartsscore.viewmodel
/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dartsscore.data.dao.ThrowDao
import com.example.dartsscore.data.dao.GameDao

class DartViewModelFactory(
    private val throwDao: ThrowDao,
    private val gameDao: GameDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DartViewModel(throwDao, gameDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/