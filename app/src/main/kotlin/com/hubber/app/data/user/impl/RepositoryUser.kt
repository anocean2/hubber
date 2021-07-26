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
 
package com.hubber.app.data.user.impl

import androidx.annotation.WorkerThread
import com.hubber.app.base.ExceptionNotFound
import com.hubber.app.base.ResponseResult
import com.hubber.app.data.user.ServiceUser
import com.hubber.app.extension.toModelUser
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryUser @Inject constructor(
    private val service: ServiceUser,
    private val gitHubUser: String
) {
    @WorkerThread
    fun loadingUser() = flow {
        try {
            service.getUser(gitHubUser).body()?.toModelUser()?.let { model ->
                emit(ResponseResult.Success(model))
            } ?: run {
                throw ExceptionNotFound()
            }
        } catch (ex: Exception) {
            emit(ResponseResult.Error(ex))
        }
    }
}
