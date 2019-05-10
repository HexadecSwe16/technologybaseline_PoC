package com.hexadec.megalexa.adapter.connectors

import org.jetbrains.anko.doAsyncResult
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean


class ControllerFeedRss constructor(private var url: String):Connector {
    private var result= AtomicBoolean(false)
    init {
        url =connect(url)
    }

    override fun connect(url: String):String { 
            return url
    }

    override fun isvalid():Boolean {
        val operation = doAsyncResult {
            isRssFeed()
        }
        return operation.get()
    }

    private fun isRssFeed() :Boolean {
        var resource: URL
        var xpp: XmlPullParser
        var iStream: InputStream
        try {
            resource = URL(url)
            val factory = XmlPullParserFactory.newInstance()
            xpp = factory.newPullParser()
            iStream = resource.openConnection().getInputStream()
            iStream.use { x ->
                xpp.setInput(x, "UTF_8")
                xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                xpp.nextTag()
                if (xpp.name == "rss") {
                    setBoolean(AtomicBoolean(true))
                    return result.get()
                }
            }
        } catch (err: MalformedURLException) {
            err.printStackTrace()
        } catch (err: XmlPullParserException) {
            err.printStackTrace()
        }


        setBoolean(AtomicBoolean(false))
        return result.get()
    }

    private fun getInputStream(resource: URL) : InputStream {

        try {
            return resource.openConnection().getInputStream()
        }catch (err: IOException){
            err.printStackTrace()
        }
        return resource.openConnection().getInputStream()
    }

    fun setBoolean(b:AtomicBoolean){
        result=b
    }
}