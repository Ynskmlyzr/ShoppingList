package com.example.shopapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.R
import com.example.shopapp.adapter.ShoppingListAdapter
import com.example.shopapp.databinding.FragmentShoppingListBinding
import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.Constant
import com.example.shopapp.model.ShoppingList
import com.example.shopapp.viewmodel.ShoppingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {

    private lateinit var binding: FragmentShoppingListBinding
    private var shoppingList : ArrayList<ShoppingList> = arrayListOf()
    private var tryList : ArrayList<ShoppingList> = arrayListOf()
    private var list : ArrayList<CompletedList> = arrayListOf()
    private var shoppingListAdabter = ShoppingListAdapter()
    private val shoppingListViewModel : ShoppingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingListViewModel.roomDataList(list)
        shoppingListViewModel.roomList(requireActivity(),list)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            rvShoppingList.layoutManager = LinearLayoutManager(context)
            rvShoppingList.adapter = shoppingListAdabter
            shoppingListAdabter.listFill(shoppingList)
            visibility()
            lifecycleScope.launchWhenStarted {
                shoppingListViewModel.flowTotal.collect{
                    getList(it)
                }
            }

            shoppingListAdabter.pieceViewClikListener ={ number,position ->

                shoppingList.get(position).shoppingPiece = number.toString()
                shoppingListViewModel.recordItem(shoppingList,list.get(list.size-1).uuid)

            }

            shoppingListAdabter.deleteViewClikListener={ deleteList,id,position ->
                shoppingList.removeAt(position)
                shoppingListViewModel.recordItem(shoppingList,list.get(list.size-1).uuid)
                shoppingListAdabter.listFill(shoppingList)
                visibility()
            }

            btnAdd.setOnClickListener {
               dataAdd()
            }

            imgPastShopping.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_shoppingListFragment_to_pastShoppingListFragment)
            }

            btnComplete.setOnClickListener {
                dataComplated()
            }

            imgDelete.setOnClickListener {
                deleteList()
            }

        }
    }
    fun getList(myList : ArrayList<CompletedList>){
        binding.apply {
            list.addAll(myList)
            list.forEach {
                shoppingList.clear()
                it.completedShopList?.let { it1 -> shoppingList.addAll(it1) }
            }
            if (shoppingList.size == 0) {
                shoppingListAdabter.listFill(shoppingList)
                visibility()
            } else {
                if (shoppingList.get(0).shoppingName == "") {
                    tryList.clear()
                    shoppingListAdabter.listFill(tryList)
                    imgDelete.visibility = View.GONE
                    tvEnterAnItem.visibility = View.VISIBLE
                } else {
                    shoppingListAdabter.listFill(shoppingList)
                    visibility()
                }
            }
        }
    }

    fun dataAdd(){
        binding.apply {
            if (edtItem.text.isEmpty()) {
                Toast.makeText(context, Constant.CONS_PLEASE_ITEM, Toast.LENGTH_SHORT).show()
            } else {
                if (shoppingList.size == 0) {
                    shoppingList.add(ShoppingList(edtItem.text.toString().trim().lowercase().capitalize(), "1"))
                    val liste = CompletedList("", shoppingList)
                    list.addAll(listOf(liste))
                    shoppingListAdabter.listFill(shoppingList)
                    shoppingListViewModel.record(list)
                    edtItem.text.clear()
                    visibility()
                } else {
                    if (shoppingList.get(0).shoppingName == "") {
                        shoppingList.clear()
                        shoppingList.add(ShoppingList(edtItem.text.toString().trim().lowercase().capitalize(), "1"))
                        shoppingListAdabter.listFill(shoppingList)
                        shoppingListViewModel.recordItem(shoppingList, list.get(list.size - 1).uuid)
                        edtItem.text.clear()
                        visibility()
                    } else {
                        observeSameName()
                    }
                }
            }
        }
    }

    fun observeSameName(){
        binding.apply {
            for (x in 0..shoppingList.size-1){
                if(shoppingList.get(x).shoppingName!!.lowercase().trim() == edtItem.text.toString().lowercase().trim()){
                    var number: Int = shoppingList.get(x).shoppingPiece!!.toInt()
                    number = number + 1
                    shoppingList.get(x).shoppingPiece = number.toString()
                    shoppingList.get(x).shoppingName = shoppingList.get(x).shoppingName.toString().trim().lowercase().capitalize()
                    shoppingListAdabter.listFill(shoppingList)
                    shoppingListViewModel.recordItem(shoppingList,list.get(list.size - 1).uuid)
                    edtItem.text.clear()
                    visibility()
                }
            }
            if(edtItem.text.isNotEmpty()){
                shoppingList.add(ShoppingList(edtItem.text.toString().trim().lowercase().capitalize(),"1"))
                shoppingListAdabter.listFill(shoppingList)
                shoppingListViewModel.recordItem(shoppingList,list.get(list.size - 1).uuid)
                edtItem.text.clear()
                visibility()
            }
        }
    }

    fun dataComplated(){
        binding.apply {
            if (shoppingList.size == 0) {
                Toast.makeText(context, Constant.CONS_DİFF_ITEM, Toast.LENGTH_SHORT).show()
            } else {
                val now = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val now_str = formatter.format(now)
                shoppingListViewModel.recordItemTime(now_str, list.get(list.size - 1).uuid)
                val listem: ArrayList<CompletedList> = arrayListOf()
                listem.add(CompletedList("", listOf(ShoppingList("", "1"))))
                shoppingListViewModel.record(listem)
                shoppingList.clear()
                list.clear()
                shoppingListAdabter.listFill(shoppingList)
                visibility()
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_shoppingListFragment_to_pastShoppingListFragment)
                }
            }
        }
    }

    fun deleteList(){
        binding.apply {
            shoppingList.clear()
            shoppingListAdabter.listFill(shoppingList)
            shoppingListViewModel.recordItem(shoppingList,list.get(list.size-1).uuid)
            visibility()
        }
    }

    fun visibility (){
        binding.apply {
            if(shoppingList.size == 0){
                imgDelete.visibility = View.GONE
                tvEnterAnItem.visibility = View.VISIBLE
            }else{
                imgDelete.visibility = View.VISIBLE
                tvEnterAnItem.visibility = View.GONE
            }
        }
    }

}