/*
CS 646
William Ritchie
Assignment 2
2/24/19
 */
package com.example.assignment2

import android.content.Context
import org.json.JSONObject
import java.io.File

// Used as keys to access the information inside of the file that holds the user's info
private const val USERFILENAME = "UserInfo"
private const val FIRSTNAMETAG = "First_Name"
private const val LASTNAMETAG = "Last_Name"
private const val NICKNAMETAG = "Nick_Name"
private const val AGETAG = "Age"
private const val SCORETAG = "Score"

class UserInformation {
    private val userFile : File
    private var userInfo : JSONObject
    private var firstName : String
    private var lastName : String
    private var nickName : String
    private var age : String
    private var score : String

    constructor(context : Context) {
        userFile = File(context.filesDir, USERFILENAME)
        userFile.createNewFile() // creates the file if it does not already exist, else does nothing
        // If it is empty then init the values to empty strings, otherwise grab the users data
        if (userFile.readBytes().isNotEmpty()) {
            userInfo = JSONObject(userFile.readText())
            firstName = userInfo.get(FIRSTNAMETAG).toString()
            lastName = userInfo.get(LASTNAMETAG).toString()
            nickName = userInfo.get(NICKNAMETAG).toString()
            age = userInfo.get(AGETAG).toString()
            score = userInfo.get(SCORETAG).toString()
        }
        else {
            userInfo = JSONObject()
            firstName = ""
            lastName = ""
            nickName = ""
            age = ""
            score = ""
            userInfo.put(FIRSTNAMETAG, firstName)
            userInfo.put(LASTNAMETAG, lastName)
            userInfo.put(NICKNAMETAG, nickName)
            userInfo.put(AGETAG, age)
            userInfo.put(SCORETAG, score)
            userFile.writeText(userInfo.toString())
        }
    }

    // Update the user's information
    fun update(newFirstName : String, newLastName : String, newNickName : String, newAge : String ) {
        firstName = newFirstName
        lastName = newLastName
        nickName = newNickName
        age = newAge
        userInfo.put(FIRSTNAMETAG, firstName)
        userInfo.put(LASTNAMETAG, lastName)
        userInfo.put(NICKNAMETAG, nickName)
        userInfo.put(AGETAG, age)
        userFile.writeText(userInfo.toString())
    }

    fun getFirstName() : String{
        return firstName
    }

    fun getLastName() : String{
        return lastName
    }

    fun getNickName() : String{
        return nickName
    }

    fun getAge() : String{
        return age
    }

    fun getScore() : String {
        // return the score in a percentage format
        val resultPercent = score.toFloat() * 100.0f / 4.0f
        return resultPercent.toString()
    }

    fun setScore(newScore : String) {
        score = newScore
        userInfo.put(SCORETAG, score)
        userFile.writeText(userInfo.toString())
    }
}