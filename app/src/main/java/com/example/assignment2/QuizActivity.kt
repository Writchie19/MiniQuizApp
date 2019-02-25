package com.example.assignment2

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import kotlinx.android.synthetic.main.activity_quiz.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.InputStreamReader

private const val QUESTION = "question"
private const val Q1 = "question1"
private const val Q2 = "question2"
private const val Q3 = "question3"
private const val Q4 = "question4"
private const val ANSWER1 = "answer1"
private const val ANSWER2 = "answer2"
private const val ANSWER3 = "answer3"
private const val ANSWER4 = "answer4"
private const val CORRECTANSWER = "correctAnswer"

class QuizActivity : AppCompatActivity(), QuizFragmentResult {
    private var currentQuestionNumber = 1
    private var quizResult = 0
    private var currentResult = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Log.i("HRERE", "HERE?")
        Next_Button.setOnClickListener{onNext()}
        var fragments = supportFragmentManager
        var fragmentTransaction = fragments.beginTransaction()
        val question = getQuestion(currentQuestionNumber)
        val fragment = QuizFragment.create(question)
        fragmentTransaction.replace(R.id.quizfragment, fragment)
        fragmentTransaction.commit()
//        AnswerGroup.setOnCheckedChangeListener{ group, checkId->
//            when(checkId) {
//                R.id.Answer1 -> Log.i("TAG", "answer1")
//                R.id.Answer2 -> Log.i("TAG", "answer2")
//                R.id.Answer3 -> Log.i("TAG", "answer3")
//                R.id.Answer4 -> Log.i("TAG", "answer4")
//            }
//        }

    }

    private fun onNext() {
        if (currentResult) {
            quizResult++
        }
        if (4 == currentQuestionNumber) {
            val quizResultToPassBack = intent
            quizResultToPassBack.putExtra("result", quizResult)
            setResult(Activity.RESULT_OK, quizResultToPassBack)
            finish()
        }
        else {
            currentQuestionNumber++
            val question = getQuestion(currentQuestionNumber)
            val fragment = QuizFragment.create(question)
            var fragments = supportFragmentManager
            var fragmentTransaction = fragments.beginTransaction()
            fragmentTransaction.replace(R.id.quizfragment, fragment)
            fragmentTransaction.addToBackStack("QuizFragment")
            fragmentTransaction.commit()
        }
    }

    override fun getResult(isCorrect: Boolean) {
        currentResult = isCorrect
    }

    private fun getQuestion(questionNumber : Int) : MutableMap<String, String> {
        val question = mutableMapOf<String, String>()
        val raw = resources.openRawResource(R.raw.questions)
        val rawQuestion = raw.reader().buffered().readLines()
        var jsonString = ""
        for (line in rawQuestion) {
            jsonString += line
        }
        val jsonObj = JSONObject(jsonString)
        when(questionNumber) {
            1 -> {
                question[QUESTION] = jsonObj.getJSONObject(Q1).getString(QUESTION)
                question[ANSWER1] = jsonObj.getJSONObject(Q1).getString(ANSWER1)
                question[ANSWER2] = jsonObj.getJSONObject(Q1).getString(ANSWER2)
                question[ANSWER3] = jsonObj.getJSONObject(Q1).getString(ANSWER3)
                question[ANSWER4] = jsonObj.getJSONObject(Q1).getString(ANSWER4)
                question[CORRECTANSWER] = jsonObj.getJSONObject(Q1).getString(CORRECTANSWER)
            }
            2 -> {
                question[QUESTION] = jsonObj.getJSONObject(Q2).getString(QUESTION)
                question[ANSWER1] = jsonObj.getJSONObject(Q2).getString(ANSWER1)
                question[ANSWER2] = jsonObj.getJSONObject(Q2).getString(ANSWER2)
                question[ANSWER3] = jsonObj.getJSONObject(Q2).getString(ANSWER3)
                question[ANSWER4] = jsonObj.getJSONObject(Q2).getString(ANSWER4)
                question[CORRECTANSWER] = jsonObj.getJSONObject(Q2).getString(CORRECTANSWER)
            }
            3 -> {
                question[QUESTION] = jsonObj.getJSONObject(Q3).getString(QUESTION)
                question[ANSWER1] = jsonObj.getJSONObject(Q3).getString(ANSWER1)
                question[ANSWER2] = jsonObj.getJSONObject(Q3).getString(ANSWER2)
                question[ANSWER3] = jsonObj.getJSONObject(Q3).getString(ANSWER3)
                question[ANSWER4] = jsonObj.getJSONObject(Q3).getString(ANSWER4)
                question[CORRECTANSWER] = jsonObj.getJSONObject(Q3).getString(CORRECTANSWER)
            }
            4 -> {
                question[QUESTION] = jsonObj.getJSONObject(Q4).getString(QUESTION)
                question[ANSWER1] = jsonObj.getJSONObject(Q4).getString(ANSWER1)
                question[ANSWER2] = jsonObj.getJSONObject(Q4).getString(ANSWER2)
                question[ANSWER3] = jsonObj.getJSONObject(Q4).getString(ANSWER3)
                question[ANSWER4] = jsonObj.getJSONObject(Q4).getString(ANSWER4)
                question[CORRECTANSWER] = jsonObj.getJSONObject(Q4).getString(CORRECTANSWER)
            }
        }
        return question
    }
}
