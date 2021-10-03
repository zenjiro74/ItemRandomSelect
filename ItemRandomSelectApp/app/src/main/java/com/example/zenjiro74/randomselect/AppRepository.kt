package com.example.zenjiro74.randomselect

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val preferenceName = "RoulettePrefs"
const val dataTag = "DataString"

interface Repository {
    fun getData(defValue: String): String?

    fun setData(items: String)
}

class RepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : Repository {
    private val pref = context.getSharedPreferences(
        preferenceName,
        Context.MODE_PRIVATE
    )

    override fun getData(defValue: String): String? {
        return pref.getString(dataTag, defValue)
    }

    override fun setData(items: String) {
        pref.edit().putString(dataTag, items).apply()
    }
}