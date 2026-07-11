package com.example.ticketing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketing.adapter.TicketAdapter
import com.example.ticketing.data.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class TicketListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var rvTickets: RecyclerView
    private lateinit var tvEmptyState: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_list)

        dbHelper = DatabaseHelper(this)
        rvTickets = findViewById(R.id.rvTickets)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        val fabAddTicket = findViewById<FloatingActionButton>(R.id.fabAddTicket)

        // Setup RecyclerView
        rvTickets.layoutManager = LinearLayoutManager(this)
        ticketAdapter = TicketAdapter(emptyList()) { selectedTicket ->
            val intent = Intent(this, EditTicketActivity::class.java)
            intent.putExtra("TICKET_ID", selectedTicket.id)
            startActivity(intent)
        } // Mulai dengan list kosong
        rvTickets.adapter = ticketAdapter

        // Tombol (+) diarahkan ke Halaman Buat Tiket
        fabAddTicket.setOnClickListener {
            val intent = Intent(this, CreateTicketActivity::class.java)
            startActivity(intent)
        }
    }

    // Gunakan onResume agar list otomatis refresh saat kembali dari CreateTicketActivity
    override fun onResume() {
        super.onResume()
        loadTickets()
    }

    private fun loadTickets() {
        val tickets = dbHelper.getAllTickets()

        if (tickets.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            rvTickets.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            rvTickets.visibility = View.VISIBLE
            ticketAdapter.updateData(tickets)
        }
    }
}