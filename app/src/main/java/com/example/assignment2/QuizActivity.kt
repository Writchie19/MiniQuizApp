package com.example.assignment2

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_quiz_question.*

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Log.i("HRERE", "HERE?")
        Back_Button.setOnClickListener{
            val quizResultToPassBack = intent
            quizResultToPassBack.putExtra("result", 50)
            setResult(Activity.RESULT_CANCELED, quizResultToPassBack)
            finish()
        }
//        AnswerGroup.setOnCheckedChangeListener{ group, checkId->
//            when(checkId) {
//                R.id.Answer1 -> Log.i("TAG", "answer1")
//                R.id.Answer2 -> Log.i("TAG", "answer2")
//                R.id.Answer3 -> Log.i("TAG", "answer3")
//                R.id.Answer4 -> Log.i("TAG", "answer4")
//            }
//        }
        val question = mutableListOf<String>()
        question.add("What's in my pocket?")
        question.add("is it this answer?")
        question.add("how about this one?")
        question.add("must be this guy")
        question.add("alksmdf")
        val fragments = supportFragmentManager
        val fragmentTransaction = fragments.beginTransaction()
        val fragment = QuizQuestion.create(question)
        fragmentTransaction.replace(R.id.quizfragment, fragment)
        fragmentTransaction.addToBackStack("QuizQuestion")
        fragmentTransaction.commit()
        //fragment.setQuestion(question)
    }
}
