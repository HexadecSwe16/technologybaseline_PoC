package com.hexadec.megalexa.model.blocks

import android.util.Log
import com.hexadec.megalexa.model.parserJson
import org.json.JSONObject

class BlockText constructor(private var text: String):Block,parserJson {
    init{
        Log.d("Text to Speech", "controlling text block")
        require(text.length <= 255){
            println("Overflow Error, max lenght is 255")
        }
    }

    override fun getConfiguration(): String {
        TODO("not implemented")
    }

    override fun getBLockInformation(): String {
        return "Text block"
    }

    fun setText(textRewrite: String){
        text = textRewrite
    }

    fun getText(): String {
        return text
    }

    override fun toJSON() : JSONObject{
        val infoGeneral : JSONObject = JSONObject()
        val specifics : JSONObject = JSONObject()
        val blockName ="textToSpeech"
        infoGeneral.put("Block Name", blockName)
        specifics.put(blockName, text )
        infoGeneral.put("config", specifics)
        return infoGeneral
    }
}