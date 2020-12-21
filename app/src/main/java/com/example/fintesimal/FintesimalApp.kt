package com.example.fintesimal

import android.app.Application
import com.example.fintesimal.data.db.AppDatabase
import com.example.fintesimal.data.network.Api
import com.example.fintesimal.data.network.NetworkConnectionInterceptor
import com.example.fintesimal.data.repository.MapRepository
import com.example.fintesimal.ui.detail.SecondViewModelFactory
import com.example.fintesimal.ui.history.FirstViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FintesimalApp: Application(), KodeinAware {
    override val kodein: Kodein
        get() = Kodein.lazy {
            import(androidXModule(this@FintesimalApp))
            bind() from singleton { NetworkConnectionInterceptor(instance(), instance()) }
            bind() from singleton { Api(instance()) }
            bind() from singleton { AppDatabase(instance()) }
            bind() from singleton { MapRepository(instance(), instance()) }
            bind() from provider  { FirstViewModelFactory(instance()) }
            bind() from provider { SecondViewModelFactory(instance()) }
        }
}