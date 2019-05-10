package com.hexadec.megalexa.model

import com.hexadec.megalexa.model.blocks.Block
import com.hexadec.megalexa.adapter.GatewayController
import org.json.JSONObject
import kotlin.collections.ArrayList

class Workflow constructor(private var workflowName:String, private val creationDate:String, private val lastModifyDate:String, private val interaction:Int):parserJson {
    private var blockList: ArrayList<Block> = ArrayList()
    private var welcomeText = "welcome Text"
    private var idWorkflow = 0

    fun setIdWorkflow(value:Int){
        idWorkflow=value
    }

    fun getIdWorkflow():Int{
        return idWorkflow
    }

    fun setWelcomeText(value:String){
        welcomeText=value
    }

    fun getWelcomeText():String{
        return welcomeText
    }

    fun addBlockToWorkflow(block: Block){
        blockList.add(block)
    }

    fun getLastModifyDate():String{
        return lastModifyDate
    }

    fun getCreationDate():String{
        return creationDate
    }

    fun getInteraction(): Int {
        return interaction
    }

    fun getWorkflowSize():Int {
        return blockList.size
    }

    fun removeBlockFromWorkflow(block:Block) {
        blockList.remove(block)
    }

    fun getBlocks() : ArrayList<Block>{
        return blockList
    }

    fun getBlocks(user: HexadecUser) : ArrayList<Block>{
        blockList = GatewayController.readBlocks(user, this)!!
        return blockList
    }

    fun setBlocks(blockList : ArrayList<Block>){
        this.blockList = blockList
    }

    override fun toString(): String {
        return getWorkflowName()
    }

    fun getWorkflowName() : String{
        return workflowName
    }

    override fun toJSON() : JSONObject{
        val workflow= JSONObject()
        workflow.put("WorkFlow",this.getWorkflowName())
        //TODO("not implemented")
        return workflow
    }
}