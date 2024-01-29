package ru.protei.nekrasovvi1.data.remote

import ru.protei.nekrasovvi1.domain.Note
import ru.protei.nekrasovvi1.domain.NotesRemoteRepository

class NotesGitHubRepository(private val notesApi: NotesGitHubApi): NotesRemoteRepository{
    override suspend fun list(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun add(note: Note): Long? {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note): Boolean {
        TODO("Not yet implemented")
    }

}