package com.example.sharecircle.ui.screens

import kotlin.math.roundToInt
import kotlin.random.Random

data class HomeScreenUIState (
    val expenses: List<Double> = List(20) { (Random.nextDouble(100.0) * 100).roundToInt() / 100.0 }
)