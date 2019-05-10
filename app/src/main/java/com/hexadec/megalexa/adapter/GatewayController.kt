package com.hexadec.megalexa.adapter

import android.util.Log
import com.hexadec.megalexa.model.HexadecUser
import com.hexadec.megalexa.model.blocks.Block
import com.hexadec.megalexa.model.Workflow
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList

object GatewayController{

    private const val api_URL= "https://yuh491oge9.execute-api.us-east-1.amazonaws.com/prodPost/"

    //requestWrite
    fun saveUser(user: HexadecUser){
        val resources = "utenti/writeuser"
        val date = JSONObject()
        date.put("Mail", user.getEmail())
        date.put("PIN", user.getPin())
        date.put("Username", user.getName())
        date.put("IDUser", user.getId())
        requestWritePost(date,api_URL + resources)
    }

    fun readWorkflowPost(user : HexadecUser) : ArrayList<Workflow>{
        val listWorkflow : ArrayList<Workflow> = ArrayList()
        val resources="workflow/readworkflow"
        val date = JSONObject()
        date.put("Mail", user.getEmail())
        val workflowListJSON = requestReadPost(date,api_URL+resources )
        val workflowListArray = workflowListJSON.getJSONArray("Items")
        for(i in 0..workflowListArray.length()-1) {
            val jsonObjectDetail: JSONObject = workflowListArray.getJSONObject(i)
            val appoggio = Workflow(jsonObjectDetail.getString("WorkflowName"),jsonObjectDetail.getString("CreationDate"),jsonObjectDetail.getString("ModifyDate"),jsonObjectDetail.getInt("Counter"))
            appoggio.setIdWorkflow(jsonObjectDetail.getInt("IDWorkflow"))
            listWorkflow.add(appoggio)
        }
        return listWorkflow
    }

    //RequestRead
    fun readUserPost(user : HexadecUser) : JSONObject{
        val resources="utenti/readuser"
        Log.d("USER", "CONTROLLO ESISTENZA UTENTE")
        val date = JSONObject()
        date.put("Mail", user.getEmail())
        val existUser = requestReadPost(date,api_URL+resources )
        return existUser
    }

    private fun requestWritePost(date:JSONObject,url: String) {
        val myUrl = URL(url)
        Log.d("URL", "Response: ${url.toString()}")
        with(myUrl.openConnection() as HttpsURLConnection) {
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)
            wr.write(date.toString())
            wr.flush()
            var result: StringBuffer
            BufferedReader(InputStreamReader(inputStream)).use {
                result = StringBuffer()
                var iLine = it.readLine()
                while (iLine != null) {
                    result.append(iLine)
                    iLine = it.readLine()
                }
            }
        }
    }

    private fun requestReadPost(date:JSONObject,url: String) :JSONObject {
        val myUrl = URL(url)
        Log.d("URL", "Response: ${url.toString()}")
        with(myUrl.openConnection() as HttpsURLConnection){
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            doOutput = true
            val wr = OutputStreamWriter(outputStream)
            wr.write(date.toString())
            wr.flush()
            var result: StringBuffer
            BufferedReader(InputStreamReader(inputStream)).use {
                result = StringBuffer()
                var iLine = it.readLine()
                while (iLine != null) {
                    result.append(iLine)
                    iLine = it.readLine()
                }
                return JSONObject(result.toString())
            }
        }
    }

    fun readBlocks(user: HexadecUser, workflow: Workflow) : ArrayList<Block>?{//da modificare a cascata togliendo user
        val blocksList : ArrayList<Block> = ArrayList<Block>()
        val toPass = JSONObject()
        toPass.put("IDWorkflow", workflow.getIdWorkflow().toString())
        val resources = "blocchi/readblocks"
        val blocks = requestReadPost(toPass, api_URL + resources)
        Log.d("BLOCCHI", "Response: $blocks ")
        /*for(i in 0..blocks.length()-1){
            val item = blocks.getJSONObject(i)
            when(item.getString("blockType")){
                //ADD BLOCK
                //"textToSpeech"-> blocksList.add(BlockTextBox(item.getJSONObject("config").getString("textToSpeech")))
                //"FeedRSS" -> blocksList.add(BlockFeedRss(item.getJSONObject("config").getString("url")))

            }
        }*/
        workflow.setBlocks(blocksList)
        return blocksList
    }

    //BLOCK FUNCTIONS
    fun saveBlock() {
        TODO()
    }

    fun deleteBlock() {
        TODO()
    }

    fun updateBlock() {
        TODO()
    }

    //WORKFLOW FUNCTIONS
    fun saveWorkflow(user: HexadecUser, w: Workflow) {//salva solo le info basilari di workflow non i singoli blocchi ad esso associati
        val data = JSONObject()
        data.put("Mail", user.getEmail())
        data.put("WorkflowName", w.getWorkflowName())
        data.put("WelcomeText", w.getWelcomeText())
        var resousces = "workflow/writeworkflow"
        requestWritePost(data, api_URL + resousces)
    }

    fun deleteWorkflow(workflowName: String) {
        TODO()
    }

    fun updateWorkflow(workflow: Workflow) {
        TODO()
    }
}

