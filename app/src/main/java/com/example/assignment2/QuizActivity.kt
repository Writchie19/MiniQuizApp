package com.example.assignment2

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
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
    private var currentResult = false
    private lateinit var quizResults: ArrayList<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Next_Button.setOnClickListener{onNext()}
        quizResults = arrayListOf(false,false,false,false,false)
        var fragments = supportFragmentManager
        var fragmentTransaction = fragments.beginTransaction()
        val question = getQuestion(currentQuestionNumber)
        val fragment = QuizFragment.create(question, currentQuestionNumber)
        fragmentTransaction.replace(R.id.quizfragment, fragment)
        fragmentTransaction.commit()
    }

    private fun onNext() {
        quizResults[currentQuestionNumber] = currentResult
        if (4 == currentQuestionNumber) {
            val quizResultToPassBack = intent
            var quizResult = 0
            for (item in quizResults){
                if (item) {
                    quizResult++
                }
            }
            quizResultToPassBack.putExtra("result", quizResult)
            setResult(Activity.RESULT_OK, quizResultToPassBack)
            finish()
        }
        else {
            if (1 == currentQuestionNumber) {
                if (quizResults[currentQuestionNumber]) {
                    Toast.makeText(this, "One Ring to Rule Them All", Toast.LENGTH_LONG).show()
                }
            }
            currentQuestionNumber++
            val question = getQuestion(currentQuestionNumber)
            val fragment = QuizFragment.create(question, currentQuestionNumber)
            var fragments = supportFragmentManager
            var fragmentTransaction = fragments.beginTransaction()
            fragmentTransaction.replace(R.id.quizfragment, fragment)
            fragmentTransaction.addToBackStack("QuizFragment")
            fragmentTransaction.commit()
        }
    }

    override fun getResult(isCorrect: Boolean, qNumber: Int) {
        currentResult = isCorrect
        currentQuestionNumber = qNumber

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
