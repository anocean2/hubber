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
 
package com.hubber.app.data.followers.impl

import com.hubber.app.base.ResponseResult
import com.hubber.app.base.executeWithResponse
import com.hubber.app.data.followers.ServiceFollower
import com.hubber.app.extension.toModelFollowers
import com.hubber.app.models.Follower
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryFollower @Inject constructor(
    private val preferences: com.hubber.app.base.ApplicationPreferences,
    private val service: ServiceFollower
) {
    suspend fun getModels(page: Int): ResponseResult<List<Follower>> {
        return withContext(Dispatchers.IO) {
            delay(1000) // slow internet
            executeWithResponse {
                service.listFollowers(preferences.followersUrl, page)
                    .body()
                    ?.toModelFollowers()
                    ?: emptyList()
            }
        }
    }
}