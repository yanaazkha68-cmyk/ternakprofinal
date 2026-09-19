package com.ternakpro.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ternakpro.app.TernakProApplication
import com.ternakpro.app.ui.screens.DashboardScreen
import com.ternakpro.app.viewmodel.AppViewModel

@Composable
fun TernakProApp() {
    val context = LocalContext.current
    val application = context.applicationContext as TernakProApplication

    val viewModel = remember {
        AppViewModel(application.repository)
    }

    DashboardScreen(
        viewModel = viewModel
    )
}
