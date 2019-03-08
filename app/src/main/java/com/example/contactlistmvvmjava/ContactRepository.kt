package com.example.contactlistmvvmjava

import android.app.Application
import android.os.AsyncTask

import androidx.lifecycle.LiveData

class ContactRepository internal constructor(application: Application) {
    private val mContactDao: ContactDao
    internal val allContacts: LiveData<List<Contact>>

    init {
        val db = ContactRoomDatabase.getDatabase(application)
        mContactDao = db.contactDao()
        allContacts = mContactDao.allContacts
    }

    fun insert(contact: Contact) {
        insertAsyncTask(mContactDao).execute(contact)
    }

    fun deleteContact(contact: Contact) {
        deleteContactAsyncTask(mContactDao).execute(contact)
    }


    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: ContactDao) : AsyncTask<Contact, Void, Void>() {

        override fun doInBackground(vararg params: Contact): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class deleteContactAsyncTask internal constructor(private val mAsyncTaskDao: ContactDao) : AsyncTask<Contact, Void, Void>() {

        override fun doInBackground(vararg params: Contact): Void? {
            mAsyncTaskDao.deleteContact(params[0])
            return null
        }
    }
}
