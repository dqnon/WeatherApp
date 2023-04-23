package com.example.weathermvvm.presentation.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.databinding.ActivitySearchBinding
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.adapters.SearchAdapter
import com.example.weathermvvm.presentation.viewmodel.search.SearchViewModel
import com.example.weathermvvm.presentation.viewmodel.search.SearchViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), SearchAdapter.Listener {

    lateinit var binding: ActivitySearchBinding
    lateinit var searchViewModel: SearchViewModel
    lateinit var adapterSearch: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this, SearchViewModelFactory()).get(SearchViewModel::class.java)

        searchViewModel.resultSearch.observe(this, Observer {
            adapterSearch = SearchAdapter(this)
            binding.rcViewSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcViewSearch.adapter = adapterSearch

            adapterSearch.submitList(it)
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    searchViewModel.getCityList(query.toString())
                }
                binding.imageView.visibility = View.INVISIBLE
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun onClick(city: SearchCityItem) {
//
//        searchList.add(city.name)
//        Log.d("SearchCityLog", "В активити $searchList")
        val i = Intent()
        i.putExtra("city", city)
        setResult(RESULT_OK, i)
        finish()
    }
}