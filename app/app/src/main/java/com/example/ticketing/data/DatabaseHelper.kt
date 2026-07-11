package com.example.ticketing.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ticketing.model.Ticket

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TicketingManagement.db"
        private const val DATABASE_VERSION = 2 // Naik ke versi 2 agar tabel diperbarui

        // --- Konstanta Tabel User ---
        const val TABLE_USER = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_NAME = "name"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_ROLE = "role"
        const val COLUMN_USER_PASSWORD = "password" // Kolom baru

        // --- Konstanta Tabel Ticket ---
        const val TABLE_TICKET = "tickets"
        const val COLUMN_TICKET_ID = "id"
        const val COLUMN_TICKET_TITLE = "title"
        const val COLUMN_TICKET_DESC = "description"
        const val COLUMN_TICKET_PRIORITY = "priority"
        const val COLUMN_TICKET_STATUS = "status"
        const val COLUMN_TICKET_ASSIGNEE = "assignee_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_USER_ID TEXT PRIMARY KEY,
                $COLUMN_USER_NAME TEXT,
                $COLUMN_USER_EMAIL TEXT UNIQUE,
                $COLUMN_USER_ROLE TEXT,
                $COLUMN_USER_PASSWORD TEXT
            )
        """.trimIndent()

        val createTicketTable = """
            CREATE TABLE $TABLE_TICKET (
                $COLUMN_TICKET_ID TEXT PRIMARY KEY,
                $COLUMN_TICKET_TITLE TEXT,
                $COLUMN_TICKET_DESC TEXT,
                $COLUMN_TICKET_PRIORITY TEXT,
                $COLUMN_TICKET_STATUS TEXT,
                $COLUMN_TICKET_ASSIGNEE TEXT,
                FOREIGN KEY($COLUMN_TICKET_ASSIGNEE) REFERENCES $TABLE_USER($COLUMN_USER_ID)
            )
        """.trimIndent()

        db?.execSQL(createUserTable)
        db?.execSQL(createTicketTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TICKET")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    // --- FUNGSI UNTUK USER (REGISTER & LOGIN) ---
    fun insertUser(id: String, name: String, email: String, role: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, id)
            put(COLUMN_USER_NAME, name)
            put(COLUMN_USER_EMAIL, email)
            put(COLUMN_USER_ROLE, role)
            put(COLUMN_USER_PASSWORD, password)
        }
        val result = db.insert(TABLE_USER, null, values)
        db.close()
        return result
    }

    fun checkUser(email: String, pass: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER WHERE $COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?", arrayOf(email, pass))
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    // --- FUNGSI UNTUK TIKET ---
    fun insertTicket(id: String, title: String, desc: String, priority: String, status: String, assigneeId: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TICKET_ID, id)
            put(COLUMN_TICKET_TITLE, title)
            put(COLUMN_TICKET_DESC, desc)
            put(COLUMN_TICKET_PRIORITY, priority)
            put(COLUMN_TICKET_STATUS, status)
            put(COLUMN_TICKET_ASSIGNEE, assigneeId)
        }
        val result = db.insert(TABLE_TICKET, null, values)
        db.close()
        return result
    }

    fun getAllTickets(): List<Ticket> {
        val ticketList = mutableListOf<Ticket>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TICKET", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKET_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKET_TITLE))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKET_DESC))
                val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKET_PRIORITY))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKET_STATUS))
                val assignee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKET_ASSIGNEE))
                ticketList.add(Ticket(id, title, desc, priority, status, assignee))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ticketList
    }

    fun getTicketCountByStatus(status: String): Int {
        val db = this.readableDatabase
        val query = "SELECT count(*) FROM $TABLE_TICKET WHERE $COLUMN_TICKET_STATUS = ?"
        val cursor = db.rawQuery(query, arrayOf(status))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }
}