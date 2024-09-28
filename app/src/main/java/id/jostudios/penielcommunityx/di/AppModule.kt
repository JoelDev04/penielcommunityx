package id.jostudios.penielcommunityx.di

import com.google.android.gms.auth.api.signin.internal.Storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.jostudios.penielcommunityx.data.remote.AuthAPI
import id.jostudios.penielcommunityx.data.remote.DatabaseAPI
import id.jostudios.penielcommunityx.data.remote.StorageAPI
import id.jostudios.penielcommunityx.data.repository.AuthRepositoryImpl
import id.jostudios.penielcommunityx.data.repository.DatabaseRepositoryImpl
import id.jostudios.penielcommunityx.data.repository.StorageRepositoryImpl
import id.jostudios.penielcommunityx.domain.repository.AuthRepository
import id.jostudios.penielcommunityx.domain.repository.DatabaseRepository
import id.jostudios.penielcommunityx.domain.repository.StorageRepository
import id.jostudios.penielcommunityx.domain.use_case.login.LoginUseCase
import id.jostudios.penielcommunityx.domain.use_case.signup.SignupUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthAPI(): AuthAPI {
        return AuthAPI();
    }

    @Provides
    @Singleton
    fun provideDBAPI(): DatabaseAPI {
        return DatabaseAPI();
    }

    @Provides
    @Singleton
    fun provideStorageAPI(): StorageAPI {
        return StorageAPI();
    }

    @Provides
    @Singleton
    fun provideAuthRepo(api: AuthAPI): AuthRepository {
        return AuthRepositoryImpl(api);
    }

    @Provides
    @Singleton
    fun provideDBRepo(api: DatabaseAPI): DatabaseRepository {
        return DatabaseRepositoryImpl(api);
    }

    @Provides
    @Singleton
    fun provideStorageRepo(api: StorageAPI): StorageRepository {
        return StorageRepositoryImpl(api);
    }
}