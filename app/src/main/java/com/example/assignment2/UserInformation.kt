package com.example.assignment2

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.File

private const val USERFILENAME = "UserInfo"
private const val FIRSTNAMETAG = "First_Name"
private const val LASTNAMETAG = "Last_Name"
private const val NICKNAMETAG = "Nick_Name"
private const val AGETAG = "Age"

class UserInformation {
    private val userFile : File
    private var userInfo : JSONObject
    private var firstName : String
    private var lastName : String
    private var nickName : String
    private var age : String

    constructor(context : Context) {
        userFile = File(context.filesDir, USERFILENAME)
        userFile.createNewFile()
        if (userFile.readBytes().isNotEmpty()) {
            // what if readText is not in JSON format?
            userInfo = JSONObject(userFile.readText())
            firstName = userInfo.get(FIRSTNAMETAG).toString()
            lastName = userInfo.get(LASTNAMETAG).toString()
            nickName = userInfo.get(NICKNAMETAG).toString()
            age = userInfo.get(AGETAG).toString()
        }
        else {
            userInfo = JSONObject()
            firstName = ""
            lastName = ""
            nickName = ""
            age = ""
            userInfo.put(FIRSTNAMETAG, firstName)
            userInfo.put(LASTNAMETAG, lastName)
            userInfo.put(NICKNAMETAG, nickName)
            userInfo.put(AGETAG, age)
            userFile.writeText(userInfo.toString())
        }
    }

    fun readFromFile() {
        userInfo = JSONObject(userFile.readText())
        firstName = userInfo.get(FIRSTNAMETAG).toString()
        lastName = userInfo.get(LASTNAMETAG).toString()
        nickName = userInfo.get(NICKNAMETAG).toString()
        age = userInfo.get(AGETAG).toString()
    }

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
}