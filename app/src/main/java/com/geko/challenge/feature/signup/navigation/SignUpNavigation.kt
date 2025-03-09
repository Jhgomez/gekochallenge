/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geko.challenge.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.geko.challenge.feature.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable data object SignUpRoute // route to ForYou screen

fun NavController.navigateToSignUp(navOptions: NavOptionsBuilder.() -> Unit = {}) = navigate(route = SignUpRoute, navOptions)

fun NavGraphBuilder.signUpScreen(onShowSnackbar: suspend (String) -> Unit) {
    composable<SignUpRoute> {
        SignUpScreen(onShowSnackbar = onShowSnackbar)
    }
}
