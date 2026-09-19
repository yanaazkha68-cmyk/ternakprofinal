package com.ternakpro.app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface KandangDao {

    @Query("SELECT * FROM kandang ORDER BY namaKandang")
    fun observeAll(): Flow<List<Kandang>>

    @Query("SELECT * FROM kandang ORDER BY namaKandang")
    suspend fun getAll(): List<Kandang>

    @Query("SELECT * FROM kandang WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Kandang?

    @Insert
    suspend fun insert(item: Kandang): Long

    @Delete
    suspend fun delete(item: Kandang)

    @Query("DELETE FROM kandang")
    suspend fun deleteAll()
}

@Dao
interface BatchTernakDao {

    @Query("SELECT * FROM batch_ternak ORDER BY tglMasuk DESC")
    fun observeAll(): Flow<List<BatchTernak>>

    @Query("SELECT * FROM batch_ternak ORDER BY tglMasuk DESC")
    suspend fun getAll(): List<BatchTernak>

    @Query("SELECT * FROM batch_ternak WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): BatchTernak?

    @Insert
    suspend fun insert(item: BatchTernak): Long

    @Delete
    suspend fun delete(item: BatchTernak)

    @Query("DELETE FROM batch_ternak")
    suspend fun deleteAll()
}

@Dao
interface ProduksiHarianDao {

    @Query("SELECT * FROM produksi_harian ORDER BY tanggal DESC")
    fun observeAll(): Flow<List<ProduksiHarian>>

    @Query("SELECT * FROM produksi_harian ORDER BY tanggal DESC")
    suspend fun getAll(): List<ProduksiHarian>

    @Insert
    suspend fun insert(item: ProduksiHarian): Long

    @Delete
    suspend fun delete(item: ProduksiHarian)

    @Query("DELETE FROM produksi_harian")
    suspend fun deleteAll()

    @Query("""
        SELECT COALESCE(SUM(
            telurBesar + telurSedang + telurKecil +
            telurTetel + telurRetak + telurBusuk
        ), 0)
        FROM produksi_harian
        WHERE tanggal = :tanggal
    """)
    suspend fun totalTelurTanggal(tanggal: String): Int

    @Query("""
        SELECT COALESCE(SUM(
            telurBesar + telurSedang + telurKecil +
            telurTetel + telurRetak + telurBusuk
        ), 0)
        FROM produksi_harian
        WHERE tanggal LIKE :bulan || '%'
    """)
    suspend fun totalTelurBulan(bulan: String): Int

    @Query("""
        SELECT COALESCE(SUM(pakanKg), 0)
        FROM produksi_harian
        WHERE tanggal LIKE :bulan || '%'
    """)
    suspend fun totalPakanBulan(bulan: String): Double

    @Query("""
        SELECT COALESCE(SUM(totalBeratKg), 0)
        FROM produksi_harian
        WHERE tanggal LIKE :bulan || '%'
    """)
    suspend fun totalBeratTelurBulan(bulan: String): Double
}

@Dao
interface PakanDao {

    @Query("SELECT * FROM pakan ORDER BY namaPakan")
    fun observeAll(): Flow<List<Pakan>>

    @Query("SELECT * FROM pakan ORDER BY namaPakan")
    suspend fun getAll(): List<Pakan>

    @Query("SELECT * FROM pakan WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Pakan?

    @Insert
    suspend fun insert(item: Pakan): Long

    @Query("""
        UPDATE pakan
        SET stokSaatIni = :stok
        WHERE id = :id
    """)
    suspend fun updateStok(id: Long, stok: Double)

    @Delete
    suspend fun delete(item: Pakan)

    @Query("DELETE FROM pakan")
    suspend fun deleteAll()
}

@Dao
interface TransaksiPakanDao {

    @Query("SELECT * FROM transaksi_pakan ORDER BY tanggal DESC, id DESC")
    fun observeAll(): Flow<List<TransaksiPakan>>

    @Query("SELECT * FROM transaksi_pakan ORDER BY tanggal DESC, id DESC")
    suspend fun getAll(): List<TransaksiPakan>

    @Insert
    suspend fun insert(item: TransaksiPakan): Long

    @Delete
    suspend fun delete(item: TransaksiPakan)

    @Query("DELETE FROM transaksi_pakan")
    suspend fun deleteAll()
}

@Dao
interface ObatVaksinDao {

    @Query("SELECT * FROM obat_vaksin ORDER BY namaObat")
    fun observeAll(): Flow<List<ObatVaksin>>

    @Query("SELECT * FROM obat_vaksin ORDER BY namaObat")
    suspend fun getAll(): List<ObatVaksin>

    @Query("SELECT * FROM obat_vaksin WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ObatVaksin?

    @Insert
    suspend fun insert(item: ObatVaksin): Long

    @Query("""
        UPDATE obat_vaksin
        SET stok = :stok
        WHERE id = :id
    """)
    suspend fun updateStok(id: Long, stok: Double)

    @Delete
    suspend fun delete(item: ObatVaksin)

    @Query("DELETE FROM obat_vaksin")
    suspend fun deleteAll()
}

@Dao
interface TransaksiObatDao {

    @Query("SELECT * FROM transaksi_obat ORDER BY tanggal DESC, id DESC")
    fun observeAll(): Flow<List<TransaksiObat>>

    @Query("SELECT * FROM transaksi_obat ORDER BY tanggal DESC, id DESC")
    suspend fun getAll(): List<TransaksiObat>

    @Insert
    suspend fun insert(item: TransaksiObat): Long

    @Delete
    suspend fun delete(item: TransaksiObat)

    @Query("DELETE FROM transaksi_obat")
    suspend fun deleteAll()
}

@Dao
interface PenjualanDao {

    @Query("SELECT * FROM penjualan ORDER BY tanggal DESC, id DESC")
    fun observeAll(): Flow<List<Penjualan>>

    @Query("SELECT * FROM penjualan ORDER BY tanggal DESC, id DESC")
    suspend fun getAll(): List<Penjualan>

    @Query("SELECT COALESCE(SUM(total), 0) FROM penjualan")
    fun observeTotalPenjualan(): Flow<Long>

    @Insert
    suspend fun insert(item: Penjualan): Long

    @Delete
    suspend fun delete(item: Penjualan)

    @Query("DELETE FROM penjualan")
    suspend fun deleteAll()
}

@Dao
interface PiutangDao {

    @Query("SELECT * FROM piutang ORDER BY jatuhTempo ASC, id DESC")
    fun observeAll(): Flow<List<Piutang>>

    @Query("SELECT * FROM piutang ORDER BY jatuhTempo ASC, id DESC")
    suspend fun getAll(): List<Piutang>

    @Query("SELECT * FROM piutang WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Piutang?

    @Query("SELECT COALESCE(SUM(sisaPiutang), 0) FROM piutang")
    fun observeTotalPiutang(): Flow<Long>

    @Query("""
        UPDATE piutang
        SET sisaPiutang = :sisa,
            status = :status
        WHERE id = :id
    """)
    suspend fun updatePembayaran(
        id: Long,
        sisa: Long,
        status: String
    )

    @Insert
    suspend fun insert(item: Piutang): Long

    @Delete
    suspend fun delete(item: Piutang)

    @Query("DELETE FROM piutang")
    suspend fun deleteAll()
}

@Dao
interface PembayaranPiutangDao {

    @Query("SELECT * FROM pembayaran_piutang ORDER BY tanggal DESC, id DESC")
    fun observeAll(): Flow<List<PembayaranPiutang>>

    @Query("SELECT * FROM pembayaran_piutang ORDER BY tanggal DESC, id DESC")
    suspend fun getAll(): List<PembayaranPiutang>

    @Insert
    suspend fun insert(item: PembayaranPiutang): Long

    @Query("DELETE FROM pembayaran_piutang")
    suspend fun deleteAll()
}

@Dao
interface BiayaOperasionalDao {

    @Query("SELECT * FROM biaya_operasional ORDER BY tanggal DESC, id DESC")
    fun observeAll(): Flow<List<BiayaOperasional>>

    @Query("SELECT * FROM biaya_operasional ORDER BY tanggal DESC, id DESC")
    suspend fun getAll(): List<BiayaOperasional>

    @Query("SELECT COALESCE(SUM(nominal), 0) FROM biaya_operasional")
    fun observeTotalBiaya(): Flow<Long>

    @Insert
    suspend fun insert(item: BiayaOperasional): Long

    @Delete
    suspend fun delete(item: BiayaOperasional)

    @Query("DELETE FROM biaya_operasional")
    suspend fun deleteAll()
}

@Dao
interface KerugianDao {

    @Query("SELECT * FROM kerugian ORDER BY tanggal DESC, id DESC")
    fun observeAll(): Flow<List<Kerugian>>

    @Query("SELECT * FROM kerugian ORDER BY tanggal DESC, id DESC")
    suspend fun getAll(): List<Kerugian>

    @Query("SELECT COALESCE(SUM(nilaiKerugian), 0) FROM kerugian")
    fun observeTotalKerugian(): Flow<Long>

    @Insert
    suspend fun insert(item: Kerugian): Long

    @Delete
    suspend fun delete(item: Kerugian)

    @Query("DELETE FROM kerugian")
    suspend fun deleteAll()
}

@Dao
interface PengaturanAplikasiDao {

    @Query("SELECT * FROM pengaturan_aplikasi WHERE id = 1 LIMIT 1")
    fun observe(): Flow<PengaturanAplikasi?>

    @Query("SELECT * FROM pengaturan_aplikasi WHERE id = 1 LIMIT 1")
    suspend fun get(): PengaturanAplikasi?

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun save(item: PengaturanAplikasi)
}
