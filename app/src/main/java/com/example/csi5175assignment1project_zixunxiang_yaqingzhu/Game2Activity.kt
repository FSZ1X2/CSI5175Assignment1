package com.example.csi5175assignment1project_zixunxiang_yaqingzhu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File
import java.util.*


class Game2Activity : AppCompatActivity() {

    val start = 60000L //countdown time
    var timer0 = start
    var animalTimer = 1 //var for change animal each 1-3s
    var animalList: MutableList<Int> = mutableListOf()
    var finalScore = 0.0f //save the calculated score for game B
    var savedScore = 0.0f //save the calculated score from game B to scores file
    lateinit var countDownTimer: CountDownTimer

    //change the text to show how many time left
    private fun setTextTimer() {
        var m = (timer0 / 1000) / 60
        var s = (timer0 / 1000) % 60

        var format = String.format("%02d:%02d", m, s)

        //update text for timer for users
        val textView: TextView = findViewById(R.id.timer)
        textView.text = format
    }

    //main timer function
    private fun startTimer() {
        //game timer's functions
        countDownTimer = object : CountDownTimer(timer0,1000){
            override fun onFinish() {
                Toast.makeText(this@Game2Activity, "Game End!", Toast.LENGTH_SHORT).show()
                animalList = animalList.distinct() as MutableList<Int>
                openQuestionandResult()
            }
            override fun onTick(millisUntilFinished: Long) {
                timer0 = millisUntilFinished
                setTextTimer()
                //check if the image of animal should be change or not
                val change = (0 .. 1).random()
                //if the animal have not been changed after 3s change it
                if(change == 1 || animalTimer == 3 ) {
                    animalTimer = 1
                    imageRandom()
                }
                else {
                    animalTimer += 1
                }
            }
        }.start()
    }

    //func for randomly chose a position and a image resource for the imageView
    private fun imageRandom(){
        //get current view and imageView component
        val view = findViewById<View>(R.id.game_zone) as ConstraintLayout
        val img: ImageView = findViewById(R.id.random_image)
        //check how many saved images the app currently has
        val imagesNum = getString(R.string.imgsize).toInt()
        //get image height and width
        val height: Int = img.height
        val width: Int = img.width
        //chose one randomly
        val choice = (1..imagesNum).random()
        animalList.add(choice)
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
        animalList.add(choice)
    }

    //func for clear up game state
    private fun gameReset(){
        //reset timer and var
        timer0 = start
        animalTimer = 1
        animalList.clear()
        finalScore = 0.0f
        setTextTimer()
        //reset image and its position
        val view = findViewById<View>(R.id.game_zone) as ConstraintLayout
        val img: ImageView = findViewById(R.id.random_image)
        img.drawable.level = 0
        val height: Int = img.height
        val width: Int = img.width
        val top: Int = (view.measuredHeight - height)/2
        val left: Int = (view.measuredWidth - width)/2
        img.left = left
        img.top = top
        img.right = left + width
        img.bottom = top + height
        //make start button visible again
        val startButton: Button = findViewById(R.id.start_button)
        startButton.visibility = View.VISIBLE
    }

    //save score
    private fun saveCurrentScore(){
        //get current date info
        val currentDate = Calendar.getInstance().time
        //generate the string that save the user score info
        //format the savedScore to make it looks better
        val formatScore = String.format("%.2f", savedScore)
        val savedScore = "Your game B score on $currentDate is: $formatScore\n"
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

    //ask user to answer how many animal and provide the score they got
    private fun openQuestionandResult() {
        //initialize a new layout inflater instance for result
        val questionPage: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = questionPage.inflate(R.layout.game2question, null)
        //initialize a popup window instance
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        popupWindow.isFocusable = true
        popupWindow.update()
        //get user input number from the edit text field
        val userInput = view.findViewById<EditText>(R.id.answer_enterField)
        //button for calculating the score
        val okButton = view.findViewById<Button>(R.id.confirm_gameBResult)
        okButton.setOnClickListener(){
            //check if user didn't enter an answer
            if(userInput.text.toString().trim().isEmpty()){
                Toast.makeText(this@Game2Activity, "Enter your answer!", Toast.LENGTH_SHORT).show()
            }
            else{
                //get input
                val userAnswer = userInput.text.toString().toFloat()
                //calculate final score
                if(animalList.size != 0) finalScore = userAnswer/animalList.size
                //disable user input
                userInput.isEnabled = false
                //hide question text
                val questionText = view.findViewById<TextView>(R.id.game2question_text)
                questionText.visibility = View.GONE
                //show score user got
                val scoreBoard = view.findViewById<TextView>(R.id.score_board)
                //format the finalScore to make it looks better
                val formatScore = String.format("%.2f", finalScore)
                scoreBoard.text = "Your final score is $formatScore\nCalculate by\nNo.of animals you reported/Actual animals"
            }
            //clear up the game after calculate
            savedScore = finalScore
            gameReset()
            //disable ok button
            okButton.isEnabled = false
        }
        //button for save the score from the game
        val saveButton = view.findViewById<Button>(R.id.save_gameBscore)
        saveButton.setOnClickListener(){
            //save score
            saveCurrentScore()
            //reset game
            gameReset()
        }
        //button for reset the game from the beginning
        val restartButton = view.findViewById<Button>(R.id.restart_gameB)
        restartButton.setOnClickListener(){
            //reset the timer and go back
            gameReset()
            //close pop up window
            popupWindow.dismiss()
        }
        //button for go back to homepage
        val leaveButton = view.findViewById<Button>(R.id.leavegameb_button)
        leaveButton.setOnClickListener() {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(this.findViewById(R.id.game_zone))
        popupWindow.showAtLocation(
            this.findViewById(R.id.game_zone), // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
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
            startButton.visibility = View.GONE
            startTimer()
        }
    }

    //top menu bar
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