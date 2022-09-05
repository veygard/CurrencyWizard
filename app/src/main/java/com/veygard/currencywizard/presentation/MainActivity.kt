package com.veygard.currencywizard.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.veygard.currencywizard.presentation.navigation.AppNavigation
import com.veygard.currencywizard.presentation.ui.AppTheme
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