package com.example.jetpackcomposearchi.dashboard.neos.viewModel

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposearchi.dashboard.neos.model.neosModel
import com.example.jetpackcomposearchi.network.Repository
import com.example.jetpackcomposearchi.other.Cons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class neosVM  @Inject constructor(private val repository: Repository): ViewModel() {


    val neos = repository.getNeos()

}