package com.thecode.vietglobe.di

import android.content.Context
import androidx.room.Room
import com.thecode.vietglobe.data.local.VietGlobeDataStore
import com.thecode.vietglobe.data.local.AppDatabase
import com.thecode.vietglobe.data.local.datasource.VietGlobeLocalDataSource
import com.thecode.vietglobe.data.local.datasource.VietGlobeLocalDataSourceImpl
import com.thecode.vietglobe.data.local.model.article.ArticlesDao
import com.thecode.vietglobe.data.local.model.source.SourcesDao
import com.thecode.vietglobe.data.remote.service.DeepSeekTranslationService
import com.thecode.vietglobe.data.remote.service.GeminiSummarizationService
import com.thecode.vietglobe.data.remote.service.SummarizationService
import com.thecode.vietglobe.data.remote.service.TranslationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DataModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "vietglobe.db"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideVietGlobeDataStore(dataStore: VietGlobeDataStore): VietGlobeLocalDataSource {
        return VietGlobeLocalDataSourceImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideArticlesDao(database: AppDatabase): ArticlesDao {
        return database.getArticlesDao()
    }

    @Singleton
    @Provides
    fun provideSourcesDao(database: AppDatabase): SourcesDao {
        return database.getSourcesDao()
    }
}
