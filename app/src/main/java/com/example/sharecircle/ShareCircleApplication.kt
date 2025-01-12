package com.example.sharecircle

import AppContainer
import AppDataContainer
import android.app.Application
import com.example.sharecircle.data.MyDatabase

class ShareCircleApplication : Application() {


    //The instance of AppContainer is used by other
    // classes to obtain dependencies.
    lateinit var repository: AppContainer

    override fun onCreate() {
        super.onCreate()
        val database = MyDatabase.getDatabase(this)
        repository = AppDataContainer(this)
    }
}