package com.example.shopapp.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.model.CompletedList
import com.example.shopapp.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PastShoppingListViewModel @Inject constructor(private val repository: ShoppingRepository,application: Application): BaseViewModel(application) {

    var list : ArrayList<CompletedList> = arrayListOf()
    var shopList = MutableLiveData<ArrayList<CompletedList>>()
    var _flowTotal = MutableStateFlow(list)
    var flowTotal : StateFlow<ArrayList<CompletedList>> = _flowTotal

    fun upload(listele: ArrayList<CompletedList>){
        _flowTotal.value = listele
    }

    fun roomDataList(dataList : ArrayList<CompletedList>){
        shopList.value = dataList
    }

    fun roomList(owner: LifecycleOwner, completedList: ArrayList<CompletedList>){
        repository.getAllList().observe(owner,{
            it?.let {
                completedList.clear()
                it.forEach {
                    completedList.add(it)
                    roomDataList(completedList)
                }
                upload(completedList)
            }
        })

    }
    fun record(recordList : ArrayList<CompletedList>){
        viewModelScope.launch {
            repository.record(recordList)
        }
    }

    fun deleteAll(){
        repository.deleteAll()
    }

}