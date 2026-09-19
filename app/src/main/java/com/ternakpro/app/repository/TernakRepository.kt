package com.ternakpro.app.repository

import androidx.room.withTransaction
import com.ternakpro.app.database.*
import kotlinx.coroutines.flow.Flow

class TernakRepository(
    private val db: AppDatabase
) {

    // =========================
    // KANDANG
    // =========================

    val kandang: Flow<List<Kandang>>
        get() = db.kandangDao().observeAll()

    suspend fun getAllKandang(): List<Kandang> =
        db.kandangDao().getAll()

    suspend fun addKandang(item: Kandang): Long =
        db.kandangDao().insert(item)

    suspend fun deleteKandang(item: Kandang) =
        db.kandangDao().delete(item)

    // =========================
    // BATCH TERNAK
    // =========================

    val batchTernak: Flow<List<BatchTernak>>
        get() = db.batchTernakDao().observeAll()

    suspend fun getAllBatchTernak(): List<BatchTernak> =
        db.batchTernakDao().getAll()

    suspend fun addBatchTernak(item: BatchTernak): Long =
        db.batchTernakDao().insert(item)

    suspend fun deleteBatchTernak(item: BatchTernak) =
        db.batchTernakDao().delete(item)

    // =========================
    // PRODUKSI
    // =========================

    val produksi: Flow<List<ProduksiHarian>>
        get() = db.produksiHarianDao().observeAll()

    suspend fun getAllProduksi(): List<ProduksiHarian> =
        db.produksiHarianDao().getAll()

    suspend fun addProduksi(item: ProduksiHarian): Long =
        db.produksiHarianDao().insert(item)

    suspend fun deleteProduksi(item: ProduksiHarian) =
        db.produksiHarianDao().delete(item)

    // =========================
    // PAKAN
    // =========================

    val pakan: Flow<List<Pakan>>
        get() = db.pakanDao().observeAll()

    suspend fun getAllPakan(): List<Pakan> =
        db.pakanDao().getAll()

    suspend fun addPakan(item: Pakan): Long =
        db.pakanDao().insert(item)

    suspend fun deletePakan(item: Pakan) =
        db.pakanDao().delete(item)

    val transaksiPakan: Flow<List<TransaksiPakan>>
        get() = db.transaksiPakanDao().observeAll()

    suspend fun getAllTransaksiPakan(): List<TransaksiPakan> =
        db.transaksiPakanDao().getAll()

    suspend fun addTransaksiPakan(
        item: TransaksiPakan
    ): Result<Long> = runCatching {

        db.withTransaction {

            val pakan = db.pakanDao().getById(item.pakanId)
                ?: error("Pakan tidak ditemukan")

            val stokBaru = when (item.jenisTransaksi.uppercase()) {

                "MASUK" ->
                    pakan.stokSaatIni + item.jumlahKg

                "PAKAI",
                "RUSAK",
                "HILANG" -> {

                    val hasil =
                        pakan.stokSaatIni - item.jumlahKg

                    require(hasil >= 0) {
                        "Stok pakan tidak mencukupi"
                    }

                    hasil
                }

                else ->
                    error("Jenis transaksi pakan tidak valid")
            }

            db.pakanDao().updateStok(
                id = pakan.id,
                stok = stokBaru
            )

            db.transaksiPakanDao().insert(item)
        }
    }

    suspend fun deleteTransaksiPakan(item: TransaksiPakan) =
        db.transaksiPakanDao().delete(item)

    // =========================
    // OBAT / VAKSIN
    // =========================

    val obatVaksin: Flow<List<ObatVaksin>>
        get() = db.obatVaksinDao().observeAll()

    suspend fun getAllObatVaksin(): List<ObatVaksin> =
        db.obatVaksinDao().getAll()

    suspend fun addObatVaksin(item: ObatVaksin): Long =
        db.obatVaksinDao().insert(item)

    suspend fun deleteObatVaksin(item: ObatVaksin) =
        db.obatVaksinDao().delete(item)

    val transaksiObat: Flow<List<TransaksiObat>>
        get() = db.transaksiObatDao().observeAll()

    suspend fun getAllTransaksiObat(): List<TransaksiObat> =
        db.transaksiObatDao().getAll()

    suspend fun addTransaksiObat(
        item: TransaksiObat
    ): Result<Long> = runCatching {

        db.withTransaction {

            val obat = db.obatVaksinDao().getById(item.obatId)
                ?: error("Obat/vaksin tidak ditemukan")

            val stokBaru =
                obat.stok - item.jumlah

            require(stokBaru >= 0) {
                "Stok obat/vaksin tidak mencukupi"
            }

            db.obatVaksinDao().updateStok(
                id = obat.id,
                stok = stokBaru
            )

            db.transaksiObatDao().insert(item)
        }
    }

    suspend fun deleteTransaksiObat(item: TransaksiObat) =
        db.transaksiObatDao().delete(item)

    // =========================
    // PENJUALAN
    // =========================

    val penjualan: Flow<List<Penjualan>>
        get() = db.penjualanDao().observeAll()

    suspend fun getAllPenjualan(): List<Penjualan> =
        db.penjualanDao().getAll()

    suspend fun addPenjualan(item: Penjualan): Long =
        db.penjualanDao().insert(item)

    suspend fun deletePenjualan(item: Penjualan) =
        db.penjualanDao().delete(item)

    // =========================
    // PIUTANG
    // =========================

    val piutang: Flow<List<Piutang>>
        get() = db.piutangDao().observeAll()

    suspend fun getAllPiutang(): List<Piutang> =
        db.piutangDao().getAll()

    suspend fun addPiutang(item: Piutang): Long =
        db.piutangDao().insert(item)

    suspend fun deletePiutang(item: Piutang) =
        db.piutangDao().delete(item)

    val pembayaranPiutang: Flow<List<PembayaranPiutang>>
        get() = db.pembayaranPiutangDao().observeAll()

    suspend fun getAllPembayaranPiutang(): List<PembayaranPiutang> =
        db.pembayaranPiutangDao().getAll()

    suspend fun addPembayaranPiutang(
        pembayaran: PembayaranPiutang
    ): Result<Unit> = runCatching {

        db.withTransaction {

            val piutang = db.piutangDao()
                .getById(pembayaran.piutangId)
                ?: error("Piutang tidak ditemukan")

            require(pembayaran.nominal > 0) {
                "Nominal pembayaran harus lebih dari 0"
            }

            require(pembayaran.nominal <= piutang.sisaPiutang) {
                "Pembayaran melebihi sisa piutang"
            }

            val sisaBaru =
                piutang.sisaPiutang - pembayaran.nominal

            val statusBaru =
                if (sisaBaru == 0L) {
                    "LUNAS"
                } else {
                    "SEBAGIAN"
                }

            db.pembayaranPiutangDao()
                .insert(pembayaran)

            db.piutangDao().updatePembayaran(
                id = piutang.id,
                sisa = sisaBaru,
                status = statusBaru
            )
        }
    }

    // =========================
    // BIAYA
    // =========================

    val biaya: Flow<List<BiayaOperasional>>
        get() = db.biayaOperasionalDao().observeAll()

    suspend fun getAllBiaya(): List<BiayaOperasional> =
        db.biayaOperasionalDao().getAll()

    suspend fun addBiaya(item: BiayaOperasional): Long =
        db.biayaOperasionalDao().insert(item)

    suspend fun deleteBiaya(item: BiayaOperasional) =
        db.biayaOperasionalDao().delete(item)

    // =========================
    // KERUGIAN
    // =========================

    val kerugian: Flow<List<Kerugian>>
        get() = db.kerugianDao().observeAll()

    suspend fun getAllKerugian(): List<Kerugian> =
        db.kerugianDao().getAll()

    suspend fun addKerugian(item: Kerugian): Long =
        db.kerugianDao().insert(item)

    suspend fun deleteKerugian(item: Kerugian) =
        db.kerugianDao().delete(item)

    // =========================
    // PENGATURAN
    // =========================

    val pengaturan: Flow<PengaturanAplikasi?>
        get() = db.pengaturanAplikasiDao().observe()

    suspend fun getPengaturan(): PengaturanAplikasi? =
        db.pengaturanAplikasiDao().get()

    suspend fun savePengaturan(
        item: PengaturanAplikasi
    ) = db.pengaturanAplikasiDao().save(item)

    // =========================
    // HAPUS SEMUA DATA
    // =========================

    suspend fun deleteAllData() {

        db.withTransaction {

            db.pembayaranPiutangDao().deleteAll()
            db.piutangDao().deleteAll()

            db.penjualanDao().deleteAll()

            db.transaksiObatDao().deleteAll()
            db.obatVaksinDao().deleteAll()

            db.transaksiPakanDao().deleteAll()
            db.pakanDao().deleteAll()

            db.produksiHarianDao().deleteAll()
            db.batchTernakDao().deleteAll()
            db.kandangDao().deleteAll()

            db.biayaOperasionalDao().deleteAll()
            db.kerugianDao().deleteAll()
        }
    }

    // =========================
    // DATA CONTOH
    // =========================

    suspend fun seedSampleData() {

        db.withTransaction {

            val kandangId = db.kandangDao().insert(
                Kandang(
                    namaKandang = "Kandang A",
                    lokasi = "Blok Utama",
                    kapasitas = 1000,
                    jumlahAyam = 850,
                    kondisi = "BAIK",
                    tglDibersihkan = "",
                    catatan = "Data contoh"
                )
            )

            val batchId = db.batchTernakDao().insert(
                BatchTernak(
                    namaBatch = "Batch 2026-01",
                    kandangId = kandangId,
                    tglMasuk = "2026-01-10",
                    jumlahAyam = 850,
                    umurAyam = 18,
                    breed = "Isa Brown",
                    asalAyam = "Supplier Lokal",
                    hargaPerEkor = 75000L,
                    status = "AKTIF"
                )
            )

            db.pakanDao().insert(
                Pakan(
                    namaPakan = "Pakan Layer",
                    jenis = "Layer",
                    satuan = "kg",
                    beratPerSak = 50.0,
                    stokAwal = 500.0,
                    stokSaatIni = 425.0,
                    hargaPerSak = 450000L,
                    supplier = "Supplier Pakan",
                    catatan = "Data contoh"
                )
            )

            db.obatVaksinDao().insert(
                ObatVaksin(
                    namaObat = "Vitamin Ayam",
                    jenis = "Vitamin",
                    satuan = "botol",
                    stok = 10,
                    hargaBeli = 35000L,
                    supplier = "Supplier Obat",
                    tglKedaluwarsa = "2027-01-01",
                    catatan = "Data contoh"
                )
            )

            val tanggal =
                java.text.SimpleDateFormat(
                    "yyyy-MM-dd",
                    java.util.Locale.getDefault()
                ).format(java.util.Date())

            db.produksiHarianDao().insert(
                ProduksiHarian(
                    tanggal = tanggal,
                    batchId = batchId,
                    kandangId = kandangId,
                    ayamHidup = 840,
                    ayamMati = 2,
                    ayamHilang = 1,
                    telurBesar = 420,
                    telurSedang = 250,
                    telurKecil = 80,
                    telurTetel = 15,
                    telurRetak = 5,
                    telurBusuk = 2,
                    totalBeratKg = 42.5,
                    pakanKg = 110.0,
                    obat = "",
                    catatan = "Data contoh"
                )
            )

            db.biayaOperasionalDao().insert(
                BiayaOperasional(
                    tanggal = tanggal,
                    kategori = "Pakan",
                    deskripsi = "Pembelian pakan",
                    nominal = 450000L,
                    penerima = "Supplier",
                    catatan = "Data contoh"
                )
            )

            db.kerugianDao().insert(
                Kerugian(
                    tanggal = tanggal,
                    jenisKerugian = "Ayam mati",
                    jumlah = 2.0,
                    satuan = "ekor",
                    penyebab = "Data contoh",
                    nilaiKerugian = 150000L,
                    catatan = ""
                )
            )
        }
    }
}
