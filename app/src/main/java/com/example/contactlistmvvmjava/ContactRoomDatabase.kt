package com.example.contactlistmvvmjava

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    private class PopulateDbAsync internal constructor(db: ContactRoomDatabase) : AsyncTask<Void, Void, Void>() {

        private val mDao: ContactDao
        internal var contacts = arrayOf<String>()

        init {
            mDao = db.contactDao()
        }

        override fun doInBackground(vararg params: Void): Void? {
            //            mDao.deleteAll();

            if (mDao.anyWord.size < 1) {
                for (i in 0..contacts.size - 1) {
                    val contact = Contact(contacts[i])
                    mDao.insert(contact)
                }
            }
            return null
        }
    }

    companion object {

        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context): ContactRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                        ContactRoomDatabase::class.java,
                        "contact_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build()
                INSTANCE = instance
                instance
            }

        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }
}
