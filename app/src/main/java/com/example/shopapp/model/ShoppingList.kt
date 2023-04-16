package com.example.shopapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingList")
data class ShoppingList(
    var shoppingName : String?,
    var shoppingPiece : String?
) {
    @PrimaryKey(autoGenerate = true) var uuid : Int = 0
}