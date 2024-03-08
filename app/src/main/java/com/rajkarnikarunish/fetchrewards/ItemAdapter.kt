package com.rajkarnikarunish.fetchrewards

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajkarnikarunish.fetchrewards.databinding.ListItemBinding

class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    
    private var groupedItems: Map<String, List<String>> = emptyMap()
    
    fun setItems(groupedItems: Map<String, List<String>>) {
        this.groupedItems = groupedItems
        notifyDataSetChanged()
    }
    
    inner class ItemViewHolder(private var itemBinding: ListItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        
        fun bind(listId: String, items: List<String>) {
            itemBinding.apply { 
                listIdText.text = "List Id: $listId"
                
                val itemsWithoutPrefix = items.map { it.substring(2) }                
                nameTextView.text = itemsWithoutPrefix.joinToString(separator = "\n")
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