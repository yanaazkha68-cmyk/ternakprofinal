package com.ternakpro.app

import android.app.Application
import com.ternakpro.app.database.AppDatabase
import com.ternakpro.app.repository.TernakRepository

class TernakProApplication : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }

    val repository: TernakRepository by lazy {
        TernakRepository(database)
    }
}
