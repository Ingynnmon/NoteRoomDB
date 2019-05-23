package com.app.noteroomdb

import android.arch.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.noteroomdb.DB.Entity.NoteEntry
import com.app.noteroomdb.DB.Entity.note_id
import com.app.noteroomdb.DB.UnitLocalized.NoteCurrentEntry

@Dao
interface NoteDAO {
    //update or insert at the same time
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(Nentry:NoteEntry)

    @Query("select * from notedb where id=$note_id")
    fun getNote():LiveData<NoteCurrentEntry>
}