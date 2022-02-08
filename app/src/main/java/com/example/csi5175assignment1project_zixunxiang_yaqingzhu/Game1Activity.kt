package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.util.*


class Game1Activity : AppCompatActivity() {

    var questionNo = 1 //the current number of question
    var questionAnswered = 0 //how many questions user have answered
    var finalscore = 0

    //var for load game A info
    var totalSize = 0
    var allQuestionsList: MutableList<String> = mutableListOf<String>()
    var allAnswersList: MutableList<String> = mutableListOf<String>()

    //list saves 5 questions that randomly selected for the question set
    var randomQuestionList: MutableList<String> = mutableListOf<String>()
    //saved correct answers for questions
    var correctAnswerList: MutableList<String> = mutableListOf<String>()

    //saved all user's answer
    var userAnswerList: MutableList<String> = mutableListOf<String>()

    //saved all selected choices
    var choiceList: MutableList<String> = mutableListOf<String>()

    //get questions set and answers set from file
    private fun loadGameA(){
        //load score.txt file:
        //find sdcard direction first
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "User"
        )
        //Get the text file
        val file_quesiton = File(sdCardDir, "gameA_questions.txt")
        val file_answer = File(sdCardDir, "gameA_answers.txt")
        //save questions and answers to lists
        if(file_quesiton.length().toInt()!=0){
            file_quesiton.forEachLine {
                //load all questions save on sdcard for game A
                allQuestionsList.add(it)
            }
        }
        if(file_answer.length().toInt()!=0){
            file_answer.forEachLine {
                //load all answers save on sdcard for game A
                allAnswersList.add(it)
            }
        }
        //the size and item order for two file should be the same
        //AKA: the correct answer for allQuestionsList[0] should be allAnswersList[0]
        //save total number of questions (also the number of answers)
        totalSize = allQuestionsList.size
    }

    //get 5 random selected question and save to list
    private fun selectQfromset(){
        while (randomQuestionList.size != 5) {
            val qNumber = (0 until totalSize).random()
            //make sure no duplicate questions
            if(!randomQuestionList.any{ it == allQuestionsList[qNumber] }){
                //add randomly selected question with its correct answer into Lists
                randomQuestionList.add(allQuestionsList[qNumber])
                correctAnswerList.add(allAnswersList[qNumber])
            }
        }
    }

    //clear up game A
    private fun clearParameter(){
        //reset all var
        questionNo = 1
        questionAnswered = 0
        finalscore = 0
        //reset game related List
        userAnswerList.clear()
        randomQuestionList.clear()
        choiceList.clear()
        correctAnswerList.clear()
    }

    //show how many questions left
    private fun setQuestionNumber() {
        val questionView: TextView = findViewById(R.id.question_number)
        var format = String.format("%d", questionNo)
        //set the current question number user at
        questionView.text = "$format/5"
    }

    //change questions based on random selection
    private fun setQuestionText(qNo:Int) { //, qList: List<Int>
        //setup the question describe text
        val question: TextView = findViewById(R.id.question_text)
        question.text = randomQuestionList[qNo]//getString(resourceId)

        //setup the multi-choice text
        //first select 2 wrong answer
        val aNumber: MutableSet<String> = mutableSetOf()
        while (aNumber.size < 2)
        {
            val item = allAnswersList[(0 until allAnswersList.size).random()]
            if(item != correctAnswerList[qNo]) aNumber.add(item)
        }
        //then save the correct answer to the list
        aNumber.add(correctAnswerList[qNo])
        //finally fill selected answer into choice randomly
        val randomAList = aNumber.toList()
        Collections.shuffle(randomAList)
        val choiceA1: TextView = findViewById(R.id.choice1)
        choiceA1.text = randomAList[0]
        val choiceA2: TextView = findViewById(R.id.choice2)
        choiceA2.text = randomAList[1]
        val choiceA3: TextView = findViewById(R.id.choice3)
        choiceA3.text = randomAList[2]
        //also save all choice to list for overview
        choiceList.add(randomAList[0])
        choiceList.add(randomAList[1])
        choiceList.add(randomAList[2])
    }

    //add font to text
    private fun fontEditor(qNo:Int, item:TextView){
        //check if choice have been selected by user
        if(item.text == userAnswerList[qNo]) item.typeface = Typeface.DEFAULT_BOLD;
        //check if choice is correct or not
        if(item.text == correctAnswerList[qNo]) item.setTextColor(ContextCompat.getColor(this,R.color.green))
        //check if user selected choice is wrong
        if(item.text == userAnswerList[qNo] && item.text != correctAnswerList[qNo])
            item.setTextColor(ContextCompat.getColor(this,R.color.red))
    }

    //save score
    private fun saveCurrentScore(){
        //get current date info
        val currentDate = Calendar.getInstance().time
        //generate the string that save the user score info
        val savedScore = "Your game A score on $currentDate is: $finalscore\n"
        //load score.txt file:
        //find sdcard direction first
        val sdCardDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "User"
        )
        //Get the text file
        val myfile = File(sdCardDir, "user_scores.txt")
        myfile.appendText(savedScore)
        Toast.makeText(this, "Score saved!", Toast.LENGTH_SHORT).show()
    }

    //result window
    private fun showResult() {
        //initialize a new layout inflater instance for result
        val resultPage: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = resultPage.inflate(R.layout.game1result,null)
        //initialize a popup window instance
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        //show scores
        val scoreBoard = view.findViewById<TextView>(R.id.final_score)
        scoreBoard.text = "Your Score: \n$finalscore"
        //show questions with its choices
        val questionField1 = view.findViewById<TextView>(R.id.question_text0)
        questionField1.text = "Q1. " + randomQuestionList[0]
        val question1choice1 = view.findViewById<TextView>(R.id.question0_choice0)
        question1choice1.text = choiceList[0]
        fontEditor(0, question1choice1)
        question1choice1.text = "a. " + question1choice1.text
        val question1choice2 = view.findViewById<TextView>(R.id.question0_choice1)
        question1choice2.text = choiceList[1]
        fontEditor(0, question1choice2)
        question1choice2.text = "b. " + question1choice2.text
        val question1choice3 = view.findViewById<TextView>(R.id.question0_choice2)
        question1choice3.text = choiceList[2]
        fontEditor(0, question1choice3)
        question1choice3.text = "c. " + question1choice3.text
        val questionField2 = view.findViewById<TextView>(R.id.question_text1)
        questionField2.text = "Q2. " + randomQuestionList[1]
        val question2choice1 = view.findViewById<TextView>(R.id.question1_choice0)
        question2choice1.text = choiceList[3]
        fontEditor(1, question2choice1)
        question2choice1.text = "a. " + question2choice1.text
        val question2choice2 = view.findViewById<TextView>(R.id.question1_choice1)
        question2choice2.text = choiceList[4]
        fontEditor(1, question2choice2)
        question2choice2.text = "b. " + question2choice2.text
        val question2choice3 = view.findViewById<TextView>(R.id.question1_choice2)
        question2choice3.text = choiceList[5]
        fontEditor(1, question2choice3)
        question2choice3.text = "c. " + question2choice3.text
        val questionField3 = view.findViewById<TextView>(R.id.question_text2)
        questionField3.text = "Q3. " + randomQuestionList[2]
        val question3choice1 = view.findViewById<TextView>(R.id.question2_choice0)
        question3choice1.text = choiceList[6]
        fontEditor(2, question3choice1)
        question3choice1.text = "a. " + question3choice1.text
        val question3choice2 = view.findViewById<TextView>(R.id.question2_choice1)
        question3choice2.text = choiceList[7]
        fontEditor(2, question3choice2)
        question3choice2.text = "b. " + question3choice2.text
        val question3choice3 = view.findViewById<TextView>(R.id.question2_choice2)
        question3choice3.text = choiceList[8]
        fontEditor(2, question3choice3)
        question3choice3.text = "c. " + question3choice3.text
        val questionField4 = view.findViewById<TextView>(R.id.question_text3)
        questionField4.text = "Q4. " + randomQuestionList[3]
        val question4choice1 = view.findViewById<TextView>(R.id.question3_choice0)
        question4choice1.text = choiceList[9]
        fontEditor(3, question4choice1)
        question4choice1.text = "a. " + question4choice1.text
        val question4choice2 = view.findViewById<TextView>(R.id.question3_choice1)
        question4choice2.text = choiceList[10]
        fontEditor(3, question4choice2)
        question4choice2.text = "b. " + question4choice2.text
        val question4choice3 = view.findViewById<TextView>(R.id.question3_choice2)
        question4choice3.text = choiceList[11]
        fontEditor(3, question4choice3)
        question4choice3.text = "c. " + question4choice3.text
        val questionField5 = view.findViewById<TextView>(R.id.question_text4)
        questionField5.text = "Q5. " + randomQuestionList[4]
        val question5choice1 = view.findViewById<TextView>(R.id.question4_choice0)
        question5choice1.text = choiceList[12]
        fontEditor(4, question5choice1)
        question5choice1.text = "a. " + question5choice1.text
        val question5choice2 = view.findViewById<TextView>(R.id.question4_choice1)
        question5choice2.text = choiceList[13]
        fontEditor(4, question5choice2)
        question5choice2.text = "b. " + question5choice2.text
        val question5choice3 = view.findViewById<TextView>(R.id.question4_choice2)
        question5choice3.text = choiceList[14]
        fontEditor(4, question5choice3)
        question5choice3.text = "c. " + question5choice3.text

        //button for save the score from the game
        val saveButton = view.findViewById<Button>(R.id.save_gameAscore)
        saveButton.setOnClickListener(){
            //save score
            saveCurrentScore()
            //reset game
            clearParameter()
        }
        //button for reset the game from the beginning
        val restartButton = view.findViewById<Button>(R.id.reset_gameA)
        restartButton.setOnClickListener(){
            //reset game
            clearParameter()
            setQuestionNumber()
            selectQfromset()
            setQuestionText(0)
            //close pop up window
            popupWindow.dismiss()
        }
        //button for go back to homepage
        val leaveButton = view.findViewById<Button>(R.id.leave_button)
        leaveButton.setOnClickListener() {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(this.findViewById(R.id.questionBackground))
        popupWindow.showAtLocation(
            this.findViewById(R.id.questionBackground), // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1)
        //load files for game A
        loadGameA()
        //setup the first question
        setQuestionNumber()
        //setup background color
        //change screen color to random color
        val myView: View = findViewById(R.id.questionBackground)
        val rnd = Random()
        var color = Color.argb(
            130, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)
        )
        myView.setBackgroundColor(color)
        //select 5 random questions from the question set
        selectQfromset()
        //set up first question
        setQuestionText(0)

        //get next button and radio button
        val nextButton: Button = findViewById(R.id.confirm_button)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        nextButton.setOnClickListener {
            //check if user have answered the question or not
            if (radioGroup.checkedRadioButtonId == -1)
            {
                Toast.makeText(
                    this@Game1Activity,
                    "Please answer the question!", Toast.LENGTH_SHORT
                ).show()
            }
            else {
                //save user's answer
                questionAnswered += 1
                //get element from view
                val radioButtonID = radioGroup.checkedRadioButtonId
                val selectedButton: RadioButton = findViewById(radioButtonID);
                userAnswerList.add(selectedButton.text as String)
                //update score
                if (userAnswerList[questionNo - 1] == correctAnswerList[questionNo - 1])
                    finalscore += 1
                //clear up selected options
                radioGroup.clearCheck()
                //show the process of the game
                if (questionNo < 5) questionNo += 1
                //change questions
                setQuestionNumber()
                setQuestionText(questionNo-1)//, randomQList.toList()
                //change screen color to random color
                color = Color.argb(
                    130, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)
                )
                myView.setBackgroundColor(color)
                //check if game is end
                if(userAnswerList.size == 5) showResult()
            }
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