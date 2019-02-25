/*
CS 646
William Ritchie
Assignment 2
2/24/19
 */
package com.example.assignment2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quiz_question.*

// Note: Using constants for an immediate solution for this assignment, future solution would include getting rid of these
// and providing a much more scalable answer to handle varying number of questions
// These constants are used to access map that gets passed to this fragment through the static constructor
private const val QUESTION = "question"
private const val ANSWER1 = "answer1"
private const val ANSWER2 = "answer2"
private const val ANSWER3 = "answer3"
private const val ANSWER4 = "answer4"
private const val CORRECTANSWER = "correctAnswer"

interface QuizFragmentResult {
    fun getResult(isCorrect : Boolean, qNumber: Int)
}

class QuizFragment : Fragment() {
    private var correctAnswer: String? = ""
    private var currentAnswer = 0
    private var questionResult = false
    private var qNumber = 0

    // Static Consructor used to pass in arguments to the fragment from the activity that instaniated it
    companion object Factory {
        fun create(question: MutableMap<String,String>, qNumber: Int) : QuizFragment {
            val newFragment = QuizFragment()
            val args = Bundle()
            for ((Key,Value) in question) {
                args.putString(Key, Value)
            }
            args.putString("qNumber", qNumber.toString())
            newFragment.arguments = args
            return newFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_question, container, false)
    }

    // This is where the input arguments are received through the bundle
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            Question.text = arguments?.getString(QUESTION)
            Answer1.text = arguments?.getString(ANSWER1)
            Answer2.text = arguments?.getString(ANSWER2)
            Answer3.text = arguments?.getString(ANSWER3)
            Answer4.text = arguments?.getString(ANSWER4)
            correctAnswer = arguments?.getString(CORRECTANSWER)
            qNumber = arguments?.getString("qNumber")!!.toInt()
        }

        // Set the radio button listener, informs the activity per click, the activity only cares about whether the user
        // got the right answer or not and who (i.e which question) the user is on
        AnswerGroup.setOnCheckedChangeListener{ group, checkId->
            when(checkId) {
                R.id.Answer1 -> currentAnswer = 1
                R.id.Answer2 -> currentAnswer = 2
                R.id.Answer3 -> currentAnswer = 3
                R.id.Answer4 -> currentAnswer = 4
            }

            // Note android studio gives a warning here, but I don't like how there solution looks
            if (currentAnswer == correctAnswer?.toIntOrNull()) {
                questionResult = true
            }
            else {
                questionResult = false
            }
            val activity = activity as QuizFragmentResult
            activity.getResult(questionResult, qNumber)
        }
    }
}
