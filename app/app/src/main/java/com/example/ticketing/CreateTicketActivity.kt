package com.example.ticketing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketing.data.DatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.UUID

class CreateTicketActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ticket)

        dbHelper = DatabaseHelper(this)

        // Binding ID dari XML ke variabel Kotlin
        val etTitle = findViewById<TextInputEditText>(R.id.etTitle)
        val etDescription = findViewById<TextInputEditText>(R.id.etDescription)
        val etPriority = findViewById<TextInputEditText>(R.id.etPriority)
        val etAssignee = findViewById<TextInputEditText>(R.id.etAssignee)
        val btnSaveTicket = findViewById<MaterialButton>(R.id.btnSaveTicket)

        btnSaveTicket.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val desc = etDescription.text.toString().trim()
            val priority = etPriority.text.toString().trim()
            val assignee = etAssignee.text.toString().trim()

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Mohon isi judul dan deskripsi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke SQLite
            val ticketId = UUID.randomUUID().toString()
            val status = "OPEN"
            val result = dbHelper.insertTicket(ticketId, title, desc, priority, status, assignee)

            if (result != -1L) {
                Toast.makeText(this, "Tiket berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal menyimpan tiket", Toast.LENGTH_SHORT).show()
            }
        }
    }
}