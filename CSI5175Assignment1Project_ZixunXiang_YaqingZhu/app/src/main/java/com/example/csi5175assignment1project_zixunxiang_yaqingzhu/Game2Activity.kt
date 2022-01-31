package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.absoluteValue

class Game2Activity : AppCompatActivity() {

    val start = 60000L
    var timer0 = start
    lateinit var countDownTimer: CountDownTimer

    private fun setTextTimer() {
        var m = (timer0 / 1000) / 60
        var s = (timer0 / 1000) % 60

        var format = String.format("%02d:%02d", m, s)

        val textView: TextView = findViewById(R.id.timer)
        textView.text = format
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timer0,1000){
            override fun onFinish() {
                Toast.makeText(this@Game2Activity, "Game End!", Toast.LENGTH_SHORT).show()
            }
            override fun onTick(millisUntilFinished: Long) {
                timer0 = millisUntilFinished
                setTextTimer()
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)
        setTextTimer()

        val startButton: Button = findViewById(R.id.start_button)
        startButton.setOnClickListener {
            startTimer()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_page, menu)
        val down: MenuItem = menu.findItem(R.id.action_settings)
        down.setOnMenuItemClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            false
        }
        val home: MenuItem = menu.findItem(R.id.action_backhome)
        home.setOnMenuItemClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            false
        }
        return true
    }
}