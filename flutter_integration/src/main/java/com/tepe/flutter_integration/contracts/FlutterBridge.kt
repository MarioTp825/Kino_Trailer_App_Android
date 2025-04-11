package com.tepe.flutter_integration.contracts

import android.content.Context
import android.content.Intent
import android.view.View

interface FlutterBridge {

    fun initEngine(context: Context)

    fun buildIntent(context: Context): Intent

    fun getView(context: Context): View?

    fun sendData(data: String, param: MovieParams)
}