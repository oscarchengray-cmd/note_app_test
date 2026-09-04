package edu.saihs.skills08.noteapptest

import android.R
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.serialization.descriptors.StructureKind


class sql(context: Context) : SQLiteOpenHelper(context, "SQL", null, 1) {
    companion object {
        val tablename = "sqltable"
        val id = "id"
        val title = "title"
        val body = "body"
    }

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL("CREATE TABLE $tablename ($id INTEGER PRIMARY KEY AUTOINCREMENT ,$title TEXT, $body TEXT)")
    }

    override fun onUpgrade(
        p0: SQLiteDatabase,
        p1: Int,
        p2: Int
    ) {
        p0.execSQL("DROP TABLE IF EXISTS $tablename")
        onCreate(p0)
    }

    fun write(text: Notedata) {
        this.writableDatabase.insert(tablename, null, ContentValues().apply {
            put(title, text.title)
            put(body, text.body)
        })
    }

    fun read(): List<Notedata> {
        val cursor = this.readableDatabase.rawQuery("SELECT * FROM $tablename", null)
        var list: List<Notedata> = emptyList()
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(title))
            val body = cursor.getString(cursor.getColumnIndexOrThrow(body))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(id))
            list += Notedata(title = title, body = body, id = id)
        }
        return list
    }

    fun upgrade(text: Notedata) {
        this.writableDatabase.update(tablename, ContentValues().apply {
            put(title, text.title)
            put(body, text.body)
        }, "id=?", arrayOf(text.id.toString()))
    }

    fun delete(deleteid: Int) {
        this.writableDatabase.delete(tablename, "id=?", arrayOf(deleteid.toString()))
    }
}

