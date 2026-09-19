package com.ternakpro.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ternakpro.app.database.*
import com.ternakpro.app.repository.TernakRepository
import kotlinx.coroutines.flow.*

data class DashboardState(
    val ayamHidup: Int = 0,
    val ayamMati: Int = 0,
    val produksiTelurHariIni: Int = 0,
    val produksiTelurBulanIni: Int = 0,
    val pakanTerpakai: Double = 0.0,
    val totalPenjualan: Double = 0.0,
    val totalPiutang: Double = 0.0,
    val totalBiaya: Double = 0.0,
    val totalKerugian: Double = 0.0
) {
    val labaBersih: Double
        get() = totalPenjualan - totalBiaya - totalKerugian
}

class AppViewModel(
    private val repository: TernakRepository
) : ViewModel() {

    val kandang = repository.kandang
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val batchTernak = repository.batchTernak
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val produksi = repository.produksi
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pakan = repository.pakan
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val transaksiPakan = repository.transaksiPakan
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val obatVaksin = repository.obatVaksin
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val transaksiObat = repository.transaksiObat
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val penjualan = repository.penjualan
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val piutang = repository.piutang
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pembayaranPiutang = repository.pembayaranPiutang
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val biaya = repository.biaya
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val kerugian = repository.kerugian
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pengaturan = repository.pengaturan
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PengaturanAplikasi()
        )

    val dashboard: StateFlow<DashboardState> = combine(
        produksi,
        penjualan,
        piutang,
        biaya,
        kerugian
    ) { produksiList, penjualanList, piutangList, biayaList, kerugianList ->

        val tanggalHariIni =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

        val bulanIni = tanggalHariIni.substring(0, 7)

        val produksiHariIni =
            produksiList.filter {
                it.tanggal == tanggalHariIni
            }

        val produksiBulanIni =
            produksiList.filter {
                it.tanggal.startsWith(bulanIni)
            }

        DashboardState(
            ayamHidup = produksiHariIni.sumOf {
                it.ayamHidup
            },

            ayamMati = produksiHariIni.sumOf {
                it.ayamMati
            },

            produksiTelurHariIni =
                produksiHariIni.sumOf {
                    it.totalTelur
                },

            produksiTelurBulanIni =
                produksiBulanIni.sumOf {
                    it.totalTelur
                },

            totalPenjualan =
                penjualanList.sumOf {
                    it.total.toDouble()
                },

            totalPiutang =
                piutangList.sumOf {
                    it.sisaPiutang.toDouble()
                },

            totalBiaya =
                biayaList.sumOf {
                    it.nominal.toDouble()
                },

            totalKerugian =
                kerugianList.sumOf {
                    it.nilaiKerugian.toDouble()
                }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DashboardState()
    )

    // =========================
    // KANDANG
    // =========================

    fun tambahKandang(item: Kandang) {
        viewModelScope.launch {
            repository.addKandang(item)
        }
    }

    fun hapusKandang(item: Kandang) {
        viewModelScope.launch {
            repository.deleteKandang(item)
        }
    }

    // =========================
    // BATCH
    // =========================

    fun tambahBatch(item: BatchTernak) {
        viewModelScope.launch {
            repository.addBatchTernak(item)
        }
    }

    fun hapusBatch(item: BatchTernak) {
        viewModelScope.launch {
            repository.deleteBatchTernak(item)
        }
    }

    // =========================
    // PRODUKSI
    // =========================

    fun tambahProduksi(item: ProduksiHarian) {
        viewModelScope.launch {
            repository.addProduksi(item)
        }
    }

    fun hapusProduksi(item: ProduksiHarian) {
        viewModelScope.launch {
            repository.deleteProduksi(item)
        }
    }

    // =========================
    // PAKAN
    // =========================

    fun tambahPakan(item: Pakan) {
        viewModelScope.launch {
            repository.addPakan(item)
        }
    }

    fun hapusPakan(item: Pakan) {
        viewModelScope.launch {
            repository.deletePakan(item)
        }
    }

    fun tambahTransaksiPakan(item: TransaksiPakan) {
        viewModelScope.launch {
            repository.addTransaksiPakan(item)
        }
    }

    fun hapusTransaksiPakan(item: TransaksiPakan) {
        viewModelScope.launch {
            repository.deleteTransaksiPakan(item)
        }
    }

    // =========================
    // OBAT / VAKSIN
    // =========================

    fun tambahObat(item: ObatVaksin) {
        viewModelScope.launch {
            repository.addObatVaksin(item)
        }
    }

    fun hapusObat(item: ObatVaksin) {
        viewModelScope.launch {
            repository.deleteObatVaksin(item)
        }
    }

    fun tambahTransaksiObat(item: TransaksiObat) {
        viewModelScope.launch {
            repository.addTransaksiObat(item)
        }
    }

    fun hapusTransaksiObat(item: TransaksiObat) {
        viewModelScope.launch {
            repository.deleteTransaksiObat(item)
        }
    }

    // =========================
    // PENJUALAN
    // =========================

    fun tambahPenjualan(item: Penjualan) {
        viewModelScope.launch {
            repository.addPenjualan(item)
        }
    }

    fun hapusPenjualan(item: Penjualan) {
        viewModelScope.launch {
            repository.deletePenjualan(item)
        }
    }

    // =========================
    // PIUTANG
    // =========================

    fun tambahPiutang(item: Piutang) {
        viewModelScope.launch {
            repository.addPiutang(item)
        }
    }

    fun hapusPiutang(item: Piutang) {
        viewModelScope.launch {
            repository.deletePiutang(item)
        }
    }

    fun bayarPiutang(
        piutangId: Long,
        nominal: Double,
        tanggal: String,
        catatan: String = ""
    ) {
        viewModelScope.launch {
            repository.addPembayaranPiutang(
                PembayaranPiutang(
                    piutangId = piutangId,
                    tanggal = tanggal,
                    nominal = nominal.toLong(),
                    catatan = catatan
                )
            )
        }
    }

    // =========================
    // BIAYA
    // =========================

    fun tambahBiaya(item: BiayaOperasional) {
        viewModelScope.launch {
            repository.addBiaya(item)
        }
    }

    fun hapusBiaya(item: BiayaOperasional) {
        viewModelScope.launch {
            repository.deleteBiaya(item)
        }
    }

    // =========================
    // KERUGIAN
    // =========================

    fun tambahKerugian(item: Kerugian) {
        viewModelScope.launch {
            repository.addKerugian(item)
        }
    }

    fun hapusKerugian(item: Kerugian) {
        viewModelScope.launch {
            repository.deleteKerugian(item)
        }
    }

    // =========================
    // PENGATURAN
    // =========================

    fun simpanPengaturan(item: PengaturanAplikasi) {
        viewModelScope.launch {
            repository.savePengaturan(item)
        }
    }

    // =========================
    // DATA
    // =========================

    fun hapusSemuaData() {
        viewModelScope.launch {
            repository.deleteAllData()
        }
    }

    fun isiDataContoh() {
        viewModelScope.launch {
            repository.seedSampleData()
        }
    }

    companion object {

        fun factory(
            repository: TernakRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return AppViewModel(repository) as T
                }
            }
    }
}
