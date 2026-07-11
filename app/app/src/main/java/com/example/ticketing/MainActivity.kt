package com.example.ticketing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketing.data.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val btnGoToTicketList = findViewById<Button>(R.id.btnGoToCreateTicket)
        btnGoToTicketList.setOnClickListener {
            startActivity(Intent(this, TicketListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateDashboardStats()
    }

    private fun updateDashboardStats() {
        val tvOpen = findViewById<TextView>(R.id.tvOpenCount)
        val tvProg = findViewById<TextView>(R.id.tvProgCount)
        val tvClosed = findViewById<TextView>(R.id.tvClosedCount)

        // Ambil data dari SQLite
        val openCount = dbHelper.getTicketCountByStatus("OPEN")
        val progCount = dbHelper.getTicketCountByStatus("IN PROGRESS")
        val closedCount = dbHelper.getTicketCountByStatus("CLOSED")

        // Update UI
        tvOpen.text = openCount.toString()
        tvProg.text = progCount.toString()
        tvClosed.text = closedCount.toString()
    }
}