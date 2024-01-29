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
import ru.protei.nekrasovvi1.data.local.NotesDatabase
import ru.protei.nekrasovvi1.data.local.NotesRepositoryDB
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

    private val notesRepo by lazy { NotesRepositoryDB(notesDao = database.notesDao()) }
    private val notesUseCase by lazy { NotesUseCase(notesRepo) }

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




