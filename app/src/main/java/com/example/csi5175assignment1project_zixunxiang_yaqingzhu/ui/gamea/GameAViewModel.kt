package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.gamea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameAViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "In this game, you will get 5 multiple choice questions. One at a time. Please answer them."
    }
    val text: LiveData<String> = _text
}