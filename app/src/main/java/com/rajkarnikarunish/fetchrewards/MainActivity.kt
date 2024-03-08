package com.rajkarnikarunish.fetchrewards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajkarnikarunish.fetchrewards.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    lateinit var itemAdapter: ItemAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchData()
    }
    
    private fun fetchData() {
        GlobalScope.launch(Dispatchers.IO) { 
            try {
                val response = RetrofitInstance.api.getItems()
                
                val items = response
                    .filter { it.name?.isNotBlank() == true}
                    .map { "${it.listId} ${it.name}" }
                    .sortedWith(AlphaNumComparator())

                println(items)
                val groupedItems = items.groupBy { it.split(' ')[0] }
                println(groupedItems)

                launch(Dispatchers.Main) {
                    itemAdapter.setItems(groupedItems)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error Fetching Items: ${e.message}")
            }
        }
    }
    
    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter()
        binding.listItemRecyclerView.apply { 
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}