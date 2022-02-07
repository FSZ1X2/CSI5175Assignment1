package com.example.csi5175assignment1project_zixunxiang_yaqingzhu.ui.gameb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameBViewModel : ViewModel() {
    //instruction for what is game B
    private val _text = MutableLiveData<String>().apply {
        value = "In this game, you will see randomly appearing then disappearing animals. Please count how many animals you have seen in the screen."
    }
    val text: LiveData<String> = _text
}