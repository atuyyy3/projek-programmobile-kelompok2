package com.example.ticketing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketing.R
import com.example.ticketing.model.Ticket

class TicketAdapter(
    private var ticketList: List<Ticket>,
    private val onClick: (Ticket) -> Unit
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTicketTitle)
        val tvStatus: TextView = itemView.findViewById(R.id.tvTicketStatus)
        val tvDesc: TextView = itemView.findViewById(R.id.tvTicketDesc)
        val tvPriority: TextView = itemView.findViewById(R.id.tvTicketPriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.tvTitle.text = ticket.title
        holder.tvStatus.text = ticket.status
        holder.tvDesc.text = ticket.description
        holder.tvPriority.text = "Priority: ${ticket.priority}"

        // Ubah warna teks status berdasarkan kondisinya (Opsional agar lebih menarik)
        when (ticket.status.uppercase()) {
            "OPEN" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
            "IN PROGRESS" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#0088FF"))
            "CLOSED" -> holder.tvStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
        }

        holder.itemView.setOnClickListener { onClick(ticket) }
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    // Fungsi ini dipanggil untuk memperbarui data di layar
    fun updateData(newList: List<Ticket>) {
        ticketList = newList
        notifyDataSetChanged()
    }
}