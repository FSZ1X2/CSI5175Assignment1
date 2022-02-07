package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class UserScorePage : AppCompatActivity() {

    //func for read all save scores form file
    private fun readTxt(){
        //allocate the textView for showing
        val scoreRecords: TextView = findViewById(R.id.score_records)
        //load score.txt file:
        //find sdcard direction first
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "User"
        )
        //Get the text file
        val myfile = File(sdCardDir, "user_scores.txt")
        //check if the app has user records or not
        if(myfile.length().toInt() == 0) {
            scoreRecords.text = getString(R.string.none_score)
        }
        else{
            myfile.forEachLine {
                scoreRecords.append(it+"\n")
            }
        }
    }

    //func for erase all scores record
    private fun clearRecords(){
        //load score.txt file:
        //find sdcard direction first
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "User"
        )
        //Get the text file
        val myfile = File(sdCardDir, "user_scores.txt")
        myfile.writeText("")
        //update textView
        val scoreRecords: TextView = findViewById(R.id.score_records)
        scoreRecords.text = getString(R.string.none_score)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_scorepage)

        //show all records for the user
        readTxt()

        //button for clear up all save score
        val clearButton: Button = findViewById(R.id.clear_record)
        clearButton.setOnClickListener() {
            clearRecords()
        }

        //button for go back to homepage
        val leaveButton: Button = findViewById(R.id.leave_scorePage_button)
        leaveButton.setOnClickListener() {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    //top menu bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // back to main or homepage menu setting
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