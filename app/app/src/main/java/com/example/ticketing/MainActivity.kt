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

        // Cek apakah user sudah login
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return // Hentikan eksekusi kode di bawah jika belum login
        }

        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val btnGoToTicketList = findViewById<Button>(R.id.btnGoToCreateTicket)
        val btnAnalytics = findViewById<Button>(R.id.btnViewAnalytics)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            performLogout()
        }

        btnGoToTicketList.setOnClickListener {
            startActivity(Intent(this, TicketListActivity::class.java))
        }
        btnAnalytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
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

        // Ubah status agar sesuai dengan string yang ada di Database (Seeder)
        val openCount = dbHelper.getTicketCountByStatus("Open")
        val progCount = dbHelper.getTicketCountByStatus("InProgress")
        val closedCount = dbHelper.getTicketCountByStatus("Closed")

        tvOpen.text = openCount.toString()
        tvProg.text = progCount.toString()
        tvClosed.text = closedCount.toString()
    }

    // Di dalam MainActivity.kt, tambahkan fungsi ini
    private fun performLogout() {
        // 1. Hapus status login di SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Menghapus semua data session
        editor.apply()

        // 2. Redirect ke LoginActivity
        val intent = Intent(this, LoginActivity::class.java)

        // 3. Penting: Hapus semua activity sebelumnya agar user tidak bisa kembali dengan tombol Back
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}