package com.orbit.dashboard.apod.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.orbit.network.Repository
import com.orbit.network.room_space.SpaceWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class picbyDayVM @Inject constructor(private val repository: Repository): ViewModel(){

    
    @RequiresApi(Build.VERSION_CODES.O)
    val Enddate = LocalDate.now()
    val Startdate =  Enddate.minusDays(6)

    val pictures = repository.getSpaceData()

    val data = workDataOf("START_DATE" to Startdate.toString(),
        "END_DATE" to Enddate.toString(),
        "SCREEN" to 1)

    fun callWorker(context: Context){

        val request = OneTimeWorkRequestBuilder<SpaceWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "SpaceWorker",
            ExistingWorkPolicy.KEEP,
            request
        )
    }



}

