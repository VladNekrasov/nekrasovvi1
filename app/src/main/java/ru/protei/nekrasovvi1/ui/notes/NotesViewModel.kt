package ru.protei.nekrasovvi1.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.protei.nekrasovvi1.domain.Note
import ru.protei.nekrasovvi1.domain.NotesUseCase

class NotesViewModel(private val notesUseCase: NotesUseCase) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()
    var selected  by mutableStateOf<Note?>(null)
        private set
    init {
        viewModelScope.launch {
            notesUseCase.loadRemoteNotes()
            notesUseCase.notesFlow().collect { newNotes ->
                _notes.value = newNotes
            }
        }
    }
    fun onNoteChange(title: String, text: String){
        selected = Note(
            id = this.selected?.id,
            title = title,
            text = text,
            remoteId = this.selected?.remoteId
        )
    }
    fun onEditComplete(){
        if (selected?.title?.isNotEmpty() == true || selected?.text?.isNotEmpty() == true) {
            viewModelScope.launch {
                notesUseCase.save(selected!!)
                selected=null
            }
        }
    }
    fun onNoteSelected(note: Note){
        selected=note
    }
    fun onAddNoteClicked(){
        if (selected==null){
            selected=Note(
                title = "",
                text = ""
            )
        }
    }
}