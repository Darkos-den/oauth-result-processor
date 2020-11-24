package com.darkos.oauth.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class OAuthResultProcessor {
    interface Listener {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    private var listeners: List<Listener> by weakList()
    internal var activity: AppCompatActivity? by weak()

    fun attach(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun addListener(block: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit) {
        listeners = listeners + object : Listener {
            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                block(requestCode, resultCode, data)
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        listeners.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}