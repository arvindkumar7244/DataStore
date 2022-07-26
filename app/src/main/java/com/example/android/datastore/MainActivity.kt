package com.example.android.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.android.datastore.databinding.ActivityMainBinding
import com.example.android.datastore.repository.DataStoreRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dataStoreRepository: DataStoreRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreRepository = DataStoreRepository(this)

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                dataStoreRepository.storeValue(binding.editTextTextPersonName.text.toString(),
                    applicationContext)
            }
        }

        dataStoreRepository.preferenceFlow.asLiveData().observe(this) {
            binding.textView.text = it
        }
    }
}