package com.evantemplate.cats.network

import com.evantemplate.cats.models.Cat
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.thecatapi.com/v1/images/search/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var gson = GsonBuilder()
    .setLenient()
    .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()



interface CatApiService{
    @GET(".")
    fun getCats(
        @Query("limit") limit: String,
        @Query("page") page: String,
        @Query("order") order: String
    ): Flowable<List<Cat>>
}

object CatApi{
    val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}


interface DownloadCatApiService{
    @GET("images/e27.jpg")
    fun downloadImg(): Call<ResponseBody>
}

val retrofitImg = Retrofit.Builder()
    .baseUrl("https://cdn2.thecatapi.com/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object ImgCatApi{
    val retrofitService: DownloadCatApiService by lazy {
        retrofitImg.create(DownloadCatApiService::class.java)
    }
}
