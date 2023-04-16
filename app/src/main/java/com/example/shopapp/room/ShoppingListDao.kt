package com.example.shopapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.ShoppingList


@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun record(completedList: List<CompletedList>)

    @Query("SELECT * FROM completedShopping")
    fun getAllList() : LiveData<List<CompletedList>>

    @Query("SELECT * FROM completedShopping WHERE uuid=:id ")
    fun shopingList(id : Int) : LiveData<List<CompletedList>>

    @Query("UPDATE completedShopping SET completedList=:list WHERE uuid=:uid")
    suspend fun updateShoppingList(list :List<ShoppingList>,uid:Int)

    @Query("UPDATE completedShopping SET completedDate=:time WHERE uuid=:uid")
    fun updateTime(time :String,uid:Int)

    @Update
    fun update(completedList: CompletedList)

    @Query("DELETE FROM completedShopping")
    fun deleteAllMessage()

    @Query("DELETE FROM completedShopping WHERE uuid= :id ")
    fun deleteMessage(id :Int)
}