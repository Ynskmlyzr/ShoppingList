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
import com.example.shopapp.adapter.PastShoppingListAdapter
import com.example.shopapp.databinding.FragmentPastShoppingListBinding
import com.example.shopapp.model.CompletedList
import com.example.shopapp.model.Constant
import com.example.shopapp.model.ShoppingList
import com.example.shopapp.viewmodel.PastShoppingListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastShoppingListFragment : Fragment() {

    private lateinit var binding: FragmentPastShoppingListBinding
    private val pastShoppingListViewModel : PastShoppingListViewModel by viewModels()
    private var complatedPastList : ArrayList<CompletedList> = arrayListOf()
    private var pastShoppingListAdapter = PastShoppingListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pastShoppingListViewModel.roomDataList(complatedPastList)
        pastShoppingListViewModel.roomList(requireActivity(),complatedPastList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPastShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            rvPastShopping.layoutManager = LinearLayoutManager(context)
            rvPastShopping.adapter=pastShoppingListAdapter
            pastShoppingListAdapter.listPastFill(complatedPastList)


            lifecycleScope.launchWhenCreated {
                pastShoppingListViewModel.flowTotal.collect{
                    pastShoppingListAdapter.listPastFill(it)
                    if(it.size == 0){
                        btnDelete.visibility = View.GONE
                        tvEmpty.visibility=View.VISIBLE
                    }else{
                        if(it.get(0).completedShopDate == ""){
                            btnDelete.visibility = View.GONE
                            tvEmpty.visibility=View.VISIBLE
                        }else {
                            btnDelete.visibility = View.VISIBLE
                            tvEmpty.visibility = View.GONE
                        }
                    }
                }
            }

            pastShoppingListAdapter.imageViewClikListener ={ clickList , id ->
                Navigation.findNavController(view).navigate(R.id.action_pastShoppingListFragment_to_detailPastShoppingListFragment,
                bundleOf(Constant.CONS_COMP_SHOP_LIST to id))
        }

            btnDelete.setOnClickListener {
                pastShoppingListViewModel.deleteAll()
                complatedPastList.clear()
                btnDelete.visibility = View.GONE
                tvEmpty.visibility=View.VISIBLE
                pastShoppingListAdapter.listPastFill(complatedPastList)
            }

            imgBack.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_pastShoppingListFragment_to_shoppingListFragment)
            }

        }
    }
}