package com.ternakpro.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kandang")
data class Kandang(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaKandang: String,
    val lokasi: String,
    val kapasitas: Int,
    val jumlahAyam: Int,
    val kondisi: String,
    val tglDibersihkan: String,
    val catatan: String
)

@Entity(tableName = "batch_ternak")
data class BatchTernak(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaBatch: String,
    val kandangId: Long,
    val tglMasuk: String,
    val jumlahAyam: Int,
    val umurAyam: Int,
    val breed: String,
    val asalAyam: String,
    val hargaPerEkor: Long,
    val status: String
)

@Entity(tableName = "produksi_harian")
data class ProduksiHarian(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tanggal: String,
    val batchId: Long,
    val kandangId: Long,
    val ayamHidup: Int,
    val ayamMati: Int,
    val ayamHilang: Int,
    val telurBesar: Int,
    val telurSedang: Int,
    val telurKecil: Int,
    val telurTetel: Int,
    val telurRetak: Int,
    val telurBusuk: Int,
    val totalBeratKg: Double,
    val pakanKg: Double,
    val obat: String,
    val catatan: String
)

@Entity(tableName = "pakan")
data class Pakan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaPakan: String,
    val jenis: String,
    val satuan: String,
    val beratPerSak: Double,
    val stokAwal: Double,
    val stokSaatIni: Double,
    val hargaPerSak: Long,
    val supplier: String,
    val catatan: String
)

@Entity(tableName = "transaksi_pakan")
data class TransaksiPakan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pakanId: Long,
    val tanggal: String,
    val jenisTransaksi: String,
    val jumlahKg: Double,
    val catatan: String
)

@Entity(tableName = "obat_vaksin")
data class ObatVaksin(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val namaObat: String,
    val jenis: String,
    val satuan: String,
    val stok: Double,
    val hargaBeli: Long,
    val supplier: String,
    val tglKedaluwarsa: String,
    val catatan: String
)

@Entity(tableName = "transaksi_obat")
data class TransaksiObat(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val obatId: Long,
    val tanggal: String,
    val jumlah: Double,
    val catatan: String
)

@Entity(tableName = "penjualan")
data class Penjualan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nomorInvoice: String,
    val tanggal: String,
    val pembeli: String,
    val jenisPenjualan: String,
    val detailJenis: String,
    val jumlah: Double,
    val satuan: String,
    val hargaSatuan: Long,
    val total: Long,
    val statusPembayaran: String,
    val catatan: String
)

@Entity(tableName = "piutang")
data class Piutang(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nomorInvoice: String,
    val tanggal: String,
    val pelanggan: String,
    val kontak: String,
    val barang: String,
    val totalTagihan: Long,
    val sisaPiutang: Long,
    val jatuhTempo: String,
    val status: String,
    val catatan: String
)

@Entity(tableName = "pembayaran_piutang")
data class PembayaranPiutang(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val piutangId: Long,
    val tanggal: String,
    val nominal: Long,
    val catatan: String
)

@Entity(tableName = "biaya_operasional")
data class BiayaOperasional(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tanggal: String,
    val kategori: String,
    val deskripsi: String,
    val nominal: Long,
    val penerima: String,
    val catatan: String
)

@Entity(tableName = "kerugian")
data class Kerugian(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tanggal: String,
    val jenisKerugian: String,
    val jumlah: Double,
    val satuan: String,
    val penyebab: String,
    val nilaiKerugian: Long,
    val catatan: String
)

@Entity(tableName = "pengaturan_aplikasi")
data class PengaturanAplikasi(
    @PrimaryKey val id: Int = 1,
    val namaUsaha: String = "TERNAKPRO",
    val alamat: String = "",
    val nomorTelepon: String = "",
    val mataUang: String = "Rp"
)
