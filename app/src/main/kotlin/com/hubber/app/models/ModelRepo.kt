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
 
package com.hubber.app.models

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class ModelRepo(
    @PrimaryKey val id: Long,
    val name: String,
    val language: String,
    val createdAt: String,
    val page: Int,
) {
    companion object {
        fun mock() = ModelRepo(
            id = 1,
            name = "android-app",
            language = "Kotlin",
            createdAt = "2021-07-15T16:50:40Z",
            page = 0
        )
    }
}
