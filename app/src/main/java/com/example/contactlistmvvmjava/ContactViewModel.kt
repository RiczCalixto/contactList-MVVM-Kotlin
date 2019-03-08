package com.example.contactlistmvvmjava

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: ContactRepository

    internal val allWords: LiveData<List<Contact>>

    init {
        mRepository = ContactRepository(application)
        allWords = mRepository.allContacts
    }

    fun insert(contact: Contact) {
        mRepository.insert(contact)
    }

    fun deleteContact(contact: Contact) {
        mRepository.deleteContact(contact)
    }
}
