package com.dmk.dindinnminiassignment.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Kawawa Dennis
 */
object ApiClient {
    private const val BASE_URL_PROD = "http://192.168.100.29:8080/"
    private const val BASE_URL_LOCAL = "http://192.168.100.29:8080/"
    private const val BASE_URL = BASE_URL_PROD
    private var retrofit: Retrofit? = null

    //Add Interceptor Fosr Mock Data
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val builder = OkHttpClient().newBuilder()
                builder.readTimeout(120, TimeUnit.SECONDS)
                builder.connectTimeout(360, TimeUnit.SECONDS)
                builder.writeTimeout(360, TimeUnit.SECONDS)
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
                builder.addInterceptor(MockDataInterceptor()) //Add Interceptor Fosr Mock Data
                val okHttpClient = builder.build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
}