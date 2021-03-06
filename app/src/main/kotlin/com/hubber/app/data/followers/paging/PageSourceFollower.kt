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
 
package com.hubber.app.data.followers.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hubber.app.data.followers.impl.RepositoryFollower
import com.hubber.app.extension.pagingSucceeded
import com.hubber.app.models.Follower

class PageSourceFollower(
    private val repository: RepositoryFollower
) : PagingSource<Int, Follower>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Follower> {
        val page = params.key ?: 1
        return repository.getModels(page).pagingSucceeded { data ->
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page.plus(1)
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Follower>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}