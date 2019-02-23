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

private const val QUIZREQUESTCODE = 12345

class MainActivity : AppCompatActivity() , OnEditorActionListener{
    private lateinit var userInfo : UserInformation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Age_Edit.setOnEditorActionListener(this)
        userInfo = UserInformation(this)
        Quiz_Button.setOnClickListener{
            updateUserInformation()
            val quizIntent = Intent(this, QuizActivity::class.java)
            startActivityForResult(quizIntent, QUIZREQUESTCODE)
        }
    }

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

    private fun updateUserInformation(){
        val newFirstName = First_Name_Edit.text.toString()
        val newLastName = Last_Name_Edit.text.toString()
        val newNickName = Nick_Name_Edit.text.toString()
        val newAge = Age_Edit.text.toString()
        userInfo.update(newFirstName, newLastName, newNickName, newAge)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("QUIZ", "HERE?")
        if (requestCode != QUIZREQUESTCODE) {
            Log.i("QUIZ", "Failed")
            return
        }
        when (resultCode) {
            Activity.RESULT_OK -> {
                val quizResult = data?.getIntExtra("result", -1)
                Log.i("QUIZ", "Quiz Result: " + quizResult.toString())
            }
            Activity.RESULT_CANCELED ->
                Log.i("QUIZ", "cancel")
        }
    }
}