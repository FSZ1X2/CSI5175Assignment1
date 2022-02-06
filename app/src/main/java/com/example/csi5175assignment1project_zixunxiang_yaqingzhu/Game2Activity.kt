package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class Game2Activity : AppCompatActivity() {

    val start = 60000L
    var timer0 = start
    val handler = Handler()
    lateinit var countDownTimer: CountDownTimer

    private fun setTextTimer() {
        var m = (timer0 / 1000) / 60
        var s = (timer0 / 1000) % 60

        var format = String.format("%02d:%02d", m, s)

        val textView: TextView = findViewById(R.id.timer)
        textView.text = format
    }

    private fun startTimer() {
        //game timer's functions
        countDownTimer = object : CountDownTimer(timer0,1000){
            override fun onFinish() {
                Toast.makeText(this@Game2Activity, "Game End!", Toast.LENGTH_SHORT).show()
            }
            override fun onTick(millisUntilFinished: Long) {
                timer0 = millisUntilFinished
                setTextTimer()
                handler.postDelayed({
                    imageRandom()
                }, ((1 .. 3).random()*1000).toLong())
            }
        }.start()
    }

    //func for randomly chose a position and a image resource for the imageView
    private fun imageRandom(){
        //get current view and imageView component
        val view = findViewById<View>(R.id.game_zone) as ConstraintLayout
        val img: ImageView = findViewById(R.id.random_image)
        //clear up first
        
        //check how many saved images the app currently has
        val imagesNum = getString(R.string.imgsize).toInt()
        //get image height and width
        val height: Int = img.height
        val width: Int = img.width
        //chose one randomly
        val choice = (1..imagesNum).random()
//        val choice = (1..imagesNum).random()
        //calculate a random top/left pos for imageView component
        val top: Int = (0 .. (view.measuredHeight - height)).random()
        val left: Int = (0 .. (view.measuredWidth - width)).random()
        //set position
        img.left = left
        img.top = top
        img.right = left + width
        img.bottom = top + height
        //reset the imageView using this source
        img.drawable.level = choice
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)

        //initial timer text
        setTextTimer()
        //click the start button to start the game
        val startButton: Button = findViewById(R.id.start_button)
        startButton.setOnClickListener {
            //hide the start button
            startButton.visibility = View.GONE;
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