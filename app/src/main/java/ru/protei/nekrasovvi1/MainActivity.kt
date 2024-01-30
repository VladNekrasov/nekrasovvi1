package ru.protei.nekrasovvi1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.nekrasovvi1.data.local.NotesDatabase
import ru.protei.nekrasovvi1.data.local.NotesRepositoryDB
import ru.protei.nekrasovvi1.data.remote.NotesGitHubApi
import ru.protei.nekrasovvi1.data.remote.NotesGitHubRepository
import ru.protei.nekrasovvi1.domain.NotesUseCase
import ru.protei.nekrasovvi1.ui.notes.NotesScreen
import ru.protei.nekrasovvi1.ui.notes.NotesViewModel
import ru.protei.nekrasovvi1.ui.theme.Nekrasovvi1Theme

class MainActivity : ComponentActivity() {

    private val database: NotesDatabase by lazy {
        Room.databaseBuilder(
            this,
            NotesDatabase::class.java, "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    var httpClient = OkHttpClient.Builder()
        .addInterceptor {chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer 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"
                )
                .build()
            chain.proceed(request)
        }
        .build()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/VladNekrasov/nekrasovvi1/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val notesRepo by lazy { NotesRepositoryDB(notesDao = database.notesDao()) }
    private val notesApi by lazy { NotesGitHubRepository(retrofit.create(NotesGitHubApi::class.java)) }
    private val notesUseCase by lazy { NotesUseCase(notesRepo, notesApi) }

    private val notesViewModel: NotesViewModel by viewModels {
        viewModelFactory{
            initializer {
                NotesViewModel(notesUseCase)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nekrasovvi1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    NotesScreen(vm = notesViewModel)
                }
            }
        }
    }
}




