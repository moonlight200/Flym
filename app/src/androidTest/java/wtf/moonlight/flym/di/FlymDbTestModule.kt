package wtf.moonlight.flym.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wtf.moonlight.flym.data.FlymDatabase
import wtf.moonlight.flym.data.model.Feed
import wtf.moonlight.flym.data.model.FeedSettings
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlymDbTestModule {
    @Singleton
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): FlymDatabase =
        Room.inMemoryDatabaseBuilder(context, FlymDatabase::class.java)
            .allowMainThreadQueries()
            .build()
            .also {
                it.feedDao().insertFeed(
                    Feed(
                        link = "https://feedforall.com/sample.xml",
                        title = "Sample Feed 1",
                        settings = FeedSettings(
                            retrieveFullText = false
                        )
                    )
                )
                it.feedDao().insertFeed(
                    Feed(
                        link = "https://feedforall.com/sample-feed.xml",
                        title = "Sample Feed 2",
                        settings = FeedSettings(
                            retrieveFullText = false
                        )
                    )
                )
            }
}