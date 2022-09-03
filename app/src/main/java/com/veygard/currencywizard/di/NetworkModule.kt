package com.veygard.currencywizard.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.veygard.currencywizard.data.network.api.CurrenciesConvertApi
import com.veygard.currencywizard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizard.data.network.api.CurrenciesGetAllApi
import com.veygard.currencywizard.data.network.model.currencies.convert.ConvertResultDeserializer
import com.veygard.currencywizard.data.network.model.currencies.convert.CurrenciesConvertApiResponse
import com.veygard.currencywizard.data.network.model.currencies.fetch.FetchApiResponse
import com.veygard.currencywizard.data.network.model.currencies.fetch.FetchResultDeserializer
import com.veygard.currencywizard.data.network.model.currencies.getall.CurrenciesGetAllResultDeserializer
import com.veygard.currencywizard.data.network.model.currencies.getall.GetAllApiResponse
import com.veygard.currencywizard.util.Constants
import com.veygard.currencywizard.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val RETROFIT_FETCH_NAME  = "FetchRetrofit"
const val RETROFIT_GET_ALL_NAME  = "AllCurrenciesRetrofit"
const val RETROFIT_CONVERT_NAME  = "convertRetrofit"


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCurrenciesFetchService(@Named(RETROFIT_FETCH_NAME) retrofit: Retrofit): CurrenciesFetchApi = retrofit.create(
        CurrenciesFetchApi::class.java)

    @Provides
    @Singleton
    fun provideCurrenciesAllService(@Named(RETROFIT_GET_ALL_NAME) retrofit: Retrofit): CurrenciesGetAllApi = retrofit.create(
        CurrenciesGetAllApi::class.java)

    @Provides
    @Singleton
    fun provideCurrenciesConvertService(@Named(RETROFIT_CONVERT_NAME) retrofit: Retrofit): CurrenciesConvertApi = retrofit.create(
        CurrenciesConvertApi::class.java)

    @Provides
    @Singleton
    @Named(RETROFIT_FETCH_NAME)
    fun provideCurrenciesFetchClient(): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(FetchApiResponse::class.java, FetchResultDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideHttpClient())
            .build()
    }

    @Provides
    @Singleton
    @Named(RETROFIT_GET_ALL_NAME)
    fun provideCurrenciesAllClient(): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(GetAllApiResponse::class.java, CurrenciesGetAllResultDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideHttpClient())
            .build()
    }

    @Provides
    @Singleton
    @Named(RETROFIT_CONVERT_NAME)
    fun provideCurrenciesConvertClient(): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(CurrenciesConvertApiResponse::class.java, ConvertResultDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideHttpClient())
            .build()
    }



    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val queryInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()

            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(queryInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

}