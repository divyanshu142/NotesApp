package com.example.notesapp.Singalton

import com.example.notesapp.API.AuthIntercepter
import com.example.notesapp.API.NotesAPI
import com.example.notesapp.API.UserAPI
import com.example.notesapp.Utils.Constants.BaseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun ProvideRetrofitBuilder() : Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseURL)

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authIntercepter: AuthIntercepter) : OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.SECONDS)
            .readTimeout(500, TimeUnit.SECONDS)
               .addInterceptor(authIntercepter).build()

    }

    @Singleton
    @Provides
    fun ProvideUserAPI(retrofitBuilder: Retrofit.Builder) : UserAPI{
        return retrofitBuilder.build().create(UserAPI::class.java)

    }

    @Singleton
    @Provides
    fun ProvideNoteAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient) : NotesAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesAPI::class.java)

    }
}