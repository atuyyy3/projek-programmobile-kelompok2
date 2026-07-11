package com.example.ticketing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketing.adapter.LeaderboardAdapter
import com.example.ticketing.data.DatabaseHelper

class AnalyticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        val dbHelper = DatabaseHelper(this)
        val leaderboardList = dbHelper.getLeaderboardData()
        val recyclerView = findViewById<RecyclerView>(R.id.rvLeaderboard)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LeaderboardAdapter(leaderboardList)
    }
}