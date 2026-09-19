package com.ternakpro.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Kandang::class,
        BatchTernak::class,
        ProduksiHarian::class,
        Pakan::class,
        TransaksiPakan::class,
        ObatVaksin::class,
        TransaksiObat::class,
        Penjualan::class,
        Piutang::class,
        PembayaranPiutang::class,
        BiayaOperasional::class,
        Kerugian::class,
        PengaturanAplikasi::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kandangDao(): KandangDao

    abstract fun batchTernakDao(): BatchTernakDao

    abstract fun produksiHarianDao(): ProduksiHarianDao

    abstract fun pakanDao(): PakanDao

    abstract fun transaksiPakanDao(): TransaksiPakanDao

    abstract fun obatVaksinDao(): ObatVaksinDao

    abstract fun transaksiObatDao(): TransaksiObatDao

    abstract fun penjualanDao(): PenjualanDao

    abstract fun piutangDao(): PiutangDao

    abstract fun pembayaranPiutangDao(): PembayaranPiutangDao

    abstract fun biayaOperasionalDao(): BiayaOperasionalDao

    abstract fun kerugianDao(): KerugianDao

    abstract fun pengaturanAplikasiDao(): PengaturanAplikasiDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ternakpro.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
