package ru.protei.nekrasovvi1.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.protei.nekrasovvi1.data.local.NotesRepositoryDB
import ru.protei.nekrasovvi1.data.remote.NotesGitHubRepository
import ru.protei.nekrasovvi1.domain.NotesRemoteRepository
import ru.protei.nekrasovvi1.domain.NotesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {
    @Singleton
    @Binds
    fun bindNotesRepository(notesRepository: NotesRepositoryDB): NotesRepository

    @Singleton
    @Binds
    fun bindNotesRemoteRepository(notesRepo: NotesGitHubRepository): NotesRemoteRepository
}