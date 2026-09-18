package com.ternakpro.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ternakpro.app.ui.TernakProApp
import com.ternakpro.app.ui.theme.TernakProTheme
import com.ternakpro.app.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TernakProTheme {
                val vm: AppViewModel = viewModel()
                TernakProApp(vm)
            }
        }
    }
}
