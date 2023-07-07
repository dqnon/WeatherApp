package com.example.weathermvvm.presentation.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermvvm.R
import com.example.weathermvvm.databinding.ActivitySearchBinding
import com.example.weathermvvm.domain.model.searchCity.SearchCityItem
import com.example.weathermvvm.presentation.ProgressState
import com.example.weathermvvm.presentation.adapters.SearchAdapter
import com.example.weathermvvm.presentation.viewmodel.search.SearchViewModel
import com.example.weathermvvm.presentation.viewmodel.search.SearchViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchAdapter.Listener {

    lateinit var binding: ActivitySearchBinding
    lateinit var searchViewModel: SearchViewModel
    //private val searchViewModel: SearchViewModel by viewModels()
    lateinit var adapterSearch: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(this)).get(SearchViewModel::class.java)

        searchViewModel.resultSearch.observe(this) { listSearch ->
            adapterSearch = SearchAdapter(this)
            binding.rcViewSearch.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcViewSearch.adapter = adapterSearch

            adapterSearch.submitList(listSearch)
        }

        searchViewModel.state.observe(this) {state ->
            when(state){
                ProgressState.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                    binding.imageView.visibility = View.INVISIBLE
                }
                ProgressState.Success -> {
                    binding.progressBar2.visibility = View.INVISIBLE
                }
                ProgressState.Error -> {
                    Toast.makeText(this, R.string.error_dialog, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.getCityList(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.getCityList(newText.toString())
                return true
            }

        })

    }

    override fun onClick(city: SearchCityItem) {
        val i = Intent()
        i.putExtra("city", city)
        setResult(RESULT_OK, i)
        finish()
    }
}