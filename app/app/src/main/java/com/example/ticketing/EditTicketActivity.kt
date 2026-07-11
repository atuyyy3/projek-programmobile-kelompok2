package com.example.ticketing

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketing.data.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton

class EditTicketActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var ticketId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ticket) // Reuse layout

        dbHelper = DatabaseHelper(this)
        ticketId = intent.getStringExtra("TICKET_ID") // Mengambil ID dari intent

        val etTitle = findViewById<TextInputEditText>(R.id.etTitle)
        val etDesc = findViewById<TextInputEditText>(R.id.etDescription)
        val etPriority = findViewById<TextInputEditText>(R.id.etPriority)
        val etAssignee = findViewById<TextInputEditText>(R.id.etAssignee)
        val btnSave = findViewById<MaterialButton>(R.id.btnSaveTicket)

        // Tambahkan tombol hapus secara programatik atau di XML
        val btnDelete = findViewById<MaterialButton>(R.id.btnDeleteTicket) // Tambahkan tombol ini di XML layout

        // Logika Update
        btnSave.setOnClickListener {
            dbHelper.updateTicket(ticketId!!, etTitle.text.toString(), etDesc.text.toString(),
                etPriority.text.toString(), "OPEN", etAssignee.text.toString())
            Toast.makeText(this, "Tiket Diperbarui", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Logika Hapus dengan Konfirmasi (Sesuai UC-5)
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Hapus Tiket")
                .setMessage("Apakah Anda yakin ingin menghapus tiket ini?")
                .setPositiveButton("Hapus") { _, _ ->
                    dbHelper.deleteTicket(ticketId!!)
                    Toast.makeText(this, "Tiket Dihapus", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}