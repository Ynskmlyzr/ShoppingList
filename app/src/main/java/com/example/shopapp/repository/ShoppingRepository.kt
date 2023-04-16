package com.example.shopapp.repository

import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.ShoppingList
import com.example.shopapp.room.ShoppingListDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingRepository @Inject constructor(private val dao: ShoppingListDao){

    suspend fun record(list : List<CompletedList>) = dao.record(list)
    fun getAllList() = dao.getAllList()
    fun getShoppingList(id : Int) = dao.shopingList(id)
    suspend fun updateShoppingList(list : List<ShoppingList>, id : Int) = dao.updateShoppingList(list,id)
    fun updateTime(time : String,id:Int) = dao.updateTime(time,id)
    fun deleteItem(id:Int) = dao.deleteMessage(id)
    fun deleteAll() = dao.deleteAllMessage()

}