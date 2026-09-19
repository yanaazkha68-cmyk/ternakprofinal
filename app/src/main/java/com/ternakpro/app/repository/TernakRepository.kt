package com.ternakpro.app.repository

import androidx.room.withTransaction
import com.ternakpro.app.database.AppDatabase
import com.ternakpro.app.database.BiayaOperasional
import com.ternakpro.app.database.BatchTernak
import com.ternakpro.app.database.Kandang
import com.ternakpro.app.database.Kerugian
import com.ternakpro.app.database.ObatVaksin
import com.ternakpro.app.database.Pakan
import com.ternakpro.app.database.PembayaranPiutang
import com.ternakpro.app.database.Penjualan
import com.ternakpro.app.database.PengaturanAplikasi
import com.ternakpro.app.database.Piutang
import com.ternakpro.app.database.ProduksiHarian
import com.ternakpro.app.database.TransaksiObat
import com.ternakpro.app.database.TransaksiPakan
import kotlinx.coroutines.flow.Flow

class TernakRepository(
    private val db: AppDatabase
) {

    // =========================
    // KANDANG
    // =========================

    fun observeKandang(): Flow<List<Kandang>> =
        db.kandangDao().observeAll()

    suspend fun getKandang(): List<Kandang> =
        db.kandangDao().getAll()

    suspend fun addKandang(item: Kandang): Long =
        db.kandangDao().insert(item)

    suspend fun deleteKandang(item: Kandang) =
        db.kandangDao().delete(item)

    // =========================
    // BATCH TERNAK
    // =========================

    fun observeBatch(): Flow<List<BatchTernak>> =
        db.batchTernakDao().observeAll()

    suspend fun getBatch(): List<BatchTernak> =
        db.batchTernakDao().getAll()

    suspend fun addBatch(item: BatchTernak): Long =
        db.batchTernakDao().insert(item)

    suspend fun deleteBatch(item: BatchTernak) =
        db.batchTernakDao().delete(item)

    // =========================
    // PRODUKSI
    // =========================

    fun observeProduksi(): Flow<List<ProduksiHarian>> =
        db.produksiHarianDao().observeAll()

    suspend fun getProduksi(): List<ProduksiHarian> =
        db.produksiHarianDao().getAll()

    suspend fun addProduksi(item: ProduksiHarian): Long =
        db.produksiHarianDao().insert(item)

    suspend fun deleteProduksi(item: ProduksiHarian) =
        db.produksiHarianDao().delete(item)

    suspend fun totalTelurHariIni(tanggal: String): Int =
        db.produksiHarianDao().totalTelurTanggal(tanggal)

    suspend fun totalTelurBulan(bulan: String): Int =
        db.produksiHarianDao().totalTelurBulan(bulan)

    suspend fun totalPakanBulan(bulan: String): Double =
        db.produksiHarianDao().totalPakanBulan(bulan)

    suspend fun totalBeratTelurBulan(bulan: String): Double =
        db.produksiHarianDao().totalBeratTelurBulan(bulan)

    // =========================
    // PAKAN
    // =========================

    fun observePakan(): Flow<List<Pakan>> =
        db.pakanDao().observeAll()

    suspend fun getPakan(): List<Pakan> =
        db.pakanDao().getAll()

    suspend fun addPakan(item: Pakan): Long =
        db.pakanDao().insert(item)

    suspend fun deletePakan(item: Pakan) =
        db.pakanDao().delete(item)

    fun observeTransaksiPakan(): Flow<List<TransaksiPakan>> =
        db.transaksiPakanDao().observeAll()

    suspend fun getTransaksiPakan(): List<TransaksiPakan> =
        db.transaksiPakanDao().getAll()

    suspend fun addTransaksiPakan(
        item: TransaksiPakan
    ): Result<Long> = runCatching {

        db.withTransaction {

            val pakan = db.pakanDao().getById(item.pakanId)
                ?: error("Pakan tidak ditemukan")

            val stokBaru = when (item.jenisTransaksi.uppercase()) {
                "MASUK" -> pakan.stokSaatIni + item.jumlahKg

                "PAKAI",
                "RUSAK",
                "HILANG" -> {
                    val result = pakan.stokSaatIni - item.jumlahKg

                    require(result >= 0) {
                        "Stok pakan tidak mencukupi"
                    }

                    result
                }

                else -> error("Jenis transaksi pakan tidak valid")
            }

            db.pakanDao().updateStok(
                id = pakan.id,
                stok = stokBaru
            )

            db.transaksiPakanDao().insert(item)
        }
    }

    // =========================
    // OBAT / VAKSIN
    // =========================

    fun observeObat(): Flow<List<ObatVaksin>> =
        db.obatVaksinDao().observeAll()

    suspend fun getObat(): List<ObatVaksin> =
        db.obatVaksinDao().getAll()

    suspend fun addObat(item: ObatVaksin): Long =
        db.obatVaksinDao().insert(item)

    suspend fun deleteObat(item: ObatVaksin) =
        db.obatVaksinDao().delete(item)

    fun observeTransaksiObat(): Flow<List<TransaksiObat>> =
        db.transaksiObatDao().observeAll()

    suspend fun getTransaksiObat(): List<TransaksiObat> =
        db.transaksiObatDao().getAll()

    suspend fun addTransaksiObat(
        item: TransaksiObat
    ): Result<Long> = runCatching {

        db.withTransaction {

            val obat = db.obatVaksinDao().getById(item.obatId)
                ?: error("Obat/vaksin tidak ditemukan")

            val stokBaru = obat.stok - item.jumlah

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

    // =========================
    // PENJUALAN
    // =========================

    fun observePenjualan(): Flow<List<Penjualan>> =
        db.penjualanDao().observeAll()

    suspend fun getPenjualan(): List<Penjualan> =
        db.penjualanDao().getAll()

    fun observeTotalPenjualan(): Flow<Long> =
        db.penjualanDao().observeTotalPenjualan()

    suspend fun addPenjualan(item: Penjualan): Long =
        db.penjualanDao().insert(item)

    suspend fun deletePenjualan(item: Penjualan) =
        db.penjualanDao().delete(item)

    // =========================
    // PIUTANG
    // =========================

    fun observePiutang(): Flow<List<Piutang>> =
        db.piutangDao().observeAll()

    suspend fun getPiutang(): List<Piutang> =
        db.piutangDao().getAll()

    fun observeTotalPiutang(): Flow<Long> =
        db.piutangDao().observeTotalPiutang()

    suspend fun addPiutang(item: Piutang): Long =
        db.piutangDao().insert(item)

    suspend fun deletePiutang(item: Piutang) =
        db.piutangDao().delete(item)

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

            db.pembayaranPiutangDao().insert(pembayaran)

            db.piutangDao().updatePembayaran(
                id = piutang.id,
                sisa = sisaBaru,
                status = statusBaru
            )
        }
    }

    // =========================
    // BIAYA OPERASIONAL
    // =========================

    fun observeBiaya(): Flow<List<BiayaOperasional>> =
        db.biayaOperasionalDao().observeAll()

    suspend fun getBiaya(): List<BiayaOperasional> =
        db.biayaOperasionalDao().getAll()

    fun observeTotalBiaya(): Flow<Long> =
        db.biayaOperasionalDao().observeTotalBiaya()

    suspend fun addBiaya(item: BiayaOperasional): Long =
        db.biayaOperasionalDao().insert(item)

    suspend fun deleteBiaya(item: BiayaOperasional) =
        db.biayaOperasionalDao().delete(item)

    // =========================
    // KERUGIAN
    // =========================

    fun observeKerugian(): Flow<List<Kerugian>> =
        db.kerugianDao().observeAll()

    suspend fun getKerugian(): List<Kerugian> =
        db.kerugianDao().getAll()

    fun observeTotalKerugian(): Flow<Long> =
        db.kerugianDao().observeTotalKerugian()

    suspend fun addKerugian(item: Kerugian): Long =
        db.kerugianDao().insert(item)

    suspend fun deleteKerugian(item: Kerugian) =
        db.kerugianDao().delete(item)

    // =========================
    // PENGATURAN
    // =========================

    fun observePengaturan(): Flow<PengaturanAplikasi?> =
        db.pengaturanAplikasiDao().observe()

    suspend fun getPengaturan(): PengaturanAplikasi? =
        db.pengaturanAplikasiDao().get()

    suspend fun savePengaturan(
        item: PengaturanAplikasi
    ) = db.pengaturanAplikasiDao().save(item)

    // =========================
    // HAPUS DATA
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
}
