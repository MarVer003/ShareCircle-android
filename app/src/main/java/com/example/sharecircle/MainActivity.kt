package com.example.sharecircle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.sharecircle.ui.theme.ShareCircleTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ShareCircleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = (application as ShareCircleApplication).repository.myRepository

        val viewModelFactory = ShareCircleViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ShareCircleViewModel::class.java]

        setContent {
            ShareCircleTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShareCircleLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                ShareCircleApp(viewModel = viewModel)
            }
        }
    }
}

