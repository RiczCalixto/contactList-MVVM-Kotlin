package com.example.contactlistmvvmjava

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var mContactViewModel: ContactViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ContactListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        mContactViewModel!!.allWords.observe(this, Observer { contacts ->
            // Update the cached copy of the words in the adapter.
            adapter.setWords(contacts!!)
        })

        val fab = findViewById<FloatingActionButton>(R.id.add_fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewContactActivity::class.java)
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE)
        }

        val helper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView,
                                        viewHolder: RecyclerView.ViewHolder,
                                        target: RecyclerView.ViewHolder): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                          direction: Int) {
                        val position = viewHolder.adapterPosition
                        val myContact = adapter.getContactAtPosition(position)
                        Toast.makeText(this@MainActivity, "Deleting " + myContact.contact, Toast.LENGTH_LONG).show()

                        // Delete the word
                        mContactViewModel!!.deleteContact(myContact)
                    }
                })

        helper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val contact = Contact(data!!.getStringExtra(NewContactActivity.EXTRA_REPLY))
            mContactViewModel!!.insert(contact)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        val NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1
    }
}