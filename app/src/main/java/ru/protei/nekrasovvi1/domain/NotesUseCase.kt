package ru.protei.nekrasovvi1.domain

import kotlinx.coroutines.flow.Flow
import ru.protei.nekrasovvi1.data.remote.NotesGitHubRepository
import javax.inject.Inject

class NotesUseCase @Inject constructor(
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
        val remoteNotes = notesApi.list()
        remoteNotes.forEach {
            it.remoteId?.let {it2 ->
                notesRepo.byRemoteId(it2)?.run {
                    it.id=this.id
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
            notesApi.add(note)?.let {
                note.remoteId=it
                notesRepo.add(note)
            }
        } else {
            if (notesApi.update(note))
                notesRepo.update(note)
        }
    }
}