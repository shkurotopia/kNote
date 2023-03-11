package xyz.shkurotopia.knote.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.shkurotopia.knote.data.INoteRepository
import xyz.shkurotopia.knote.data.NoteDatabase
import xyz.shkurotopia.knote.data.NoteRepository
import xyz.shkurotopia.knote.usecases.AddNote
import xyz.shkurotopia.knote.usecases.DeleteNote
import xyz.shkurotopia.knote.usecases.GetNote
import xyz.shkurotopia.knote.usecases.GetNotes
import xyz.shkurotopia.knote.usecases.UseCases
import xyz.shkurotopia.knote.utils.DB_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideNoteDb(app: Application): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        DB_NAME
    ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): INoteRepository = NoteRepository(db.noteDao)

    @Singleton
    @Provides
    fun provideUseCases(repo: INoteRepository): UseCases = UseCases(
        addNote = AddNote(repo),
        deleteNote = DeleteNote(repo),
        getNote = GetNote(repo),
        getNotes = GetNotes(repo)
    )
}