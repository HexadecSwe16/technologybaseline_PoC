package com.hexadec.megalexa.model.blocks

import android.util.Log
import com.hexadec.megalexa.adapter.connectors.Connector
import com.hexadec.megalexa.adapter.connectors.ControllerInstagram
import com.hexadec.megalexa.model.ApplyFilter
import org.json.JSONObject

class BlockInstagram(private var info:String):Block, ApplyFilter {
    init{
        val instagramInfo =Connector(info)

    }

    private fun Connector(url: String): Connector {
        val toReturn= ControllerInstagram(url= url)
        Log.d("BLOCCO", "controlling instagramConnection")
        if(toReturn.isvalid()){
            TODO("not implemented")
        }
        return toReturn
    }

    override fun getConfiguration(): String {
        TODO("not implemented")
    }

    override fun getBLockInformation(): String {
        return "Instagram block"
    }

    override fun toJSON(): JSONObject {
        val infoGeneral = JSONObject()
        val specifics = JSONObject()
        val blockName ="Instagram"
        infoGeneral.put("Block Name", blockName)
        specifics.put(blockName , info)
        infoGeneral.put("config", specifics)
         return infoGeneral
    }
}
