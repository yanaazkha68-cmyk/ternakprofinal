# TERNAKPRO — Manajemen Ayam Petelur

Project Android native Kotlin + Jetpack Compose + Material 3 + Room untuk manajemen peternakan ayam petelur secara offline-first.

## Identitas
- App: TERNAKPRO
- Package: `com.ternakpro.app`
- Version: `1.0.0`
- Min SDK: 24
- Target/Compile SDK: 37
- Database: Room/SQLite
- UI: Jetpack Compose Material 3
- State: ViewModel + Coroutines/Flow
- Penyimpanan: lokal/offline

## Fitur
- Dashboard ayam hidup/mati, telur harian/bulanan, pakan, penjualan, piutang, biaya, kerugian, laba bersih.
- HDP dan FCR Telur.
- Grafik produksi 14 pencatatan terakhir.
- 11 menu: Dashboard, Kandang, Batch Ternak, Produksi, Pakan, Obat/Vaksin, Penjualan, Piutang, Biaya Operasional, Kerugian, Pengaturan.
- CRUD data utama.
- Transaksi pakan otomatis mengubah stok dan mencegah stok minus.
- Pemakaian obat/vaksin otomatis mengurangi stok dan mencegah stok minus.
- Penjualan non-lunas otomatis membentuk piutang.
- Pembayaran piutang otomatis mengurangi sisa dan mengubah status menjadi LUNAS/SEBAGIAN.
- Peringatan stok pakan rendah dan obat/vaksin kedaluwarsa.
- Backup/restore JSON lengkap.
- Export CSV dan PDF memakai API Android native.
- Data contoh realistis.
- Hapus seluruh data dengan konfirmasi.
- Tidak memerlukan permission storage legacy karena backup/export memakai Storage Access Framework.

## Membuka di Android Studio
1. Ekstrak ZIP.
2. Buka folder `TERNAKPRO` di Android Studio.
3. Tunggu Gradle Sync selesai.
4. Pastikan Android SDK 37 terpasang.
5. Jalankan perangkat/emulator Android API 24+.
6. Klik Run.

## Build APK
```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

> Catatan: arsip ini berisi konfigurasi Gradle Wrapper (`gradle/wrapper/gradle-wrapper.properties`) dan skrip `gradlew`, tetapi binary `gradle-wrapper.jar` tidak disertakan oleh generator ini. Android Studio dapat melakukan Gradle Sync dan menyiapkan wrapper; jika ingin menggunakan terminal, jalankan perintah Gradle dari Android Studio/instalasi Gradle 9.7 Anda.

APK debug:
`app/build/outputs/apk/debug/app-debug.apk`

APK release:
`app/build/outputs/apk/release/app-release-unsigned.apk`

### Signing release
Build release default menghasilkan APK unsigned. Untuk APK rilis Play Store/instalasi produksi, gunakan keystore Anda sendiri dan konfigurasi `signingConfigs` pada `app/build.gradle.kts`. Jangan pernah memasukkan password/keystore pribadi ke repository publik.

## Catatan versi
Versi dependency mengikuti dokumentasi Android/Kotlin yang tersedia pada September 2026: Kotlin 2.4.20, Compose BOM 2026.09.00, Room 2.8.5, KSP 2.3.12, dan AGP 9.4.0.

## Struktur utama
```
TERNAKPRO/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/ternakpro/app/
│           ├── MainActivity.kt
│           ├── TernakProApplication.kt
│           ├── database/
│           ├── repository/
│           ├── utils/
│           ├── viewmodel/
│           └── ui/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradle/wrapper/gradle-wrapper.properties
```

## Catatan penting
Project ini sengaja menggunakan Room 2.x karena merupakan jalur Android Room stabil dan langsung cocok untuk aplikasi Android native. Semua backup/restore dan laporan dilakukan melalui file picker Android sehingga tidak membutuhkan izin penyimpanan lama.
