package com.hexadec.megalexa.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hexadec.megalexa.R
import com.hexadec.megalexa.adapter.GatewayController
import com.hexadec.megalexa.model.Workflow
import com.hexadec.megalexa.model.HexadecUser
import kotlinx.android.synthetic.main.activity_view.*
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_view.*


class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setSupportActionBar(toolbar)
        //WorflowListView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        val uLoggato = getActualUser()
        Log.d("UTENTE", "Profile Response: ${uLoggato.getName()}")
        setNewWorkflowListener(uLoggato)
        setSettingListener(uLoggato)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Thread(Runnable {
            var workflowList = GatewayController.readWorkflowPost(uLoggato)
            Log.d("workflow", "Profile Response: $workflowList")
            var user = GatewayController.readUserPost(uLoggato)
            Log.d("mail", "Profile Response: $user")
            if(user["Count"] ==0){
                Log.d("UTENTE","Response : UTENTE DA AGGIUNGERE")
                GatewayController.saveUser(uLoggato)
            }
            var tryWorkflow=Workflow("provaWorkflow","","",0)
            tryWorkflow.setIdWorkflow(1)
            //GatewayController.saveWorkflow(uLoggato,tryWorkflow)
            //per i workflow con idWorkflow preso da db posso fare le seguenti cose
            val blocchi = GatewayController.readBlocks(uLoggato,tryWorkflow)
        }).start()
    }

    private fun setSettingListener(uLoggato:HexadecUser){
        settings.setOnClickListener{
            val clickIntent = Intent(this@ViewActivity, SettingsActivity::class.java)
            clickIntent.putExtra("Utente",uLoggato)
            startActivity(clickIntent)
        }
    }

    private fun setNewWorkflowListener(uLoggato:HexadecUser) {
        fab.setOnClickListener {
            val clickIntent = Intent(this@ViewActivity, NewWorkflow::class.java)
            clickIntent.putExtra("Utente",uLoggato)
            startActivity(clickIntent)
        }
    }

    private fun getActualUser(): HexadecUser{
        val user = getIntent().getExtras().getSerializable("Utente") as HexadecUser
        return user
    }

    override fun onResume() {
        super.onResume()
        loadContent()
    }

    override fun onPause() {
        super.onPause()
        saveContent()
    }

    private fun loadContent() {

    }

    private fun saveContent() {

    }

}
