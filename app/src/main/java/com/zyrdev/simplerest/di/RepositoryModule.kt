package com.zyrdev.simplerest.di

import com.zyrdev.simplerest.repository.UserRepository
import com.zyrdev.simplerest.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun userRepository(repo: UserRepositoryImpl) : UserRepository


}
