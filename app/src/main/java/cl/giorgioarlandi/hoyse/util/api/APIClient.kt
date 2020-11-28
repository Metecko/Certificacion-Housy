package cl.giorgioarlandi.hoyse.util.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        const val BASE_URL = "http://my-json-server.typicode.com/Himuravidal/mansions/"

        private var retrofit: Retrofit? = null


        val client: Retrofit
            get() {
                val httpClient = OkHttpClient.Builder()
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