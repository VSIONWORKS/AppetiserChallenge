package com.exam.appetiserchallenge.utils

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

/**
 * Launches subscriber when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * */
fun LifecycleOwner.launchOnStart(subscriber: suspend () -> Unit) {
    lifecycleScope.launchWhenStarted {
        subscriber.invoke()
    }
}

/**
 * Launches collect when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * @param subscriber - accepts any method accepting [T] as parameter
 * */
fun <T> Flow<T>.collectOnChange(lifecycleOwner: LifecycleOwner, subscriber: suspend (T) -> Unit) {
    lifecycleOwner.launchOnStart {
        collect {
            subscriber.invoke(it)
        }
    }
}

/**
 * Launches collect when [LifecycleCoroutineScope] is at least in [Lifecycle.State.STARTED] state.
 * @param kMutableProperty0 - accepts any var equal to [T] and is set during [collect]
 * */
suspend infix fun <T> Flow<T>.flowTo(stateFlow: MutableStateFlow<T>) {
    collect {
        stateFlow.value = it
    }
}

fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value.toInt()) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value.toFloat()) }
        is Long -> edit { it.putLong(key, value.toLong()) }
        else -> {
            Log.e("Unsupported Type:", "$value")
        }
    }
}

inline fun <reified T> SharedPrefs.get(key: String, value: T): T {
    return when (value) {
        is String -> this.prefs.getString(key, value) as T
        is Int -> this.prefs.getInt(key, value) as T
        is Boolean -> this.prefs.getBoolean(key, value) as T
        is Float -> this.prefs.getFloat(key, value) as T
        is Long -> this.prefs.getLong(key, value) as T
        else -> {
            throw IllegalStateException("Unsupported Type: $value")
        }
    }
}

fun Date.getFormattedDate(): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("MMM. dd, yyyy")
    return dateFormat.format(currentDate)
}

/**
 * load image url using default argb_8888
 */
fun ImageView.load(
    url: String,
    requestOptions: RequestOptions = RequestOptions()
        .format(DecodeFormat.PREFER_ARGB_8888),
    placeHolder: Int = -1,
    errorHolder: Int = -1
) {
    Glide.with(context)
        .load(url)
        .apply {
            if (placeHolder >= 0) {
                placeholder(placeHolder)
            }
            if (errorHolder >= 0) {
                error(errorHolder)
            }
        }
        .apply(requestOptions)
        .into(this)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
}