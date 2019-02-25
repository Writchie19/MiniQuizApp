package com.example.assignment2


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quiz_question.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val QUESTION = "question"
private const val ANSWER1 = "answer1"
private const val ANSWER2 = "answer2"
private const val ANSWER3 = "answer3"
private const val ANSWER4 = "answer4"
private const val CORRECTANSWER = "correctAnswer"

interface QuizFragmentResult {
    fun getResult(isCorrect : Boolean)
}
/**
 * A simple [Fragment] subclass.
 *
 */
class QuizFragment : Fragment() {
    private var correctAnswer: String? = ""
    private var currentAnswer = 0
    private var questionResult = false

    companion object Factory {
        fun create(question: MutableMap<String,String>) : QuizFragment {
            val newFragment = QuizFragment()
            val args = Bundle()
            for ((Key,Value) in question) {
                args.putString(Key, Value)
            }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            Question.text = arguments?.getString(QUESTION)
            Answer1.text = arguments?.getString(ANSWER1)
            Answer2.text = arguments?.getString(ANSWER2)
            Answer3.text = arguments?.getString(ANSWER3)
            Answer4.text = arguments?.getString(ANSWER4)
            correctAnswer = arguments?.getString(CORRECTANSWER)
        }

        AnswerGroup.setOnCheckedChangeListener{ group, checkId->
            when(checkId) {
                R.id.Answer1 -> currentAnswer = 1
                R.id.Answer2 -> currentAnswer = 2
                R.id.Answer3 -> currentAnswer = 3
                R.id.Answer4 -> currentAnswer = 4
            }
            if (currentAnswer == correctAnswer?.toIntOrNull()) {
                questionResult = true
            }
            else {
                questionResult = false
            }
            val activity = activity as QuizFragmentResult
            activity.getResult(questionResult)
        }
    }
}
