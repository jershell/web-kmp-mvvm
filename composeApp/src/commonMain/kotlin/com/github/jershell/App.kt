package com.github.jershell

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.github.jershell.containers.foobar.FooBar
import web_kmp_mvvm.composeapp.generated.resources.*
import com.github.jershell.theme.AppTheme
import com.github.jershell.theme.LocalThemeIsDark
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun App() = AppTheme {
    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing, {
        refreshing = true
    })

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3.seconds)
            window.location.reload()
            refreshing = false
        }
    }

    Box(Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(48.dp))

            FooBar()

            Spacer(Modifier.height(48.dp))

            Text(
                text = stringResource(Res.string.cyclone),
                fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
                style = MaterialTheme.typography.displayLarge
            )

            var isAnimate by remember { mutableStateOf(false) }
            val transition = rememberInfiniteTransition()
            val rotate by transition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing)
                )
            )

            Image(
                modifier = Modifier
                    .size(250.dp)
                    .padding(16.dp)
                    .run { if (isAnimate) rotate(rotate) else this },
                imageVector = vectorResource(Res.drawable.ic_cyclone),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                contentDescription = null
            )

            ElevatedButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .widthIn(min = 200.dp),
                onClick = { isAnimate = !isAnimate },
                content = {
                    Icon(vectorResource(Res.drawable.ic_rotate_right), contentDescription = null)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        stringResource(if (isAnimate) Res.string.stop else Res.string.run)
                    )
                }
            )

            var isDark by LocalThemeIsDark.current
            val icon = remember(isDark) {
                if (isDark) Res.drawable.ic_light_mode
                else Res.drawable.ic_dark_mode
            }

            ElevatedButton(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
                onClick = { isDark = !isDark },
                content = {
                    Icon(vectorResource(icon), contentDescription = null)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(Res.string.theme))
                }
            )

            TextButton(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
                onClick = { openUrl("https://github.com/terrakok") },
            ) {
                Text(stringResource(Res.string.open_github))
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

internal expect fun openUrl(url: String?)