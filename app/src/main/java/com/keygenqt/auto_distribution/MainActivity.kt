/*
 * Copyright 2021 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.keygenqt.auto_distribution

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*
import com.keygenqt.auto_distribution.ui.theme.AndroidAutoAppDistributionTheme
import com.keygenqt.auto_distribution.ui.theme.BottomPreview
import com.keygenqt.auto_distribution.ui.theme.BottomPreviewText
import com.keygenqt.auto_distribution.ui.theme.TopPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private val isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAutoAppDistributionTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Body()
                }
            }
        }

        // Splash delay
        window.decorView.findViewById<View>(android.R.id.content)?.let { content ->
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (isReady.value) {
                            content.viewTreeObserver.removeOnPreDrawListener(this); true
                        } else false
                    }
                }
            )
        }
    }
}

@Composable
fun Body() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (cycle, bg) = createRefs()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Column {
                ConstraintLayout(
                    modifier = Modifier
                        .background(TopPreview)
                        .weight(0.1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = BottomPreviewText,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .constrainAs(bg) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }
                ConstraintLayout(
                    modifier = Modifier
                        .background(BottomPreview)
                        .weight(0.1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.create_date),
                        color = TopPreview,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .constrainAs(bg) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }
            }
        }

        Cycle(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(cycle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )


    }
}

@Composable
fun Cycle(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.cycle_on_hill),
        onRetry = { _, _ -> isReady.value = false; true }
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )

    // stop splash for animation after already started
    SideEffect {
        scope.launch {
            delay(200)
            isReady.value = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidAutoAppDistributionTheme {
        Body()
    }
}