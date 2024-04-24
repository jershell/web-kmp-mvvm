package com.github.jershell.containers.foobar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class FooBarViewModel : ViewModel() {
    private fun getNow(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    val mutableValue = MutableStateFlow(getNow().toString())

    fun refreshDT() {
        mutableValue.value = getNow().toString()
    }
}
