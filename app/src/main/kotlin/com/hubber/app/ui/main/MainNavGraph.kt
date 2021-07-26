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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.hubber.app.ui.home.DetailsRepo
import com.hubber.app.ui.home.StartApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@ExperimentalTime
@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    val actions = remember(navController) {
        MainActions(navController)
    }
    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Home.route) {
            composable(NavScreen.Home.route) {
                StartApp(
                    viewModel = hiltViewModel(),
                    navigateToDetailsRepo = actions.navigateToDetailsRepo,
                )
            }
            composable(
                route = NavScreen.DetailsRepo.routeWithArgument,
                arguments = listOf(navArgument(NavScreen.DetailsRepo.argument0) { type = NavType.LongType })
            ) { backStackEntry ->
                backStackEntry.arguments?.let {
                    DetailsRepo(
                        it.getLong(NavScreen.DetailsRepo.argument0),
                        viewModel = hiltViewModel(),
                        upPress = actions.upPress
                    )
                }
            }
        }
    }
}


