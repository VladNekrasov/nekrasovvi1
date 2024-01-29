package ru.protei.nekrasovvi1.domain
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
class Note (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id: Long? = null,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "text")
    var text: String,
    @ColumnInfo(name = "remote_id")
    var remoteId: Long? = null
)