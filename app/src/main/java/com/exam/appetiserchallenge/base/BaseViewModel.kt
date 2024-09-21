package com.exam.appetiserchallenge.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.appetiserchallenge.utils.Contants.THROWABLE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * Base class for ViewModel
 * with implementation of [viewModelScope] launch and error handling
 * */
abstract class BaseViewModel : ViewModel() {

    fun safeLaunch(dispatcher: CoroutineDispatcher, method: suspend () -> Unit) {
        viewModelScope.launch(errorHandler + dispatcher) {
            method.invoke()
        }
    }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        onExceptionReceived(throwable)

    }

    open fun onExceptionReceived(e: Throwable) {
        Log.e(THROWABLE, "$e")
    }
}