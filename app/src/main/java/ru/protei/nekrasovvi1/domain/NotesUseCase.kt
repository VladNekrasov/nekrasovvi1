package ru.protei.nekrasovvi1.domain

import kotlinx.coroutines.flow.Flow
import ru.protei.nekrasovvi1.data.remote.NotesGitHubApi
import ru.protei.nekrasovvi1.data.remote.NotesGitHubRepository

class NotesUseCase(
    private val notesRepo: NotesRepository,
    private val notesApi: NotesGitHubRepository
) {
    suspend fun fillWithInitialNotes(initialNotes: List<Note>){
        notesRepo.deleteAll()
        for (item in initialNotes) {
            notesRepo.add(item)
        }
    }
    suspend fun loadRemoteNotes(){
        notesRepo.deleteAll()
        val remoteNotes = notesApi.list()
        remoteNotes.forEach {
            it.remoteId?.let {it2 ->
                notesRepo.byRemoteId(it2)?.run {
                    notesRepo.update(it)
                } ?: notesRepo.add(it)
            }
        }
    }
    fun notesFlow(): Flow<List<Note>> {
        return notesRepo.allFlow()
    }
    suspend fun save(note: Note){
        if (note.id == null) {
            notesRepo.add(note)
        } else {
            notesRepo.update(note)
        }
    }
}