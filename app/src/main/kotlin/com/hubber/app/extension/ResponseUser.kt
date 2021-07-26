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
 
package com.hubber.app.extension

import com.hubber.app.data.user.impl.ResponseUser
import com.hubber.app.models.User

fun ResponseUser.toModelUser(): User {
    return User(
        id = this.id,
        login = this.login,
        avatarUrl = this.avatar_url,
        followersUrl = this.followers_url,
        reposUrl = this.repos_url,
        name = this.name,
        bio = this.bio ?: "",
        createdAt = this.created_at,
    )
}