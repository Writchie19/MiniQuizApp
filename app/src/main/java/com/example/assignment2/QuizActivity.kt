package com.example.assignment2

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Back_Button.setOnClickListener{
            val quizResultToPassBack = intent
            quizResultToPassBack.putExtra("result", 50)
            setResult(Activity.RESULT_CANCELED, quizResultToPassBack)
            finish()
        }
    }
}
