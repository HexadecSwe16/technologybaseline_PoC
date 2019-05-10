package com.hexadec.megalexa.model
import org.json.JSONObject
import java.io.Serializable

class HexadecUser constructor(private val id: String ,private val name: String,private val email:String) : parserJson,Serializable{
    private var pin = String()
    init{
        var n1 = (0..9).random()
        var n2 = (0..9).random()
        var n4 = (0..9).random()
        var n3 = (0..9).random()
        pin = n1.toString()+n2.toString()+n3.toString()+n4.toString()
    }

    fun getName():String{
        return name
    }
    fun getId():String{
        return id
    }
    fun getEmail():String{
        return email
    }

    fun setPin(security:String){
        pin = security
    }

    fun getPin():String{
        return pin
    }

    override fun toJSON() : JSONObject{
        val userJSON : JSONObject = JSONObject()
        userJSON.put("IDUser", id)
        userJSON.put("Name", name)
        userJSON.put("Mail", email)
        userJSON.put("Pin", pin)
        return userJSON
    }
}