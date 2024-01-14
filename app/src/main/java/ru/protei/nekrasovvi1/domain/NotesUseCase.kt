package ru.protei.nekrasovvi1.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
class NotesUseCase(
    private val notesRepo: NotesRepository
) {
    suspend fun fillWithInitialNotes(initialNotes: List<Note>){
        notesRepo.deleteAll()
        for (item in initialNotes) {
            notesRepo.add(item)
        }
    }
    fun notesFlow(): Flow<List<Note>> {
        return notesRepo.allFlow()
    }
    suspend fun delete(id: Long){
        notesRepo.deleteById(id)
    }
    suspend fun save(note: Note){
        if (note.id == null) {
            notesRepo.add(note)
        } else {
            notesRepo.update(note)
        }
    }
}