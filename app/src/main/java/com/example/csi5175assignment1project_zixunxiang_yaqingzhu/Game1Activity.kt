package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*


class Game1Activity : AppCompatActivity() {

    var questionNo = 1 //the current number of question
    var questionAnswered = 0 //how many questions user have answered
    var finalscore = 0

    var userAnswerList: MutableList<String> = mutableListOf<String>() //saved all user's answer
    var questionList: MutableList<Int> = mutableListOf<Int>() //saved the order of questions by id
    var randomQList: MutableSet<Int> = mutableSetOf<Int>() //saved randomly selected questions id
    var choiceList: MutableList<Int> = mutableListOf<Int>() //saved all selected choices by id
    var correctAnswerList: MutableList<String> = mutableListOf<String>() //saved correct answers for questions

    //get 5 random select question as a list
    private fun selectQfromset(): MutableSet<Int>{
        val qSize : Int = getString(R.string.questionsize).toInt()
        val qNumber: MutableSet<Int> = mutableSetOf()
        while (qNumber.size < 6) { qNumber.add((1..qSize).random())}
        return qNumber
    }

    //clear up game A
    private fun clearParameter(){
        questionNo = 1
        questionAnswered = 0
        finalscore = 0

        userAnswerList.clear()
        questionList.clear()
        randomQList.clear()
        choiceList.clear()
        correctAnswerList.clear()
    }

    //show how many questions left
    private fun setQuestionNumber() {
        val questionView: TextView = findViewById(R.id.question_number)
        var format = String.format("%d", questionNo)
        questionView.text = "$format/5"
    }

    //change questions based on random selection
    private fun setQuestionText(qNo:Int, qList: List<Int>) {
        //setup the question describe text
        var name = String.format("question%d", qList[qNo])//.toInt()
        val resourceId = this.resources.getIdentifier(name, "string", this.packageName)
        val question: TextView = findViewById(R.id.question_text)
        question.text = getString(resourceId)
        questionList.add(resourceId)

        //setup the multi-choice text
        //first select true answer
        var trueAnswer = String.format("answer%d", qList[qNo])
        val trueAnswerID = this.resources.getIdentifier(trueAnswer, "string", this.packageName)
        //then find the other two wrong answers
        val aSize : Int = getString(R.string.answersize).toInt()
        val aNumber: MutableSet<Int> = mutableSetOf()
        aNumber.add(trueAnswerID)
        while (aNumber.size < 3)
        {
            aNumber.add(this.resources.getIdentifier(
                String.format("answer%d", (1..aSize).random()), "string", this.packageName))
        }
        val randomAList = aNumber.toList()
        //finally fill selected answer into choice randomly
        Collections.shuffle(randomAList)
        val choiceA1: TextView = findViewById(R.id.choice1)
        choiceA1.text = getString(randomAList[0])
        val choiceA2: TextView = findViewById(R.id.choice2)
        choiceA2.text = getString(randomAList[1])
        val choiceA3: TextView = findViewById(R.id.choice3)
        choiceA3.text = getString(randomAList[2])
        //also save the correct answer to the list
        if(randomAList[0] == trueAnswerID) correctAnswerList.add(getString(randomAList[0]))
        if(randomAList[1] == trueAnswerID) correctAnswerList.add(getString(randomAList[1]))
        if(randomAList[2] == trueAnswerID) correctAnswerList.add(getString(randomAList[2]))
        choiceList.add(randomAList[0])
        choiceList.add(randomAList[1])
        choiceList.add(randomAList[2])
    }

    //add font to text
    private fun fontEditor(qNo:Int, item:TextView){
        if(item.text == userAnswerList[qNo]) item.typeface = Typeface.DEFAULT_BOLD;
        if(item.text == correctAnswerList[qNo]) item.setTextColor(ContextCompat.getColor(this,R.color.green))
        if(item.text == userAnswerList[qNo] && item.text != correctAnswerList[qNo])
            item.setTextColor(ContextCompat.getColor(this,R.color.red))
    }

    //result window
    private fun showResult() {
        //initialize a new layout inflater instance for result
        val resultPage: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = resultPage.inflate(R.layout.game1result,null)
        //initialize a popup window instance
        val popupWindow = PopupWindow( view,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        //show scores
        val scoreBoard = view.findViewById<TextView>(R.id.final_score)
        scoreBoard.text = "Your Score: \n$finalscore"
        //show questions and choices
        val questionField1 = view.findViewById<TextView>(R.id.question_text0)
        questionField1.text = "Q1. " + getString(questionList[0])
        val question1choice1 = view.findViewById<TextView>(R.id.question0_choice0)
        question1choice1.text = getString(choiceList[0])
        fontEditor(0, question1choice1)
        question1choice1.text = "a. " + question1choice1.text
        val question1choice2 = view.findViewById<TextView>(R.id.question0_choice1)
        question1choice2.text = getString(choiceList[1])
        fontEditor(0, question1choice2)
        question1choice2.text = "b. " + question1choice2.text
        val question1choice3 = view.findViewById<TextView>(R.id.question0_choice2)
        question1choice3.text = getString(choiceList[2])
        fontEditor(0, question1choice3)
        question1choice3.text = "c. " + question1choice3.text
        val questionField2 = view.findViewById<TextView>(R.id.question_text1)
        questionField2.text = "Q2. " + getString(questionList[1])
        val question2choice1 = view.findViewById<TextView>(R.id.question1_choice0)
        question2choice1.text = getString(choiceList[3])
        fontEditor(1, question2choice1)
        question2choice1.text = "a. " + question2choice1.text
        val question2choice2 = view.findViewById<TextView>(R.id.question1_choice1)
        question2choice2.text = getString(choiceList[4])
        fontEditor(1, question2choice2)
        question2choice2.text = "b. " + question2choice2.text
        val question2choice3 = view.findViewById<TextView>(R.id.question1_choice2)
        question2choice3.text = getString(choiceList[5])
        fontEditor(1, question2choice3)
        question2choice3.text = "c. " + question2choice3.text
        val questionField3 = view.findViewById<TextView>(R.id.question_text2)
        questionField3.text = "Q3. " + getString(questionList[2])
        val question3choice1 = view.findViewById<TextView>(R.id.question2_choice0)
        question3choice1.text = getString(choiceList[6])
        fontEditor(2, question3choice1)
        question3choice1.text = "a. " + question3choice1.text
        val question3choice2 = view.findViewById<TextView>(R.id.question2_choice1)
        question3choice2.text = getString(choiceList[7])
        fontEditor(2, question3choice2)
        question3choice2.text = "b. " + question3choice2.text
        val question3choice3 = view.findViewById<TextView>(R.id.question2_choice2)
        question3choice3.text = getString(choiceList[8])
        fontEditor(2, question3choice3)
        question3choice3.text = "c. " + question3choice3.text
        val questionField4 = view.findViewById<TextView>(R.id.question_text3)
        questionField4.text = "Q4. " + getString(questionList[3])
        val question4choice1 = view.findViewById<TextView>(R.id.question3_choice0)
        question4choice1.text = getString(choiceList[9])
        fontEditor(3, question4choice1)
        question4choice1.text = "a. " + question4choice1.text
        val question4choice2 = view.findViewById<TextView>(R.id.question3_choice1)
        question4choice2.text = getString(choiceList[10])
        fontEditor(3, question4choice2)
        question4choice2.text = "b. " + question4choice2.text
        val question4choice3 = view.findViewById<TextView>(R.id.question3_choice2)
        question4choice3.text = getString(choiceList[11])
        fontEditor(3, question4choice3)
        question4choice3.text = "c. " + question4choice3.text
        val questionField5 = view.findViewById<TextView>(R.id.question_text4)
        questionField5.text = "Q5. " + getString(questionList[4])
        val question5choice1 = view.findViewById<TextView>(R.id.question4_choice0)
        question5choice1.text = getString(choiceList[12])
        fontEditor(4, question5choice1)
        question5choice1.text = "a. " + question5choice1.text
        val question5choice2 = view.findViewById<TextView>(R.id.question4_choice1)
        question5choice2.text = getString(choiceList[13])
        fontEditor(4, question5choice2)
        question5choice2.text = "b. " + question5choice2.text
        val question5choice3 = view.findViewById<TextView>(R.id.question4_choice2)
        question5choice3.text = getString(choiceList[14])
        fontEditor(4, question5choice3)
        question5choice3.text = "c. " + question5choice3.text

        //button for reset the game from the beginning
        val restartButton = view.findViewById<Button>(R.id.reset_gameA)
        restartButton.setOnClickListener(){
            clearParameter()
            setQuestionNumber()
            randomQList = selectQfromset()//qNumber.toList()
            setQuestionText(1, randomQList.toList())
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
        randomQList = selectQfromset()//qNumber.toList()
        setQuestionText(1, randomQList.toList())

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
                setQuestionText(questionNo, randomQList.toList())
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