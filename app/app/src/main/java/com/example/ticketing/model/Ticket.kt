package com.example.ticketing.model

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val assigneeId: String
)