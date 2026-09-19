package com.ternakpro.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ternakpro.app.utils.formatAngka
import com.ternakpro.app.utils.formatRupiah
import com.ternakpro.app.viewmodel.AppViewModel

@Composable
fun DashboardScreen(
    viewModel: AppViewModel
) {
    val dashboard by viewModel.dashboard.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Column {
                Text(
                    text = "TERNAKPRO",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Dashboard Peternakan",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Ayam Hidup",
                    value = formatAngka(dashboard.ayamHidup)
                )

                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Ayam Mati",
                    value = formatAngka(dashboard.ayamMati)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Telur Hari Ini",
                    value = formatAngka(dashboard.produksiTelurHariIni)
                )

                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Telur Bulan Ini",
                    value = formatAngka(dashboard.produksiTelurBulanIni)
                )
            }
        }

        item {
            DashboardCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Pakan Terpakai",
                value = "${formatAngka(dashboard.pakanTerpakai)} kg"
            )
        }

        item {
            Text(
                text = "Keuangan",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            DashboardCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Total Penjualan",
                value = formatRupiah(dashboard.totalPenjualan)
            )
        }

        item {
            DashboardCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Total Piutang",
                value = formatRupiah(dashboard.totalPiutang)
            )
        }

        item {
            DashboardCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Total Biaya",
                value = formatRupiah(dashboard.totalBiaya)
            )
        }

        item {
            DashboardCard(
                modifier = Modifier.fillMaxWidth(),
                title = "Total Kerugian",
                value = formatRupiah(dashboard.totalKerugian)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Laba Bersih",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = formatRupiah(dashboard.labaBersih),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }

        item {
            Text(
                text = "Menu Utama",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(
            listOf(
                "🏠 Dashboard",
                "🏠 Kandang",
                "🐔 Batch Ternak",
                "🥚 Produksi",
                "🌾 Pakan",
                "💊 Obat / Vaksin",
                "💰 Penjualan",
                "📋 Piutang",
                "💵 Biaya",
                "⚠️ Kerugian",
                "⚙️ Pengaturan"
            )
        ) { menu ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = menu,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(18.dp)
                )
            }
        }
    }
}

@Composable
private fun DashboardCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
