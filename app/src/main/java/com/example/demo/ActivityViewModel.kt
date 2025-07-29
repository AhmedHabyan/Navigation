package com.example.demo

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActivityViewModel:ViewModel() {

    private var isResponseRunning= false
    private val dataList = listOf<String>("ahmed","mohamed","mostafa")
    val _evenStateFlow = MutableSharedFlow<Event>()
    val eventStateFlow = _evenStateFlow
    val eventChannel = Channel<Event>()
    fun sendEvent(event:Event){

        viewModelScope.launch {
//            eventChannel.send(event)
            Log.e("in event","emit now")
            _evenStateFlow.emit(event)

        }


    }
     var flow = flow<List<String>>(
         block = {
             emit(dataList)
         }
     )
         .map {
             UiState.Ideal as UiState

            }


        .onStart {
            Log.e("flow now:","first start")

            emit(UiState.Loading)
        }
         .onStart {
             Log.e("flow now:","second start")
             while (true){
                 Log.e("flow now:","emit now")
                 emit(UiState.Success(
                     listOf(
                         "Ahmed","Mohamed",
                         "reem"
                     )
                 ))
                 delay(2000)
             }
         }
        .onCompletion {
            Log.e("flow now:","completion 1 ended")
        }
         .onCompletion {
             Log.e("flow now:","completion 2 ended")
         }

    fun getNewData(){
        viewModelScope.launch {
            delay(1000)



        }
    }

    fun getData(){
        if(isResponseRunning) return

        isResponseRunning= true

        try {
            //callApi
            Log.e("time","${SystemClock.elapsedRealtime()}")
            Log.e("api call", "yes called")
        }catch (e:Exception){
            Log.e("api call", "${e.message}")
        }finally {
            isResponseRunning= false
            Log.e("api call","ended")
        }

    }
}