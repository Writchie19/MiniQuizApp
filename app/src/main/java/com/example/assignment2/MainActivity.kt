package com.example.assignment2

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import java.io.File
import org.json.JSONObject

class MainActivity : AppCompatActivity() , OnEditorActionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<EditText>(R.id.Age_Edit).setOnEditorActionListener(this)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var handled = false
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            handled = true
            val newFirstName = findViewById<EditText>(R.id.First_Name_Edit).text.toString()
            val newLastName = findViewById<EditText>(R.id.Last_Name_Edit).text.toString()
            val newNickName = findViewById<EditText>(R.id.Nick_Name_Edit).text.toString()
            val newAge = findViewById<EditText>(R.id.Age_Edit).text.toString()
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(v?.windowToken, 0)
            val userInfo = UserInformation(this)
            userInfo.update(newFirstName, newLastName, newNickName, newAge)
            Log.i("FIRSTNAME", userInfo.getFirstName())
            Log.i("LASTNAME", userInfo.getLastName())
            Log.i("NICKNAME", userInfo.getNickName())
            Log.i("AGE", userInfo.getAge())
        }
        return handled
    }
}