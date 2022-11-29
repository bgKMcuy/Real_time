package id.skripsi.kaem.realtime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.skripsi.kaem.realtime.network.ApiClient
import id.skripsi.kaem.realtime.network.ApiService
import id.skripsi.kaem.realtime.repo.SensorRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService() = ApiClient.instance

    @Singleton
    @Provides
    fun provideSensorRepo(apiService: ApiService) = SensorRepo(apiService)
}