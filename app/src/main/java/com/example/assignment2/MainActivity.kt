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
            Log.i("FIRSTNAME", findViewById<EditText>(R.id.First_Name_Edit).text.toString())
            Log.i("LASTNAME", findViewById<EditText>(R.id.Last_Name_Edit).text.toString())
            Log.i("NICKNAME", findViewById<EditText>(R.id.Nick_Name_Edit).text.toString())
            Log.i("AGE", findViewById<EditText>(R.id.Age_Edit).text.toString())
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(v?.windowToken, 0)
            writeToFile()
            readFromFile()
        }
        return handled
    }

    fun writeToFile(){
        val outPutFile = File(this.filesDir, "UsrInfo")
        val jsonObj = JSONObject()
        jsonObj.put("First_Name", findViewById<EditText>(R.id.First_Name_Edit).text.toString())
        jsonObj.put("Last_Name", findViewById<EditText>(R.id.Last_Name_Edit).text.toString())
        jsonObj.put("Nick_Name", findViewById<EditText>(R.id.Nick_Name_Edit).text.toString())
        jsonObj.put("Age", findViewById<EditText>(R.id.Age_Edit).text.toString())
        outPutFile.writeText(jsonObj.toString())
    }

    fun readFromFile() {
        val inputFile = File(this.filesDir, "UsrInfo")
        Log.i("DIR", this.filesDir.toString())
        Log.i("UsrInfo",inputFile.readText())
    }
}
