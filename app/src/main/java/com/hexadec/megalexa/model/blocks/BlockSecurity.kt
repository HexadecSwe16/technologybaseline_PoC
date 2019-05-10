package com.hexadec.megalexa.model.blocks

import android.util.Log
import org.json.JSONObject

class BlockSecurity constructor(private var pin: String):Block {
    init{
        Log.d("Security", "controlling pin setting")
        require(pin.length == 4){
            println("Overflow Error. Pin is composed by 4 symbol")
        }
    }

    override fun getConfiguration(): String {
        TODO("not implemented")
    }

    override fun getBLockInformation(): String {
        return "Pin block"
    }

    fun setPin(textRewrite: String){
        pin = textRewrite
    }

    fun getPin(): String {
        return pin
    }

    override fun toJSON() : JSONObject {
        val infoGeneral : JSONObject = JSONObject()
        val specifics : JSONObject = JSONObject()
        val blockName ="Security"
        infoGeneral.put("Block Name", blockName)
        specifics.put(blockName, pin )
        infoGeneral.put("config", specifics)
        return infoGeneral
    }
}