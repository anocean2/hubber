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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hubber.app.models.ModelRepo
import com.hubber.app.ui.common.CommonList
import com.hubber.app.ui.common.LanguageImage
import com.hubber.app.ui.theme.StackTheme
import com.hubber.app.utils.ConstantsDateFormat
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReposList(
    modifier: Modifier = Modifier,
    models: LazyPagingItems<ModelRepo>,
    navigateToDetailsRepo: (Long) -> Unit
) {
    CommonList(
        modifier = modifier,
        models = models,
        state = rememberSwipeRefreshState(models.loadState.refresh is LoadState.Loading)
    ) { model ->
        ItemRepo(model = model, navigateToRepoView = navigateToDetailsRepo)
    }
}

// Example of use Row/Column
@Composable
fun ItemRepo(
    model: ModelRepo,
    modifier: Modifier = Modifier,
    navigateToRepoView: (Long) -> Unit = { },
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = { navigateToRepoView(model.id) }
            ),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            LanguageImage(
                language = model.language,
                modifier = Modifier
                    .size(56.dp, 56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
            )
            Column {
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 4.dp)
                )
                Text(
                    text = "Created: ${
                        model.createdAt.toInstant()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .toJavaLocalDateTime()
                            .format(DateTimeFormatter.ofPattern(ConstantsDateFormat.DATE_TIME_LIST))
                    }",
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemRepoPreviewLight() {
    StackTheme(darkTheme = false) {
        ItemRepo(model = ModelRepo.mock())
    }
}

@Preview
@Composable
fun ItemRepoPreviewDark() {
    StackTheme(darkTheme = true) {
        ItemRepo(model = ModelRepo.mock())
    }
}