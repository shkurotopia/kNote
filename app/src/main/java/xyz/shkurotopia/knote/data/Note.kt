package xyz.shkurotopia.knote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "note", indices = [Index("id")])
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")                val id: Int? = null,
    @ColumnInfo(name = "title")             val title: String,
    @ColumnInfo(name = "content")           val content: String,
    @ColumnInfo(name = "timestamp")         val timestamp: Long,
    @ColumnInfo(name = "category")          val category: Int = 0
) {
    class InvalidNoteException(msg: String): Exception(msg)

    companion object {
        val noteCategories = listOf<Int>(0, 1, 2, 3)
    }

    override fun toString(): String = id.toString()
}
