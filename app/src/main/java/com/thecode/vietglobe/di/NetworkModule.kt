package com.thecode.vietglobe.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thecode.vietglobe.BuildConfig
import com.thecode.vietglobe.data.remote.NewsApiRemoteService
import com.thecode.vietglobe.data.remote.NewsApiRemoteServiceImpl
import com.thecode.vietglobe.data.remote.api.NewsApi
import com.thecode.vietglobe.data.remote.mapper.NewsMapper
import com.thecode.vietglobe.utils.AppConstants
import com.thecode.vietglobe.utils.AppConstants.REQUEST_TIMEOUT
import com.thecode.vietglobe.data.remote.service.GeminiSummarizationService
import com.thecode.vietglobe.data.remote.service.SummarizationService
import com.thecode.vietglobe.data.remote.service.DeepSeekTranslationService
import com.thecode.vietglobe.data.remote.service.TranslationService
import com.thecode.vietglobe.data.remote.datasource.VietGlobeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(AppConstants.NEWSAPI_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getOkHttpService())
    }

    @Singleton
    @Provides
    fun provideWhoService(retrofit: Retrofit.Builder): NewsApi {
        return retrofit
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideVietGlobeRemoteService(
        api: NewsApi
    ): NewsApiRemoteService {
        return NewsApiRemoteServiceImpl(api)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        apiService: NewsApiRemoteService,
        newsMapper: NewsMapper
    ): VietGlobeRemoteDataSourceImpl {
        return VietGlobeRemoteDataSourceImpl(
            apiService,
            newsMapper
        )
    }

    @Provides
    @Singleton
    fun provideSummarizationService(): SummarizationService {
        return GeminiSummarizationService()
    }

    @Provides
    @Singleton
    fun provideTranslationService(): TranslationService {
        return DeepSeekTranslationService()
    }

    private fun getOkHttpService(): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(interceptor)
        }
        httpClient.addInterceptor(BasicAuthInterceptor())

        return httpClient.build()
    }

    class BasicAuthInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newUrl =
                request.url.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
            val newRequest = request.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }
}
