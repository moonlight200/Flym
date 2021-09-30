package wtf.moonlight.flym.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wtf.moonlight.flym.repositories.EntriesRepository
import wtf.moonlight.flym.repositories.EntriesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FlymRepoModule {
    @Binds
    abstract fun bindEntriesRepository(
        entriesRepositoryImpl: EntriesRepositoryImpl
    ): EntriesRepository
}