package com.example.uncovid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    val currentID: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}