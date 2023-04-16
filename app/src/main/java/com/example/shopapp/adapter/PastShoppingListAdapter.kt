package com.example.shopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.PastShoppingListViewLayoutBinding
import com.example.shopapp.model.CompletedList

class PastShoppingListAdapter : RecyclerView.Adapter<PastShoppingListAdapter.PastListHolder> () {

    private var list : ArrayList<CompletedList> = arrayListOf()
    var imageViewClikListener: (ArrayList<CompletedList>,Int) -> Unit = {_,_ ->}

    class PastListHolder(var binding: PastShoppingListViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastListHolder {
        val binding = PastShoppingListViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PastListHolder(binding)
    }

    override fun onBindViewHolder(holder: PastListHolder, position: Int) {
        holder.binding.apply {
            tvDate.text=list.get(position).completedShopDate
            consPastShopping.setOnClickListener{
                imageViewClikListener(list,list.get(position).uuid)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size-1
    }
    fun listPastFill(shopList : ArrayList<CompletedList>){
        list.clear()
        list.addAll(shopList)
        notifyDataSetChanged()
    }
}