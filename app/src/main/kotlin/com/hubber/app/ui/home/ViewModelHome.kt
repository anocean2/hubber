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
 
package com.hubber.app.ui.home

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.hubber.app.base.ResponseResult
import com.hubber.app.data.AppDatabase
import com.hubber.app.data.followers.impl.RepositoryFollower
import com.hubber.app.data.followers.paging.PageSourceFollower
import com.hubber.app.data.repos.impl.RepositoryRepo
import com.hubber.app.data.repos.paging.RemoteMediatorRepo
import com.hubber.app.data.user.impl.RepositoryUser
import com.hubber.app.extension.success
import com.hubber.app.models.Follower
import com.hubber.app.models.ModelRepo
import com.hubber.app.models.User
import com.hubber.app.utils.ConstantsPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class ViewModelHome @Inject constructor(
    private val db: AppDatabase,
    private val preferences: com.hubber.app.base.ApplicationPreferences,
    repositoryRepo: RepositoryRepo,
    repositoryFollower: RepositoryFollower,
    private val repositoryUser: RepositoryUser
) : ViewModel() {

    // change tabs
    private val _selectedTab: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // first query to get user
    private val _loadingUser: MutableStateFlow<ResponseResult<User>?> = MutableStateFlow(null)
    val loadingUser = _loadingUser.asStateFlow()

    // Example of use RemoteMediatorRepo
    @ExperimentalPagingApi
    val repos: Flow<PagingData<ModelRepo>> = Pager(
        config = PagingConfig(pageSize = ConstantsPaging.PER_PAGE),
        remoteMediator = RemoteMediatorRepo(db, preferences, repositoryRepo)
    ) {
        db.repo().pagingSource()
    }.flow

    // Example of use PagingSource
    val followers: Flow<PagingData<Follower>> = Pager(PagingConfig(pageSize = ConstantsPaging.PER_PAGE)) {
        PageSourceFollower(repositoryFollower)
    }.flow.cachedIn(viewModelScope)

    init {
        repeatLoadingUser()
    }

    fun repeatLoadingUser() {
        repositoryUser.loadingUser()
            .onEach {
                it.success { preferences.modelUser = it }
                _loadingUser.value = it
            }
            .launchIn(viewModelScope)
    }

    fun findByIdRepo(id: Long): Flow<ModelRepo?> {
        return db.repo().getModel(id).distinctUntilChanged()
    }

    @MainThread
    fun selectTab(@StringRes tab: Int) {
        _selectedTab.value = tab
    }
}
