package ru.protei.nekrasovvi1.ui.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.protei.nekrasovvi1.domain.Note

class NotesViewModel : ViewModel() {
    val selected  = mutableStateOf<Note?>(null)
    val notes = mutableStateListOf<Note>(
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

    fun onNoteChange(title: String, text: String){
        selected.value=Note(
            title = title,
            text = text
        )
    }

    fun onEditComplete(){
        notes.add(0,selected.value!!)
        selected.value=null
    }

    fun onNoteSelected(note: Note){
        selected.value=note
        notes.remove(note)
    }

    fun onAddNoteClicked(){
        if (selected.value==null){
            selected.value= Note(
                title = "",
                text = ""
            )
        }
    }
}