package com.example.demo

import androidx.core.app.PendingIntentCompat.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch


suspend fun main() {

    val scope= CoroutineScope(Dispatchers.Default)

    val shared= flow<Int>{
        print("emit now")
        emit(1)
    }

val stateFlow = MutableStateFlow<Int>(0)
    val channel = Channel<Int>()
   scope.launch {
      channel.send(10)
      channel.send(10)
      channel.send(10)
      channel.send(10)
   }

//    scope.launch {
//        channel.consumeEach {
//            println("received consume 1 ${it}")
//        }
//    }

    scope.launch {
        channel.consumeEach {
            println("received consume 2 ${it}")
        }
    }

    Thread.sleep(1000)

}