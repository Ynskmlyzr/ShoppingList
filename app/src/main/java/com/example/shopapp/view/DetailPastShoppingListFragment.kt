package com.example.shopapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.R
import com.example.shopapp.adapter.DetailPastShoppingList
import com.example.shopapp.databinding.FragmentDetailPastShoppingListBinding
import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.Constant
import com.example.shopapp.model.ShoppingList
import com.example.shopapp.viewmodel.DetailPastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailPastShoppingListFragment : Fragment() {

    private lateinit var binding: FragmentDetailPastShoppingListBinding
    private var detailPastShoppingList = DetailPastShoppingList()
    private val detailPastViewModel : DetailPastViewModel by viewModels()
    private var pastCompletedList : ArrayList<CompletedList> = arrayListOf()
    private var pastShoppingList : ArrayList<ShoppingList> = arrayListOf()
    private var listID : Int = 0
    private val number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (arguments)?.let {
            if (Build.VERSION.SDK_INT >= 33) {
                it.getString(Constant.CONS_COMP_SHOP_LIST)
                listID = it.toString().toInt()
                //listID = it.getParcelableArrayList (Constant.CONS_COMP_SHOP_LIST, CompletedList::class.java) as ArrayList<CompletedList>
            }else{
                (it.get(Constant.CONS_COMP_SHOP_LIST).toString().toInt()).let {
                    listID=it
                }
            }
        }
        detailPastViewModel.roomDataList(pastCompletedList)
        detailPastViewModel.listem(listID,requireActivity(),pastCompletedList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPastShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            rvProducts.layoutManager = LinearLayoutManager(context)
            rvProducts.adapter = detailPastShoppingList
            detailPastShoppingList.listDetailFill(pastShoppingList)

            lifecycleScope.launchWhenCreated {
                detailPastViewModel.flowTotal.collect{
                   it.forEach {
                       it.completedShopList?.let { it1 -> pastShoppingList.addAll(it1) }
                   }
                    detailPastShoppingList.listDetailFill(pastShoppingList)
                }

            }

            imgBack.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_detailPastShoppingListFragment_to_pastShoppingListFragment,)
            }

            imgDeleteItem.setOnClickListener {
                detailPastViewModel.deleteItem(listID)
                Navigation.findNavController(it).navigate(R.id.action_detailPastShoppingListFragment_to_pastShoppingListFragment)
            }

        }
    }

}