package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {
//    fun fullSchedule() : List<Schedule> = scheduleDao.getAll() // 오류 발생

//    suspend fun fullSchedule() : List<Schedule> = withContext(Dispatchers.IO){
//        scheduleDao.getAll()
//    }

      fun fullSchedule() : Flow<List<Schedule>> = scheduleDao.getAll() // 오류 발생

//    fun scheduleForStopName(name: String) : List<Schedule> = scheduleDao.getByStopName(name) // 오류 발생

//    suspend fun scheduleForStopName(name: String) : List<Schedule> = withContext(Dispatchers.IO) {
//        scheduleDao.getByStopName(name)
//    }

      fun scheduleForStopName(name: String) : Flow<List<Schedule>> = scheduleDao.getByStopName(name) // 오류 발생
}

class BusScheduleViewModelFactory(private val scheduleDao: ScheduleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BusScheduleViewModel(scheduleDao) as T
    }
}