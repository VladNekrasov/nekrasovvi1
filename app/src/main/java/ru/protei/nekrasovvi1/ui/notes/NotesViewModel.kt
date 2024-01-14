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
            val initialNotes = listOf(
                Note(
                    title = "Покупки",
                    text = "Молоко, яйца, хлеб"
                ),
                Note(
                    title = "Встреча",
                    text = "Встреча с друзьями в 19:00"
                ),
                Note(
                    title = "Уроки",
                    text = "Геометрия, физика, история"
                ),
                Note(
                    title = "Основные пункты",
                    text = "1. Закончить проект. 2. Позвонить маме. 3. Купить подарок"
                ),
                Note(
                    title = "Идеи для путешествия",
                    text = "Париж, Рим, Барселона"
                ),
                Note(
                    title = "Книги для прочтения",
                    text = "1984 - Джордж Оруэлл, Мастер и Маргарита - Михаил Булгаков"
                ),
                Note(
                    title = "План тренировки",
                    text = "1. Прогулка 30 минут. 2. Зарядка 15 минут. 3. Тренировка с гантелями 30 минут."
                ),
                Note(
                    title = "Список дел",
                    text = "Постирать белье, погладить рубашки, приготовить обед"
                )
            )
            notesUseCase.fillWithInitialNotes(initialNotes)
            _notes.value = initialNotes
        }
    }

    fun onNoteChange(title: String, text: String){
        selected=Note(
            title = title,
            text = text
        )
    }

     fun onEditComplete(){
        if (selected?.title?.isNotEmpty() == true || selected?.text?.isNotEmpty() == true) {
            //_notes.value.add(0, selected!!)
            viewModelScope.launch {
                notesUseCase.save(selected!!)
            }
        }
          selected=null
    }

    fun onNoteSelected(note: Note){
        selected=note
    }

    fun onAddNoteClicked(){
//        if (selected==null){
//            selected=Note(
//                title = "",
//                text = ""
//            )
//        }
    }
}