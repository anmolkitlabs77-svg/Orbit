package com.orbit.dashboard.neos.viewModel

import androidx.lifecycle.ViewModel
import com.orbit.dashboard.neos.model.neosModel
import com.orbit.network.Repository
import com.orbit.other.Cons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class neosVM  @Inject constructor(private val repository: Repository): ViewModel() {


    val neos = repository.getNeos()

}