package com.example.ticketing.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketing.R

class LeaderboardAdapter(private val list: List<Pair<String, Int>>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRank: TextView = view.findViewById(R.id.tvRank)
        val tvName: TextView = view.findViewById(R.id.tvUserName)
        val tvCount: TextView = view.findViewById(R.id.tvTicketCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.tvRank.text = (position + 1).toString()
        holder.tvName.text = data.first
        holder.tvCount.text = "${data.second} Tiket"

        // Memberikan warna emas untuk peringkat 1
        if (position == 0) {
            holder.tvRank.setTextColor(Color.parseColor("#FFD700"))
        }
    }

    override fun getItemCount() = list.size
}