package com.example.ticketing

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketing.data.DatabaseHelper

class AnalyticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        val dbHelper = DatabaseHelper(this)
        val leaderboardList = dbHelper.getLeaderboardData()

        // Konversi data ke format string untuk ditampilkan di ListView sederhana
        val displayData = leaderboardList.map { "User: ${it.first} | Selesai: ${it.second}" }

        val listView = findViewById<ListView>(R.id.rvLeaderboard) // Gunakan ListView untuk kemudahan
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayData)
        listView.adapter = adapter
    }
}