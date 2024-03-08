package com.rajkarnikarunish.fetchrewards

import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajkarnikarunish.fetchrewards.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response

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
                    .sortedWith(compareBy({it.listId}, {it.name}))
                
                val groupedItems = items.groupBy { it.listId }
                val sortedGroupedItems = groupedItems.toSortedMap()
                
                launch(Dispatchers.Main) {
                    itemAdapter.setItems(sortedGroupedItems)
                }
                
                Log.e("MainActivity", items.toString()) 
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