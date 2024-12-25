package com.times.focus.di

import android.content.Context
import com.times.focus.data.db.FocusDatabase
import com.times.focus.data.db.dao.FocusSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FocusDatabase {
        return FocusDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFocusSessionDao(database: FocusDatabase): FocusSessionDao {
        return database.focusSessionDao()
    }
} 