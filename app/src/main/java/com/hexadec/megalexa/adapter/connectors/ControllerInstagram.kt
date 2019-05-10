package com.hexadec.megalexa.adapter.connectors

import org.jetbrains.anko.doAsyncResult
import java.util.concurrent.atomic.AtomicBoolean

class ControllerInstagram constructor(private var url: String):Connector {
    private var result= AtomicBoolean(false)
    init {
        url =connect(url)
    }

    override fun connect(url: String):String {
        return url
    }

    override fun isvalid():Boolean {
        val operation = doAsyncResult {
           isUrlORHastag()
        }
        return operation.get()
    }

    private fun isUrlORHastag():Boolean{
        TODO("not implemeted")
        setBoolean(AtomicBoolean(false))
        return result.get()
    }

    fun setBoolean(b:AtomicBoolean){
        result=b
    }
}