package com.app.noteroomdb.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val note_id=0
@Entity(tableName = "note")
data class NoteEntry(
    val title:String?,
    val description:String?
)
{@PrimaryKey(autoGenerate = true)
    var id:Int= note_id
}
