package ru.protei.nekrasovvi1.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.protei.nekrasovvi1.domain.Note
import ru.protei.nekrasovvi1.domain.NotesRepository

class NotesRepositoryDB(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val notesDao: NotesDao
): NotesRepository
{
    override suspend fun add(note: Note): Long = withContext(ioDispatcher) {
        return@withContext notesDao.insert(note)
    }

    override suspend fun update(note: Note) = withContext(ioDispatcher) {
        notesDao.update(note)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        notesDao.deleteAll()
    }
}