package hk.collaction.timber.api.core

import android.content.Context
import com.google.gson.Gson
import hk.collaction.timber.BuildConfig
import hk.collaction.timber.api.service.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager(val context: Context) {
    private val connectTimeout: Long = 15
    private val readTimeout: Long = 15
    private val writeTimeout: Long = 15
    lateinit var apiService: ApiService

    init {
        initClient()
    }

    internal class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "android")
                .addHeader("app", BuildConfig.APPLICATION_ID)
                .addHeader(
                    "app-build-version",
                    BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE
                )
            return chain.proceed(builder.build())
        }
    }

    private fun initClient() {
        val client = OkHttpClient().newBuilder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .addInterceptor(CustomInterceptor())
            .addInterceptor(HttpLoggingInterceptor(HttpLogger()).apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ApiConverterFactory(Gson()))
            .client(client)
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun reInitClient() {
        initClient()
    }
}