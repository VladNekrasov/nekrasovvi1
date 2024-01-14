package ru.protei.nekrasovvi1.domain
interface NotesRepository {
    suspend fun add(note: Note): Long
    suspend fun update(note: Note)
    fun deleteAll()
}