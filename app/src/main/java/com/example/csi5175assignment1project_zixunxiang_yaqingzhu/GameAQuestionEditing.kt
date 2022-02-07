package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.util.*

class GameAQuestionEditing : AppCompatActivity() {

    //add question and answer to game A
    private fun addQuestion(newQuestion:String, newAnswer:String){
        //load score.txt file:
        //find sdcard direction first
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "User"
        )
        //Get the text file
        val question_file = File(sdCardDir, "gameA_questions.txt")
        val answer_file = File(sdCardDir, "gameA_answers.txt")
        //save to gameA_questions.txt file
        val newQuestionText = "$newQuestion\n"
        val newAnswerText = "$newAnswer\n"
        question_file.appendText(newQuestionText)
        answer_file.appendText(newAnswerText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamea_question_editing)

        //get user input fields
        val questionText: EditText = findViewById(R.id.question_field)
        val answerText: EditText = findViewById(R.id.answer_field)

        //button for adding new question with its' answer to the txt file
        val addButton: Button = findViewById(R.id.add_question)
        addButton.setOnClickListener(){
            //check if all required field is filled
            if(questionText.text.isNotEmpty() && answerText.text.isNotEmpty()){
                addQuestion(questionText.text.toString(), answerText.text.toString())
                questionText.text.clear()
                answerText.text.clear()
            }
            else{
                Toast.makeText(
                    this@GameAQuestionEditing,
                    "Please fill all fields!", Toast.LENGTH_SHORT
                ).show()
            }
        }

        //button for go back to homepage
        val leaveButton: Button = findViewById(R.id.leave_editing_button)
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