package com.exam.appetiserchallenge.utils

sealed class LoadState {
    object Loading : LoadState()
    object Completed : LoadState()
    object Error : LoadState()
}
