package cl.giorgioarlandi.hoyse.util.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/Himuravidal/mansions/"

        private var retrofit: Retrofit? = null


        val client: Retrofit
            get() {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(logging)
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .client(httpClient.build())
                        .build()
                }
                return retrofit!!
            }
    }
}