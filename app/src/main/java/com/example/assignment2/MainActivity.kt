/*
CS 646
William Ritchie
Assignment 2
2/24/19
 */
package com.example.assignment2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import kotlinx.android.synthetic.main.activity_main.*

// A code for the quiz activity so that main can know it is the quiz activity returning a result
private const val QUIZREQUESTCODE = 12345

class MainActivity : AppCompatActivity() , OnEditorActionListener{
    private lateinit var userInfo : UserInformation // Used to read/write info to/from the user's info file

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Listens for when user is finished entering their info then hides the keyboard
        Age_Edit.setOnEditorActionListener(this)

        userInfo = UserInformation(this)

        // If there is already information on the user then populate that data to the respective edit views
        First_Name_Edit.setText(userInfo.getFirstName())
        Last_Name_Edit.setText(userInfo.getLastName())
        Nick_Name_Edit.setText(userInfo.getNickName())
        Age_Edit.setText(userInfo.getAge())
        Result.text = String.format("Quiz Result: ${userInfo.getScore()}%%")

        Quiz_Button.setOnClickListener{
            updateUserInformation()
            val quizIntent = Intent(this, QuizActivity::class.java)
            startActivityForResult(quizIntent, QUIZREQUESTCODE)
        }
    }

    // This is what gets called when the age_edit view finish's, essentially just hides the Keyboard
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var handled = false
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            handled = true
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(v?.windowToken, 0)
            updateUserInformation()
        }
        return handled
    }

    // Note: there is no check for if the information is an empty string, this app allows empty strings to be a valid input
    private fun updateUserInformation(){
        val newFirstName = First_Name_Edit.text.toString()
        val newLastName = Last_Name_Edit.text.toString()
        val newNickName = Nick_Name_Edit.text.toString()
        val newAge = Age_Edit.text.toString()
        userInfo.update(newFirstName, newLastName, newNickName, newAge)
    }

    // This is what gets called if there is a result to this activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != QUIZREQUESTCODE) {
            Log.i("QUIZ", "Failed")
            return
        }
        when (resultCode) {
            Activity.RESULT_OK -> {
                val quizResult = data?.getIntExtra("result", -1)
                var resultPercent = 0.0f
                if (quizResult != null) {
                    resultPercent = quizResult.toFloat() * 100.0f / 4.0f
                }
                Log.i("QUIZ", "Quiz Result: " + quizResult.toString())
                userInfo.setScore(quizResult.toString())
                Result.text = String.format("Quiz Result: $resultPercent%%")
            }
            Activity.RESULT_CANCELED ->
                Log.i("QUIZ", "cancel")
        }
    }
}