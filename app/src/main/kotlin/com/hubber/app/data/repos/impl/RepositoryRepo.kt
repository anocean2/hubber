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
 
package com.hubber.app.data.repos.impl

import com.hubber.app.base.ExceptionNotFound
import com.hubber.app.data.repos.ServiceRepo
import com.hubber.app.extension.toModelRepos
import com.hubber.app.models.ModelRepo
import kotlinx.coroutines.delay
import javax.inject.Inject

class RepositoryRepo @Inject constructor(
    private val preferences: com.hubber.app.base.ApplicationPreferences,
    private val service: ServiceRepo
) {
    suspend fun getModels(page: Int): List<ModelRepo> {
        delay(1000) // slow internet
        return service.getlistRepo(preferences.reposUrl, page).body()?.toModelRepos(page) ?: throw ExceptionNotFound()
    }
}
