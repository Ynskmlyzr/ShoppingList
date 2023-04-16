package com.example.shopapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName = "completedShopping")
data class CompletedList(
    @ColumnInfo(name = "completedDate")
    var completedShopDate: String? = null,
    @ColumnInfo(name = "completedList")
    var completedShopList: List<ShoppingList>? = arrayListOf()
) {
    @PrimaryKey(autoGenerate = true) var uuid : Int = 0
}

class Converters{

    @TypeConverter
    fun listToJsonString(value: List<ShoppingList>?) : String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value : String) = Gson().fromJson(value,Array<ShoppingList>::class.java).toList()

}