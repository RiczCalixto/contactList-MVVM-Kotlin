package com.example.contactlistmvvmjava

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class ContactListAdapter(context: Context) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    private val mInflater: LayoutInflater
    private var mContacts: List<Contact>? = null


    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactItemView: TextView

        init {
            contactItemView = itemView.findViewById(R.id.contact_name_textview)
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = mInflater.inflate(R.layout.card_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = mContacts!![position]
        holder.contactItemView.text = current.contact


    }

    fun setWords(contacts: List<Contact>) {
        mContacts = contacts
        notifyDataSetChanged()
    }



    fun getContactAtPosition(position: Int): Contact {
        return mContacts!![position]
    }

    override fun getItemCount(): Int {
        return if (mContacts != null)
            mContacts!!.size
        else
            0
    }


}