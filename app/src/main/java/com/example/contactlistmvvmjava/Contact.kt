package com.example.contactlistmvvmjava

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
class Contact(@field:PrimaryKey
              @field:ColumnInfo(name = "contact")
              val contact: String)
