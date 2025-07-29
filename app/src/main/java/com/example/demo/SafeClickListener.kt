package com.example.demo

import android.os.SystemClock
import android.util.Log
import android.view.View

class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = SystemClock.elapsedRealtime()

    override fun onClick(v: View) {
        val currentTime = SystemClock.elapsedRealtime()
        Log.e("time","${SystemClock.elapsedRealtime()}")
        Log.e("time","${SystemClock.elapsedRealtime() - lastTimeClicked}")
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = currentTime

      onSafeCLick(v)
    }

}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)

    }

    setOnClickListener(safeClickListener)

}