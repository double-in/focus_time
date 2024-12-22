package com.times.foucse_i.di

import android.content.Context
import com.times.foucse_i.data.preferences.FocusPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideFocusPreferences(@ApplicationContext context: Context): FocusPreferences {
        return FocusPreferences(context)
    }
} 