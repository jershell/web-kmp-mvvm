package com.github.jershell.containers.foobar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

@Composable
fun FooBar() {
    println("> ${LocalViewModelStoreOwner.current}")

    val vm = viewModel(
        key = "FooBar",
        modelClass = FooBarViewModel::class,
        factory = viewModelFactory { FooBarViewModel() }
    )

    val dt by vm.mutableValue.collectAsState()

    Box(
        modifier = Modifier
            .size(256.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE6E6E6))
            .clickable { vm.refreshDT() }
            .padding(16.dp)
    ) {
        Text(text = dt, Modifier.align(Alignment.Center))
    }
}