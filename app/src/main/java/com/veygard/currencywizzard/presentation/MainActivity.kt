@file:OptIn(ExperimentalMaterialApi::class)

package com.veygard.currencywizzard.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.veygard.currencywizzard.presentation.navigation.AppNavigation
import com.veygard.currencywizzard.presentation.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation(navController= rememberNavController())
            }
        }
    }
}