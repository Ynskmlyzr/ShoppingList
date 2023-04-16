package com.example.shopapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.Converters


@Database(entities = [CompletedList::class], version = 1)
@TypeConverters(Converters::class)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun listDao() : ShoppingListDao
}