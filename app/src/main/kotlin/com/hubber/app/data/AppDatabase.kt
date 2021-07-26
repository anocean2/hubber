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
 
package com.hubber.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hubber.app.data.repos.DaoRepo
import com.hubber.app.models.ModelRepo

@Database(
    entities = [
        ModelRepo::class
    ], version = 5, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repo(): DaoRepo
}
