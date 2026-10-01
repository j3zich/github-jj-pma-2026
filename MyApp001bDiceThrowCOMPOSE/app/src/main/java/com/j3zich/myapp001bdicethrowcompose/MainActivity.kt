package com.j3zich.myapp001bdicethrowcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DiceApp()
            }
        }
    }
}

@Preview
@Composable
fun DiceApp() {
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
    // remember last value of mutableInStateOf, won't initialize default value again
    var diceValue by remember { mutableIntStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }
    val history = remember { mutableStateListOf<Int>() }
    val scope = rememberCoroutineScope()

    val backgroundColor = Color(0xFFF5F3FF)
    val primaryColor = Color(0xFF352060)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hoď kostkou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )
        Text(
            text = diceSymbols[diceValue - 1],
            fontSize = 120.sp,
            color = primaryColor,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        Button(
            enabled = !isRolling,
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.White,
            ),
            onClick = {
                isRolling = true

                scope.launch {
                    repeat(10) {
                        diceValue = (1..6).random()
                        delay(250.milliseconds)
                    }

                    history.add(0, diceValue)
                    if (history.size > 5) history.removeAt(5)
                    isRolling = false
                }
            }

        ) {
            Text(
                text = "Hodit",
                fontSize = 26.sp
            )
        }
        Spacer(Modifier.height(24.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            )
            {
                Text(
                    text = "Historie",
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                Text(
                    text = history.joinToString { diceSymbols[it - 1] },
                    fontSize = 60.sp
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Statistika",
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                Text(
                    text = (1..6).joinToString("\n") { v -> diceSymbols[v - 1] + " " + "●".repeat(history.count { it == v }) },
                    fontSize = 32.sp,
                    lineHeight = 40.sp
                )
            }
        }
    }
}