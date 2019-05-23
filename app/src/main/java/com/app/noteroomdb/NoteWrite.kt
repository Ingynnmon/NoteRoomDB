package com.app.noteroomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.noteroomdb.DB.Entity.NoteEntry
import com.app.noteroomdb.DB.UnitLocalized.NoteCurrentEntry

@Database(
    entities = [NoteCurrentEntry::class],
    version = 1
)
abstract class NoteWrite:RoomDatabase(){
    abstract fun noteDAO(): NoteDAO

   //create one instance
    companion object {
        @Volatile private var instance:NoteWrite?=null
        //not two processes parallel working
        private val LOCK=Any()

       fun getInstance(context:Context): NoteWrite= instance?: synchronized(LOCK){
           instance?: buildDatabase(context).also { instance = it }
       }
      /* private fun buildDatabase(context: Context)=
               Room.databaseBuilder(context.applicationContext,
                   NoteWrite::class.java,"notedb.db").build()*/

       private fun buildDatabase(context: Context)=
           Room.databaseBuilder(context.applicationContext,
               NoteWrite::class.java,"notedb.db")
               //prepopulate the database after onCreate was callled
               .addCallback(object :Callback(){
                   override fun onCreate(db: SupportSQLiteDatabase) {
                       super.onCreate(db)
                       //insert the data on the IO Thread
                       ioThread {
                           getInstance(context).noteDAO().insertData(PREPOPULATE_DATA)
                       }
                   }
               }).build()

       val PREPOPULATE_DATA = listOf(NoteEntry("1", "val"), NoteEntry("2", "val 2"))
    }


}