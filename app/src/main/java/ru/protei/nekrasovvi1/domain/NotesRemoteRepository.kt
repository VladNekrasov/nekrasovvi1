package ru.protei.nekrasovvi1.domain

interface NotesRemoteRepository {
    suspend fun list(): List<Note>
    suspend fun add(note: Note): Long?
    suspend fun update(note: Note): Boolean
}