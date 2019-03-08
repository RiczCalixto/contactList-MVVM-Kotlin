package com.example.contactlistmvvmjava

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {

    @get:Query("SELECT * from contact_table ORDER BY contact ASC")
    val allContacts: LiveData<List<Contact>>

    @get:Query("SELECT * from contact_table LIMIT 1")
    val anyWord: Array<Contact>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAll()

    @Delete
    fun deleteContact(contact: Contact)
}
