package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
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

    //func for load all game A questions and posting
    private fun showQuestions(){
        //initialize a new layout inflater instance for result
        val resultPage: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = resultPage.inflate(R.layout.game1questionchecking,null)
        //initialize a popup window instance
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        //allocate the textView for showing
        val questionRecords = view.findViewById<TextView>(R.id.score_records)
        //load score.txt file:
        //find sdcard direction first
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "User"
        )
        //Get the text file
        val question_file = File(sdCardDir, "gameA_questions.txt")
        //add all text to TextView
        question_file.forEachLine {
            questionRecords.append(it+"\n")
        }
        //button for close all records
        val leaveButton = view.findViewById<Button>(R.id.leave_editing_button)
        leaveButton.setOnClickListener() {
            popupWindow.dismiss()
        }
        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(this.findViewById(R.id.questionEditing))
        popupWindow.showAtLocation(
            this.findViewById(R.id.questionEditing), // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
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
                //if any field is empty, require user to enter text
                Toast.makeText(
                    this@GameAQuestionEditing,
                    "Please fill all fields!", Toast.LENGTH_SHORT
                ).show()
            }
        }

        //button for check all questions for game A
        val checkButton: Button = findViewById(R.id.check_question)
        checkButton.setOnClickListener() {
            //pop up a window that shows all questions (without answer) saved in sdcard
            showQuestions()
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