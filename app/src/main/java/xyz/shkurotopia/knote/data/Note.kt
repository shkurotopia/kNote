package xyz.shkurotopia.knote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import xyz.shkurotopia.knote.ui.theme.*
@Entity(tableName = "note", indices = [Index("id")])
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")                val id: Int = 0,
    @ColumnInfo(name = "title")             val title: String,
    @ColumnInfo(name = "content")           val content: String,
    @ColumnInfo(name = "timestamp")         val timestamp: Long,
    @ColumnInfo(name = "category")          val category: Int = 1
) {
    class InvalidNoteException(msg: String): Exception(msg)

    override fun toString(): String = id.toString()
}
