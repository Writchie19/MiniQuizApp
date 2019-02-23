package com.example.assignment2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import org.json.JSONObject
class MainActivity : AppCompatActivity() , OnEditorActionListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Age_Edit.setOnEditorActionListener(this)
        Quiz_Button.setOnClickListener{
            val quizIntent = Intent(this, QuizActivity::class.java)
            startActivity(quizIntent)
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var handled = false
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            handled = true
            val newFirstName = First_Name_Edit.text.toString()
            val newLastName = Last_Name_Edit.text.toString()
            val newNickName = Nick_Name_Edit.text.toString()
            val newAge = Age_Edit.text.toString()
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(v?.windowToken, 0)
            val userInfo = UserInformation(this)
            userInfo.update(newFirstName, newLastName, newNickName, newAge)
        }
        return handled
    }
}