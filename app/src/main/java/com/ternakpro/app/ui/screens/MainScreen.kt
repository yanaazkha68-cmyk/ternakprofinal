package com.ternakpro.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TERNAKPRO",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Manajemen Ayam Petelur",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 6.dp)
        )

        Card(
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Data peternakan akan tampil di sini.",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
