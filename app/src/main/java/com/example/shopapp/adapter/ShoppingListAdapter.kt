package com.example.shopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ShoppingListViewLayoutBinding
import com.example.shopapp.model.ShoppingList

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ListHolder>() {

    private var list : ArrayList<ShoppingList> = arrayListOf()
    private var number :Int = 0
    var pieceViewClikListener: ( Int, Int) -> Unit = { _, _->}
    var deleteViewClikListener: (ArrayList<ShoppingList>,Int,Int) -> Unit = {_,_,_ ->}

    class ListHolder(var binding: ShoppingListViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = ShoppingListViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.binding.apply {
            edtProduct.text = list.get(position).shoppingName
            tvPiece.text=list.get(position).shoppingPiece.toString()
            number=list.get(position).shoppingPiece!!.toInt()

            imgPlus.setOnClickListener {
                number=list.get(position).shoppingPiece!!.toInt()
                number++
                tvPiece.text = number.toString()
                pieceViewClikListener(number,position)
            }
            imgInterest.setOnClickListener {
                number=list.get(position).shoppingPiece!!.toInt()
                if(number > 1){
                    number=list.get(position).shoppingPiece!!.toInt()
                    number--
                    tvPiece.text = number.toString()
                    pieceViewClikListener(number,position)
                }
                else{
                    deleteViewClikListener(list,list.get(position).uuid,position)
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun listFill(shopList : ArrayList<ShoppingList>){
        list.clear()
        list.addAll(shopList)
        notifyDataSetChanged()
    }
}