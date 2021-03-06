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
 
package com.hubber.app.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.hubber.app.base.LocalBaseViewModel
import com.hubber.app.ui.theme.StackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModelMain by viewModels()

    private lateinit var navController: NavHostController

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CompositionLocalProvider(LocalBaseViewModel provides viewModel) {
                StackTheme {
                    MainNavGraph(navController)
                }
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.route) {
            NavScreen.Home.route -> if (viewModel.isShowSnackBar.value) finishAffinity() else viewModel.toggleSnackBar()
            else -> super.onBackPressed()
        }
    }
}