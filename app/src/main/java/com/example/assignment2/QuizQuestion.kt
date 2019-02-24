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

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizQuestion : Fragment() {

    companion object Factory {
        fun create(question: List<String>) : QuizQuestion {
            val newFragment = QuizQuestion()
            val args = Bundle()
            for ((itemNumber, item) in question.withIndex()) {
                args.putString(itemNumber.toString(), item)
            }
            newFragment.arguments = args
            return newFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Log.i("SIZE", arguments?.size().toString())
            Log.i("ONCREATE0", arguments?.getString("0"))
            Log.i("ONCREATE1", arguments?.getString("1"))
            Log.i("ONCREATE2", arguments?.getString("2"))
            Log.i("ONCREATE3", arguments?.getString("3"))
            Log.i("ONCREATE4", arguments?.getString("4"))
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

//    fun setQuestion(question : ArrayList<String>){
//        Question.text = question[0]
//        Answer1.text = question[1]
//        Answer2.text = question[2]
//        Answer3.text = question[3]
//        Answer4.text = question[4]
//    }
}
