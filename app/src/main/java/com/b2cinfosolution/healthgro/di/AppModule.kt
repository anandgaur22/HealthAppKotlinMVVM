package com.b2cinfosolution.healthgro.di


import com.b2cinfosolution.healthgro.application.App
import com.b2cinfosolution.healthgro.utils.Network
import com.b2cinfosolution.healthgro.utils.NetworkConnectivity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(): NetworkConnectivity {
        return Network(App.context)
    }
}
