package com.example.zenjiro74.randomselect.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenjiro74.randomselect.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val defaultData = "1,2,3"

@ObsoleteCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _event = MutableSharedFlow<Any>(Channel.UNLIMITED)
    val event: SharedFlow<Any> get() = _event

    private var tickerCh: ReceiveChannel<Unit>? = null

    private var isRoulette = false

    fun getItems(): String {
        return repository.getData(defaultData) ?: defaultData
    }

    fun setItems(newItems: String) {
        return repository.setData(newItems)
    }

    fun actionState() {
        val items = getItems()
        val sep = items.split(",")
        isRoulette = if (isRoulette) {

            tickerCh!!.cancel()
            tickerCh = null

            val res = ((1..sep.size).random())
            viewModelScope.launch { _event.emit(sep[res - 1]) }

            false

        } else {
            tickerCh = ticker(100L, 0)
            viewModelScope.launch {
                var cnt = 1
                for (event in tickerCh!!) {
                    if (cnt >= sep.size) cnt = 0
                    _event.emit(sep[cnt])
                    cnt++
                }
            }

            true
        }

        viewModelScope.launch { _event.emit(isRoulette) }
    }
}