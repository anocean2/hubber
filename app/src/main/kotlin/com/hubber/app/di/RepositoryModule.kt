/*
 * Copyright 2021 Andrey Kupriev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.hubber.app.di

import android.content.Context
import com.hubber.app.R
import com.hubber.app.data.followers.ServiceFollower
import com.hubber.app.data.followers.impl.RepositoryFollower
import com.hubber.app.data.repos.ServiceRepo
import com.hubber.app.data.repos.impl.RepositoryRepo
import com.hubber.app.data.user.ServiceUser
import com.hubber.app.data.user.impl.RepositoryUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepositoryRepo(
        preferences: com.hubber.app.base.ApplicationPreferences,
        service: ServiceRepo
    ): RepositoryRepo {
        return RepositoryRepo(preferences, service)
    }

    @Provides
    @ViewModelScoped
    fun provideRepositoryFollower(
        preferences: com.hubber.app.base.ApplicationPreferences,
        service: ServiceFollower,
    ): RepositoryFollower {
        return RepositoryFollower(preferences, service)
    }

    @Provides
    @ViewModelScoped
    fun provideRepositoryUser(
        @ApplicationContext context: Context,
        service: ServiceUser
    ): RepositoryUser {
        return RepositoryUser(service, context.getString(R.string.github_user))
    }
}
