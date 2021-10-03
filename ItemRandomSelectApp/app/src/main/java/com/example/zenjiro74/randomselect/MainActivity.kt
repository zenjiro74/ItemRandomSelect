package com.example.zenjiro74.randomselect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenjiro74.randomselect.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}