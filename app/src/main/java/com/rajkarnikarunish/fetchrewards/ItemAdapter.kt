package com.rajkarnikarunish.fetchrewards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajkarnikarunish.fetchrewards.databinding.ListItemBinding

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    
    private var groupedItems: Map<Int, List<Item>> = emptyMap()
    
    fun setItems(groupedItems: Map<Int, List<Item>>) {
        this.groupedItems = groupedItems
        notifyDataSetChanged()
    }
    
    inner class ItemViewHolder(private var itemBinding: ListItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        
        fun bind(listId: Int, items: List<Item>) {
            itemBinding.apply { 
                listIdText.text = "List Id: $listId"
                nameTextView.text = items.joinToString(separator = "\n") {it.name ?: ""}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = groupedItems.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val listId = groupedItems.keys.toList()[position]
        val items = groupedItems[listId] ?: return
        
        holder.bind(listId, items)
    }
}