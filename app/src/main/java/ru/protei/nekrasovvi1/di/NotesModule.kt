package ru.protei.nekrasovvi1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.nekrasovvi1.data.local.NotesDao
import ru.protei.nekrasovvi1.data.local.NotesDatabase
import ru.protei.nekrasovvi1.data.remote.NotesGitHubApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java, "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(db: NotesDatabase): NotesDao {
        return db.notesDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer github_token"
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/VladNekrasov/nekrasovvi1/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesGitHubApi(retrofit: Retrofit): NotesGitHubApi {
        return retrofit.create(NotesGitHubApi::class.java)
    }
}