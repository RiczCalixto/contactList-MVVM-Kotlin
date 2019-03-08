package com.example.contactlistmvvmjava

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText

class NewContactActivity : AppCompatActivity() {

    private var mEditContactView: EditText? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)
        mEditContactView = findViewById(R.id.edit_contact)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(mEditContactView!!.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val contact = mEditContactView!!.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, contact)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {

        val EXTRA_REPLY = "com.example.contactlistmvvmjava.REPLY"
    }
}
