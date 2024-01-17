package com.stephanieverissimo.rickandmortyapi.di

import android.app.Application
import com.stephanieverissimo.rickandmortyapi.data.api.CharacterApi
import com.stephanieverissimo.rickandmortyapi.data.api.ServiceApi
import com.stephanieverissimo.rickandmortyapi.domain.repository.CharacterRepository
import com.stephanieverissimo.rickandmortyapi.domain.repository.CharacterRepositoryImpl
import com.stephanieverissimo.rickandmortyapi.presentation.viewModel.CharacterScreenModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
    
    private val appModule = module {
        single<CharacterApi> { ServiceApi.characterApi }
        single<CharacterRepository> { CharacterRepositoryImpl(characterApi = get()) }
        factory { CharacterScreenModel(repository = get()) }
        
    }
}
