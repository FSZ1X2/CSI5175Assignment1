package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.csi5175assignment1project_zixunxiang_yaqingzhu.service.BackGroundMusic
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    //create a user score file
    private fun userScoreTxt() {
        //create user folder they saves all user's file
        val path = "User"
        //create sdcard path
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), path
        )
        //check if sdcard path is exist or not
        //it not create one
        if (!sdCardDir.exists()) {
            if (!sdCardDir.mkdirs()) {
                try {
                    sdCardDir.createNewFile()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        //after create user folder, add user scores text file
        try {
            val saveFile = File(sdCardDir, "user_scores.txt")
            //check if the user scores text file is exist or not
            //if not create one
            if (!saveFile.exists()) {
                saveFile.createNewFile()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //create a question set for game A
    private fun gameAQuestionSet(){
        //create user folder they saves all user's file
        val path = "User"
        //create sdcard path
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), path
        )
        //check if sdcard path is exist or not
        //it not create one
        if (!sdCardDir.exists()) {
            if (!sdCardDir.mkdirs()) {
                try {
                    sdCardDir.createNewFile()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        //after create user folder, add user scores text file
        try {
            val saveFile = File(sdCardDir, "gameA_questions.txt")
            //check if the user scores text file is exist or not
            //if not create one
            if (!saveFile.exists()) {
                saveFile.createNewFile()
                //add all default questions to file
                saveFile.appendText("Who is the director of dune 2021?\n")
                saveFile.appendText("What band was Harry Styles in before his solo career?\n")
                saveFile.appendText("Which Disney Princess called Gus and Jaq friends?\n")
                saveFile.appendText("What is rapper P Diddy\\'s real name?\n")
                saveFile.appendText("Which two musicians collaborated on Another Way To Die, the theme song 007: Quantum of Solace?\n")
                saveFile.appendText("Which popular video game franchise has released games with the subtitles World At War and Black Ops?\n")
                saveFile.appendText("Which rock band was founded by Trent Reznor in 1988?\n")
                saveFile.appendText("For what movie did Tom Hanks score his first Academy Award nomination?\n")
                saveFile.appendText("What’s the name of the skyscraper in Die Hard?\n")
                saveFile.appendText("Who played park owner John Hammond in Jurassic Park?\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //create an answer set for game A
    private fun gameAAnswerSet(){
        //create user folder they saves all user's file
        val path = "User"
        //create sdcard path
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), path
        )
        //check if sdcard path is exist or not
        //it not create one
        if (!sdCardDir.exists()) {
            if (!sdCardDir.mkdirs()) {
                try {
                    sdCardDir.createNewFile()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        //after create user folder, add user scores text file
        try {
            val saveFile = File(sdCardDir, "gameA_answers.txt")
            //check if the user scores text file is exist or not
            //if not create one
            if (!saveFile.exists()) {
                saveFile.createNewFile()
                //add all default questions to file
                saveFile.appendText("Denis Villeneuve\n")
                saveFile.appendText("One Direction\n")
                saveFile.appendText("Cinderella\n")
                saveFile.appendText("Sean Combs\n")
                saveFile.appendText("Alicia Keys and Jack White\n")
                saveFile.appendText("Call of Duty\n")
                saveFile.appendText("Nine Inch Nails\n")
                saveFile.appendText("Big\n")
                saveFile.appendText("Nakatomi Plaza\n")
                saveFile.appendText("Richard Attenborough\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initial user score file
        userScoreTxt()

        //initial questions and answers for game A
        gameAQuestionSet()
        gameAAnswerSet()

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