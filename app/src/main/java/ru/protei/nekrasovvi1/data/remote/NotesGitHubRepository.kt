package ru.protei.nekrasovvi1.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.protei.nekrasovvi1.domain.Note
import ru.protei.nekrasovvi1.domain.NotesRemoteRepository
import javax.inject.Inject

class NotesGitHubRepository @Inject constructor(private val notesApi: NotesGitHubApi): NotesRemoteRepository{

    private fun toNote(gitHubIssue: GitHubIssue): Note {
        return Note(
            id = null,
            title = gitHubIssue.title,
            text = gitHubIssue.body,
            remoteId = gitHubIssue.number
        )
    }

    private fun toGitHubIssue(note: Note): GitHubIssue {
        return GitHubIssue(
            number = note.remoteId,
            title = note.title,
            body = note.text
        )
    }

    override suspend fun list(): List<Note> = withContext(Dispatchers.IO) {
        val issues: List<GitHubIssue>?
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

    override suspend fun add(note: Note): Long? = withContext(Dispatchers.IO) {
        try {
            val issue = toGitHubIssue(note)
            val result = notesApi.add(issue)
            if (!result.isSuccessful) {
                Log.w("NotesRepositoryApi", "Can't create issue $result")
                return@withContext null
            }

            result.body()?.number

        } catch (e: Exception) {
            Log.w("NotesGitHubRepository", "Can't create issue", e)
            null
        }
    }

    override suspend fun update(note: Note): Boolean = withContext(Dispatchers.IO) {
        try {
            val issue = toGitHubIssue(note)
            issue.number?.let {
                val result = notesApi.update(it, issue)
                if (!result.isSuccessful) {
                    Log.w("NotesRepositoryApi", "Can't update issue $result")
                    false
                } else
                    true
            } ?: run {
                Log.w("NotesRepositoryApi", "Can't update issue")
                false
            }
        } catch (e: Exception) {
            Log.w("NotesGitHubRepository", "Can't update issue", e)
            false
        }
    }

}