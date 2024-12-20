package com.example.noteapp.data.models

import android.content.ClipDescription
import android.icu.text.CaseMap.Title
import android.media.audiofx.AudioEffect.Descriptor
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val date: String,
    val time: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
