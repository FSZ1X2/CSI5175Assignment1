package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome!\n\n" +
                "Please click the top-left menu to select the game you want to play!\n\n" +
                "Or choose one action you want to take below."
    }
    val text: LiveData<String> = _text
}