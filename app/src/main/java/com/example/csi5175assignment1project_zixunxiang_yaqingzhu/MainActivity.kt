package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.service.BackGroundMusic


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //enter the game selecting scene by click the OK button on the main scene:
        //finds the View with the corresponding ID
        val entryButton: Button = findViewById(R.id.entryButton)
        //attaches a click listener to the Button
        entryButton.setOnClickListener{
            //request another activity to achieve the task
            startService(Intent(this, BackGroundMusic::class.java))
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}