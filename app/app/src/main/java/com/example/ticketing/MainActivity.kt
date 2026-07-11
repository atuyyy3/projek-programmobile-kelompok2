package com.example.ticketing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGoToCreateTicket = findViewById<Button>(R.id.btnGoToCreateTicket)

        btnGoToCreateTicket.setOnClickListener {
            // Ubah tujuan intent ke TicketListActivity
            val intent = Intent(this, TicketListActivity::class.java)
            startActivity(intent)
        }
    }
}