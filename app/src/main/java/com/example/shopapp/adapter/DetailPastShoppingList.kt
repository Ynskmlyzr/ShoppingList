package com.example.shopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.DetailShoppingViewLayoutBinding
import com.example.shopapp.model.ShoppingList

class DetailPastShoppingList : RecyclerView.Adapter<DetailPastShoppingList.DetailListHolder> () {

    private var list:ArrayList<ShoppingList> = arrayListOf()

    class DetailListHolder(var binding: DetailShoppingViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListHolder {
        val binding = DetailShoppingViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailListHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailListHolder, position: Int) {
        holder.binding.apply {

            tvProduct.text=list.get(position).shoppingName
            tvPiece.text=list.get(position).shoppingPiece.toString()

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun listDetailFill(shopList : ArrayList<ShoppingList>){
        list.clear()
        list.addAll(shopList)
        notifyDataSetChanged()
    }
}