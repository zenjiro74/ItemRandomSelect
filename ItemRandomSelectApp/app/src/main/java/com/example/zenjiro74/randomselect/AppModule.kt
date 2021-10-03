package com.example.zenjiro74.randomselect

import com.example.zenjiro74.randomselect.Repository
import com.example.zenjiro74.randomselect.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}