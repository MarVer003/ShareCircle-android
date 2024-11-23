package com.example.sharecircle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sharecircle.ui.theme.ShareCircleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShareCircleTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShareCircleLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                ShareCircleApp()
            }
        }
    }
}

