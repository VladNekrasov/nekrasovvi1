package ru.protei.nekrasovvi1.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.protei.nekrasovvi1.domain.Note
import ru.protei.nekrasovvi1.domain.NotesRemoteRepository

class NotesGitHubRepository(private val notesApi: NotesGitHubApi): NotesRemoteRepository{
    override suspend fun list(): List<Note> = withContext(Dispatchers.IO) {
        var issues: List<GitHubIssue>?
        try {
            val result = notesApi.getList()
            if (!result.isSuccessful) {
                Log.w("NotesRepositoryApi", "Can't get issues $result")
                return@withContext emptyList()
            }

            issues = result.body()

        } catch (e: Exception) {
            Log.w("NotesGitHubRepository", "Can't get issues", e)
            return@withContext emptyList()
        }

        val notes = issues?.map {
            toNote(it)
        } ?: emptyList()

        notes
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