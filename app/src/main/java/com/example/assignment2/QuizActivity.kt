/*
CS 646
William Ritchie
Assignment 2
2/24/19
 */
package com.example.assignment2

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*
import org.json.JSONObject

// Note: Using constants for an immediate solution for this assignment, future solution would include getting rid of these
// and providing a much more scalable answer to handle varying number of questions
// These constants are used to access the contents of the questions.json file in the res/raw directory and to set the appropriate
// Keys in the map that will get passed to the fragment
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
    private var currentQuestionNumber = 1 // keeps track of the current fragment running
    private var currentResult = false // a boolean to keep track of if the users current selected answer is correct or not
    private lateinit var quizResults: ArrayList<Boolean> // Holds bools for keeping track of a history of the users answers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // This button will handle going to the next fragment
        Next_Button.setOnClickListener{onNext()}

        // Hide the visibility of the next button until the user answer s the question
        Next_Button.visibility = View.INVISIBLE

        // Note: A future solution would not do this and provide a much more scalable solution and not hard code five false's
        // The first false is ignored in order to match up the indices with the corresponding question
        quizResults = arrayListOf(false,false,false,false,false)

        // The initial fragment to bring to existence, note this does not add it onto the backstack
        // This addresses an edge case where the user hits the back button on the first fragment and see just the activity
        // which is undesirable
        var fragments = supportFragmentManager
        var fragmentTransaction = fragments.beginTransaction()
        val question = getQuestion(currentQuestionNumber) // A Collection to hold what will become the contents of the fragments' views
        val fragment = QuizFragment.create(question, currentQuestionNumber) // Static contructor
        fragmentTransaction.replace(R.id.quizfragment, fragment)
        fragmentTransaction.commit()
    }

    // Handles going to the next fragment and possibly back to the main activity depending on if the last next button has
    // been pressed, in which case it also sends the result to main ( in number of correct answers)
    private fun onNext() {
        // Updates quizResults to handle a user going backwards and forwards during the quiz
        // And for result calcutions
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
            // Whats in my pocket?
            if (1 == currentQuestionNumber) {
                if (quizResults[currentQuestionNumber]) {
                    Toast.makeText(this, "One Ring to Rule Them All", Toast.LENGTH_LONG).show()
                }
            }
            Next_Button.visibility = View.INVISIBLE
            // Begin the next fragment, which should be populated with the contents of the next question
            currentQuestionNumber++
            val question = getQuestion(currentQuestionNumber)
            val fragment = QuizFragment.create(question, currentQuestionNumber)
            var fragments = supportFragmentManager
            var fragmentTransaction = fragments.beginTransaction()
            fragmentTransaction.replace(R.id.quizfragment, fragment)
            fragmentTransaction.addToBackStack("QuizFragment") // These, unlike the initial fragment, are added to the backstack
            fragmentTransaction.commit()
        }
    }

    // The function to override that provides an interface for communication to the fragments, updates every radio button click
    override fun getResult(isCorrect: Boolean, qNumber: Int) {
        Next_Button.visibility = View.VISIBLE
        currentResult = isCorrect
        currentQuestionNumber = qNumber
    }

    // Returns a Map that holds the contents of the next fragment
    private fun getQuestion(questionNumber : Int) : MutableMap<String, String> {
        var qNum = ""
        // Determine what the next question to come to fruition is
        when (questionNumber) {
            1 -> qNum = Q1
            2 -> qNum = Q2
            3 -> qNum = Q3
            4 -> qNum = Q4
        }
        val question = mutableMapOf<String, String>()

        // Get the questions.json file from res/raw and read in every line
        val raw = resources.openRawResource(R.raw.questions)
        val rawQuestion = raw.reader().buffered().readLines()
        var jsonString = ""

        // Format a json string that will become the input to a jsonobj, simple concatenation is desired because it will
        // retain the appropriate JSON syntax with out adding any unnessary garbage, and it can thus become a convenient input
        // to a JSONObject which is convenient to work with
        for (line in rawQuestion) {
            jsonString += line
        }

        val jsonObj = JSONObject(jsonString)
        question[QUESTION] = jsonObj.getJSONObject(qNum).getString(QUESTION)
        question[ANSWER1] = jsonObj.getJSONObject(qNum).getString(ANSWER1)
        question[ANSWER2] = jsonObj.getJSONObject(qNum).getString(ANSWER2)
        question[ANSWER3] = jsonObj.getJSONObject(qNum).getString(ANSWER3)
        question[ANSWER4] = jsonObj.getJSONObject(qNum).getString(ANSWER4)

        question[CORRECTANSWER] = jsonObj.getJSONObject(qNum).getString(CORRECTANSWER)

        return question
    }
}
