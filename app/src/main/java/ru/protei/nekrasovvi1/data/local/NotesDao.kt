package ru.protei.nekrasovvi1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.protei.nekrasovvi1.domain.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM note")
    suspend fun all():List<Note>

    @Query("SELECT * FROM note ORDER BY note_id ASC")
    fun allFlow(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM note WHERE note_id= :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM note")
    suspend fun deleteAll()

    @Query("SELECT * FROM note WHERE remote_id= :remoteId")
    suspend fun byRemoteId(remoteId: Long): Note?
}