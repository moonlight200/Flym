package wtf.moonlight.flym.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wtf.moonlight.flym.data.FlymDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlymDbModule {
    @Provides
    @Singleton
    fun provideFlymDatabase(@ApplicationContext context: Context): FlymDatabase =
        FlymDatabase.build(context)

    @Provides
    @Singleton
    fun provideEntryDao(database: FlymDatabase) = database.entryDao()

    @Provides
    @Singleton
    fun provideFeedDao(database: FlymDatabase) = database.feedDao()
}