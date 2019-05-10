package com.hexadec.megalexa.model.blocks

import android.util.Log
import com.hexadec.megalexa.adapter.connectors.Connector
import com.hexadec.megalexa.adapter.connectors.ControllerFeedRss
import com.hexadec.megalexa.model.ApplyFilter
import com.hexadec.megalexa.model.parserJson
import org.json.JSONObject


class BlockFeedRss constructor(private val url: String): Block,ApplyFilter {
    private val connector:Connector
    init{
        connector = Connector(url)
    }

    private fun Connector(url: String): Connector {
        val toReturn=ControllerFeedRss(url= url)
        Log.d("FEED RSS", "creating feed rss")
        if(!toReturn.isvalid()){
            TODO("not implemented")
        }
        return toReturn
    }

    fun getUrl(): String {
        return url
    }

    override fun getConfiguration(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBLockInformation():String {
        return "Feed RSS block "
    }

    override fun toJSON() : JSONObject {
        val infoGeneral : JSONObject = JSONObject()
        val specifics : JSONObject = JSONObject()
        val blockName ="FeedRSS"
        infoGeneral.put("Block Name", blockName)
        specifics.put(blockName , url)
        infoGeneral.put("config", specifics)
        return infoGeneral
    }
}