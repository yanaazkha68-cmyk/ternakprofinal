package com.ternakpro.app.utils

import android.content.Context
import android.net.Uri
import com.ternakpro.app.database.*
import com.ternakpro.app.repository.TernakRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

object BackupUtils {

    suspend fun exportBackup(
        context: Context,
        repository: TernakRepository,
        uri: Uri
    ) = withContext(Dispatchers.IO) {

        val root = JSONObject()

        root.put("app", "TERNAKPRO")
        root.put("version", 1)
        root.put("createdAt", System.currentTimeMillis())

        val data = JSONObject()

        data.put(
            "kandang",
            JSONArray(repository.getAllKandang().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("namaKandang", it.namaKandang)
                    put("lokasi", it.lokasi)
                    put("kapasitas", it.kapasitas)
                    put("jumlahAyam", it.jumlahAyam)
                    put("kondisi", it.kondisi)
                    put("tglDibersihkan", it.tglDibersihkan)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "batchTernak",
            JSONArray(repository.getAllBatchTernak().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("namaBatch", it.namaBatch)
                    put("kandangId", it.kandangId)
                    put("tglMasuk", it.tglMasuk)
                    put("jumlahAyam", it.jumlahAyam)
                    put("umurAyam", it.umurAyam)
                    put("breed", it.breed)
                    put("asalAyam", it.asalAyam)
                    put("hargaPerEkor", it.hargaPerEkor)
                    put("status", it.status)
                }
            })
        )

        data.put(
            "produksi",
            JSONArray(repository.getAllProduksi().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("tanggal", it.tanggal)
                    put("batchId", it.batchId)
                    put("kandangId", it.kandangId)
                    put("ayamHidup", it.ayamHidup)
                    put("ayamMati", it.ayamMati)
                    put("ayamHilang", it.ayamHilang)
                    put("telurBesar", it.telurBesar)
                    put("telurSedang", it.telurSedang)
                    put("telurKecil", it.telurKecil)
                    put("telurTetel", it.telurTetel)
                    put("telurRetak", it.telurRetak)
                    put("telurBusuk", it.telurBusuk)
                    put("totalBeratKg", it.totalBeratKg)
                    put("pakanKg", it.pakanKg)
                    put("obat", it.obat)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "pakan",
            JSONArray(repository.getAllPakan().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("namaPakan", it.namaPakan)
                    put("jenis", it.jenis)
                    put("satuan", it.satuan)
                    put("beratPerSak", it.beratPerSak)
                    put("stokAwal", it.stokAwal)
                    put("stokSaatIni", it.stokSaatIni)
                    put("hargaPerSak", it.hargaPerSak)
                    put("supplier", it.supplier)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "obatVaksin",
            JSONArray(repository.getAllObatVaksin().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("namaObat", it.namaObat)
                    put("jenis", it.jenis)
                    put("satuan", it.satuan)
                    put("stok", it.stok)
                    put("hargaBeli", it.hargaBeli)
                    put("supplier", it.supplier)
                    put("tglKedaluwarsa", it.tglKedaluwarsa)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "penjualan",
            JSONArray(repository.getAllPenjualan().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("nomorInvoice", it.nomorInvoice)
                    put("tanggal", it.tanggal)
                    put("pembeli", it.pembeli)
                    put("jenisPenjualan", it.jenisPenjualan)
                    put("detailJenis", it.detailJenis)
                    put("jumlah", it.jumlah)
                    put("satuan", it.satuan)
                    put("hargaSatuan", it.hargaSatuan)
                    put("total", it.total)
                    put("statusPembayaran", it.statusPembayaran)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "piutang",
            JSONArray(repository.getAllPiutang().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("nomorInvoice", it.nomorInvoice)
                    put("tanggal", it.tanggal)
                    put("pelanggan", it.pelanggan)
                    put("kontak", it.kontak)
                    put("barang", it.barang)
                    put("totalTagihan", it.totalTagihan)
                    put("sisaPiutang", it.sisaPiutang)
                    put("jatuhTempo", it.jatuhTempo)
                    put("status", it.status)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "biaya",
            JSONArray(repository.getAllBiaya().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("tanggal", it.tanggal)
                    put("kategori", it.kategori)
                    put("deskripsi", it.deskripsi)
                    put("nominal", it.nominal)
                    put("penerima", it.penerima)
                    put("catatan", it.catatan)
                }
            })
        )

        data.put(
            "kerugian",
            JSONArray(repository.getAllKerugian().map {
                JSONObject().apply {
                    put("id", it.id)
                    put("tanggal", it.tanggal)
                    put("jenisKerugian", it.jenisKerugian)
                    put("jumlah", it.jumlah)
                    put("satuan", it.satuan)
                    put("penyebab", it.penyebab)
                    put("nilaiKerugian", it.nilaiKerugian)
                    put("catatan", it.catatan)
                }
            })
        )

        root.put("data", data)

        context.contentResolver.openOutputStream(uri)?.use { output ->
            output.write(root.toString(2).toByteArray(Charsets.UTF_8))
        }
    }
}
