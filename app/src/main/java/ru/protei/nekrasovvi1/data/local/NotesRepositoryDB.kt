package ru.protei.nekrasovvi1.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.nekrasovvi1.domain.Note
import ru.protei.nekrasovvi1.domain.NotesRepository
import javax.inject.Inject

class NotesRepositoryDB @Inject constructor(
    private val notesDao: NotesDao
): NotesRepository
{
    override suspend fun add(note: Note): Long = withContext(Dispatchers.IO) {
        notesDao.insert(note)
    }

    override suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        notesDao.update(note)
    }

    override fun allFlow(): Flow<List<Note>> {
        return notesDao.allFlow()
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        notesDao.deleteAll()
    }

    override suspend fun byRemoteId(remoteId: Long): Note? = withContext(Dispatchers.IO) {
        notesDao.byRemoteId(remoteId)
    }

    override suspend fun deleteById(id: Long) = withContext(Dispatchers.IO){
        notesDao.deleteById(id)
    }
}