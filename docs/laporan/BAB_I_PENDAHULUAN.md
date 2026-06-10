# BAB I — PENDAHULUAN

## 1. Deskripsi Program

### 1.1. Nama Program

**FutsalKu — Sistem Reservasi Lapangan Futsal**

Aplikasi desktop berbasis Java yang dirancang untuk mengelola proses reservasi lapangan futsal secara terkomputerisasi, dilengkapi dengan antarmuka grafis (GUI) menggunakan Java Swing dan penyimpanan data persisten melalui database MySQL.

---

### 1.2. Latar Belakang

Olahraga futsal merupakan salah satu aktivitas yang sangat populer di kalangan masyarakat Indonesia, mulai dari pelajar, mahasiswa, hingga pekerja. Tingginya animo terhadap olahraga ini berdampak pada meningkatnya jumlah penyedia jasa penyewaan lapangan futsal di berbagai daerah.

Namun, dalam praktiknya, banyak pengelola lapangan futsal — terutama yang berskala kecil dan menengah — masih mengandalkan metode manual dalam mengelola operasional bisnisnya. Pencatatan jadwal booking menggunakan buku tulis atau papan tulis, pendataan pelanggan yang tidak terorganisir, serta pencatatan transaksi pembayaran yang rawan kesalahan merupakan permasalahan umum yang kerap ditemui.

Beberapa permasalahan spesifik yang teridentifikasi antara lain:

1. **Tumpang tindih jadwal (*double booking*)** — Pencatatan manual berpotensi menyebabkan dua pelanggan memesan lapangan yang sama pada waktu yang bersamaan.
2. **Kesulitan dalam pelacakan riwayat** — Tanpa sistem yang terstruktur, pengelola kesulitan untuk melacak riwayat transaksi dan booking sebelumnya.
3. **Pengelolaan data pelanggan yang tidak efisien** — Data pelanggan dan member tidak tersimpan secara terpusat, sehingga menyulitkan pengelolaan program keanggotaan.
4. **Pencatatan keuangan yang tidak akurat** — Pencatatan pembayaran secara manual rentan terhadap kesalahan perhitungan dan kehilangan data.

Berdasarkan permasalahan tersebut, dibutuhkan sebuah sistem informasi yang mampu mengotomasi dan mengintegrasikan seluruh proses bisnis penyewaan lapangan futsal. Pengembangan aplikasi ini sekaligus menjadi sarana implementasi konsep-konsep Pemrograman Berorientasi Objek (OOP) yang telah dipelajari selama perkuliahan.

---

### 1.3. Fungsi dan Tujuan

#### 1.3.1. Fungsi Aplikasi

Aplikasi FutsalKu berfungsi sebagai sistem manajemen terpadu untuk penyewaan lapangan futsal dengan fungsi-fungsi utama sebagai berikut:

1. **Fungsi Manajemen Lapangan** — Mengelola data lapangan futsal yang tersedia, mencakup informasi tipe (Indoor/Outdoor), harga per jam, fasilitas, dan status ketersediaan.
2. **Fungsi Manajemen Pelanggan** — Menyimpan dan mengelola data pelanggan termasuk sistem keanggotaan (member) untuk pelanggan tetap.
3. **Fungsi Reservasi (Booking)** — Memfasilitasi proses pemesanan lapangan dengan pemilihan tanggal, jam, dan durasi penyewaan.
4. **Fungsi Pembayaran** — Mencatat dan memproses transaksi pembayaran dengan dukungan metode pembayaran tunai (Cash) dan transfer bank.
5. **Fungsi Pelaporan** — Menampilkan riwayat booking dan ringkasan statistik melalui dashboard untuk mendukung pengambilan keputusan.

#### 1.3.2. Tujuan Aplikasi

1. Mengembangkan aplikasi desktop berbasis Java yang mengimplementasikan konsep-konsep OOP secara komprehensif.
2. Menyediakan solusi digital untuk menggantikan pencatatan manual dalam pengelolaan penyewaan lapangan futsal.
3. Meningkatkan efisiensi operasional pengelola lapangan futsal melalui otomasi proses booking dan pembayaran.
4. Meminimalkan potensi kesalahan dalam pencatatan jadwal dan transaksi keuangan.
5. Memberikan kemudahan akses terhadap data historis booking dan pembayaran melalui fitur riwayat.

---

### 1.4. Fitur

Berikut adalah fitur-fitur utama yang diimplementasikan dalam aplikasi FutsalKu:

| No. | Fitur | Deskripsi |
|:---:|-------|-----------|
| 1 | **Dashboard** | Halaman utama yang menampilkan ringkasan statistik booking harian, jumlah lapangan tersedia, dan total pendapatan. |
| 2 | **Manajemen Lapangan** | Fitur CRUD (Create, Read, Update, Delete) untuk mengelola data lapangan futsal bertipe Indoor maupun Outdoor. |
| 3 | **Manajemen Pelanggan** | Fitur untuk mendaftarkan pelanggan baru, mengelola data pelanggan, dan mengatur status keanggotaan (member). |
| 4 | **Booking Jadwal** | Fitur reservasi lapangan dengan pemilihan tanggal, jam mulai, dan durasi. Sistem otomatis menghitung total biaya berdasarkan tipe lapangan. |
| 5 | **Pembayaran** | Pencatatan transaksi pembayaran yang mendukung dua metode: Cash (langsung lunas) dan Transfer (memerlukan konfirmasi). |
| 6 | **Riwayat Booking** | Menampilkan histori seluruh reservasi beserta status booking dan pembayaran untuk keperluan pelacakan dan pelaporan. |

---

### 1.5. Rancangan Struktur Program

#### 1.5.1. Struktur Classes dalam Program

Aplikasi FutsalKu terdiri dari beberapa kelompok class yang diorganisasikan berdasarkan tanggung jawabnya (separation of concerns):

**A. Package `model` — Entity Classes**

| No. | Class | Tipe | Atribut | Method Utama |
|:---:|-------|------|---------|-------------|
| 1 | `Lapangan` | Abstract Class | `idLapangan: int`, `nama: String`, `tipe: String`, `hargaPerJam: double`, `status: String`, `fasilitas: String` | `hitungHarga(durasi): double` *(abstract)*, getter/setter |
| 2 | `LapanganIndoor` | Concrete Class (extends Lapangan) | *(inherited)* | `hitungHarga(durasi)` — harga × durasi × 1.10 (surcharge AC 10%) |
| 3 | `LapanganOutdoor` | Concrete Class (extends Lapangan) | *(inherited)* | `hitungHarga(durasi)` — harga × durasi (tanpa biaya tambahan) |
| 4 | `Pelanggan` | Concrete Class | `idPelanggan: int`, `nama: String`, `noTelp: String`, `email: String`, `isMember: boolean`, `tglDaftar: Date` | getter/setter |
| 5 | `Booking` | Concrete Class | `idBooking: int`, `idLapangan: int`, `idPelanggan: int`, `tanggal: Date`, `jamMulai: Time`, `durasi: int`, `totalHarga: double`, `status: String` | getter/setter |
| 6 | `Pembayaran` | Abstract Class | `idPembayaran: int`, `idBooking: int`, `jumlah: double`, `metode: String`, `status: String`, `tglBayar: Date` | `prosesTransaksi(): boolean` *(abstract)*, getter/setter |
| 7 | `PembayaranCash` | Concrete Class (extends Pembayaran) | *(inherited)* | `prosesTransaksi()` — langsung set status "Lunas" |
| 8 | `PembayaranTransfer` | Concrete Class (extends Pembayaran) | *(inherited)* | `prosesTransaksi()` — set status "Pending" (butuh verifikasi) |

**B. Package `interfaces` — Interface OOP**

| No. | Interface | Method | Deskripsi |
|:---:|-----------|--------|-----------|
| 1 | `Printable` | `cetakBukti(): String` | Kontrak untuk mencetak bukti booking/pembayaran |
| 2 | `Searchable<T>` | `cari(keyword: String): List<T>` | Kontrak generik untuk pencarian data |

**C. Package `dao` — Data Access Object**

| No. | Class | Deskripsi |
|:---:|-------|-----------|
| 1 | `LapanganDAO` | Operasi CRUD tabel `lapangan` |
| 2 | `PelangganDAO` | Operasi CRUD tabel `pelanggan` |
| 3 | `BookingDAO` | Operasi CRUD tabel `booking` |
| 4 | `PembayaranDAO` | Operasi CRUD tabel `pembayaran` |

**D. Package `gui` — Java Swing GUI**

| No. | Class | Deskripsi |
|:---:|-------|-----------|
| 1 | `MainFrame` | Frame utama dengan navigasi antar panel |
| 2 | `DashboardPanel` | Panel statistik ringkasan |
| 3 | `LapanganPanel` | Panel kelola data lapangan |
| 4 | `PelangganPanel` | Panel kelola data pelanggan |
| 5 | `BookingPanel` | Panel reservasi dengan kalender |
| 6 | `PembayaranPanel` | Panel proses pembayaran |
| 7 | `RiwayatPanel` | Panel riwayat booking |

**E. Package `util` — Utility**

| No. | Class | Deskripsi |
|:---:|-------|-----------|
| 1 | `DatabaseConnection` | Singleton pattern untuk koneksi MySQL via JDBC |
| 2 | `ValidationHelper` | Helper method untuk validasi input (email, telepon, dll.) |

#### 1.5.2. Relasi Antar Class

```
                        <<interface>>              <<interface>>
                         Printable                  Searchable<T>
                        ┌──────────┐              ┌──────────────┐
                        │cetakBukti│              │cari(keyword) │
                        └────┬─────┘              └──────┬───────┘
                             │ implements                 │ implements
                     ┌───────┴────────┐                   │
                     ▼                ▼                   ▼
              ┌─────────────┐  ┌───────────────┐  ┌─────────────┐
              │  Booking    │  │  Pembayaran   │  │ PelangganDAO│
              │ (concrete)  │  │  (abstract)   │  └─────────────┘
              └──────┬──────┘  └───────┬───────┘
                     │                 │ extends
                     │          ┌──────┴──────┐
                     │          ▼             ▼
                     │  ┌──────────────┐ ┌────────────────┐
                     │  │PembayaranCash│ │PembayaranTransfer│
                     │  └──────────────┘ └────────────────┘
                     │
              ┌──────────────┐
              │  Lapangan    │
              │  (abstract)  │
              └──────┬───────┘
                     │ extends
              ┌──────┴──────┐
              ▼             ▼
      ┌──────────────┐ ┌────────────────┐
      │LapanganIndoor│ │LapanganOutdoor │
      └──────────────┘ └────────────────┘

   ┌──────────────────────────────────────────┐
   │              Pelanggan                    │
   │ (concrete, standalone entity class)       │
   └──────────────────────────────────────────┘

   Model ◄──── DAO ◄──── GUI
   (data)    (CRUD DB)  (tampilan)
```

Penjelasan relasi:
- `LapanganIndoor` dan `LapanganOutdoor` merupakan subclass dari `Lapangan` (inheritance).
- `PembayaranCash` dan `PembayaranTransfer` merupakan subclass dari `Pembayaran` (inheritance).
- `Booking` dan subclass `Pembayaran` mengimplementasikan interface `Printable`.
- `PelangganDAO` mengimplementasikan interface `Searchable<Pelanggan>`.
- Setiap class DAO mengakses model class yang bersesuaian dan berkomunikasi dengan database melalui `DatabaseConnection`.
- Setiap class GUI menggunakan class DAO untuk operasi data.

---

### 1.6. Rancangan Database

#### 1.6.1. Entitas dalam Database

Sistem FutsalKu menggunakan database MySQL dengan nama `futsalku_db` yang terdiri dari empat entitas utama:

| No. | Entitas | Deskripsi |
|:---:|---------|-----------|
| 1 | **Lapangan** | Menyimpan informasi lapangan futsal yang tersedia untuk disewakan, mencakup tipe, harga, dan fasilitas. |
| 2 | **Pelanggan** | Menyimpan data identitas pelanggan yang melakukan reservasi, termasuk status keanggotaan. |
| 3 | **Booking** | Menyimpan data transaksi reservasi yang menghubungkan pelanggan dengan lapangan pada waktu tertentu. |
| 4 | **Pembayaran** | Menyimpan data pembayaran atas setiap transaksi booking yang dilakukan. |

#### 1.6.2. Struktur Tabel dalam Database

**Tabel `lapangan`**

| No. | Field | Tipe Data | Constraint | Keterangan |
|:---:|-------|-----------|------------|------------|
| 1 | `id_lapangan` | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik lapangan |
| 2 | `nama` | VARCHAR(100) | NOT NULL | Nama lapangan |
| 3 | `tipe` | ENUM('Indoor','Outdoor') | NOT NULL | Tipe lapangan |
| 4 | `harga_per_jam` | DECIMAL(12,2) | NOT NULL | Harga sewa per jam |
| 5 | `status` | ENUM('Tersedia','Tidak Tersedia') | DEFAULT 'Tersedia' | Status ketersediaan |
| 6 | `fasilitas` | TEXT | — | Deskripsi fasilitas |

**Tabel `pelanggan`**

| No. | Field | Tipe Data | Constraint | Keterangan |
|:---:|-------|-----------|------------|------------|
| 1 | `id_pelanggan` | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik pelanggan |
| 2 | `nama` | VARCHAR(100) | NOT NULL | Nama pelanggan |
| 3 | `no_telp` | VARCHAR(15) | — | Nomor telepon |
| 4 | `email` | VARCHAR(100) | — | Alamat email |
| 5 | `is_member` | BOOLEAN | DEFAULT FALSE | Status member |
| 6 | `tgl_daftar` | DATE | DEFAULT CURRENT_DATE | Tanggal registrasi |

**Tabel `booking`**

| No. | Field | Tipe Data | Constraint | Keterangan |
|:---:|-------|-----------|------------|------------|
| 1 | `id_booking` | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik booking |
| 2 | `id_lapangan` | INT | FOREIGN KEY → lapangan | Referensi lapangan |
| 3 | `id_pelanggan` | INT | FOREIGN KEY → pelanggan | Referensi pelanggan |
| 4 | `tanggal` | DATE | NOT NULL | Tanggal booking |
| 5 | `jam_mulai` | TIME | NOT NULL | Jam mulai sewa |
| 6 | `durasi` | INT | NOT NULL | Durasi sewa (jam) |
| 7 | `total_harga` | DECIMAL(12,2) | NOT NULL | Total biaya |
| 8 | `status` | ENUM('Pending','Confirmed','Cancelled','Done') | DEFAULT 'Pending' | Status booking |

**Tabel `pembayaran`**

| No. | Field | Tipe Data | Constraint | Keterangan |
|:---:|-------|-----------|------------|------------|
| 1 | `id_pembayaran` | INT | PRIMARY KEY, AUTO_INCREMENT | ID unik pembayaran |
| 2 | `id_booking` | INT | FOREIGN KEY → booking | Referensi booking |
| 3 | `jumlah` | DECIMAL(12,2) | NOT NULL | Jumlah dibayar |
| 4 | `metode` | ENUM('Cash','Transfer') | NOT NULL | Metode pembayaran |
| 5 | `status` | ENUM('Pending','Lunas') | DEFAULT 'Pending' | Status pembayaran |
| 6 | `tgl_bayar` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Waktu pembayaran |

#### 1.6.3. Relasi Antar Tabel (Conceptual Data Model)

```
  ┌──────────────┐          ┌──────────────┐
  │  PELANGGAN   │          │   LAPANGAN   │
  │──────────────│          │──────────────│
  │ id_pelanggan │(PK)      │ id_lapangan  │(PK)
  │ nama         │          │ nama         │
  │ no_telp      │          │ tipe         │
  │ email        │          │ harga_per_jam│
  │ is_member    │          │ status       │
  │ tgl_daftar   │          │ fasilitas    │
  └──────┬───────┘          └──────┬───────┘
         │                         │
         │ 1                       │ 1
         │                         │
         │ melakukan               │ digunakan dalam
         │                         │
         │ N                       │ N
         │                         │
  ┌──────┴─────────────────────────┴──────┐
  │               BOOKING                  │
  │────────────────────────────────────────│
  │ id_booking     (PK)                    │
  │ id_pelanggan   (FK → pelanggan)        │
  │ id_lapangan    (FK → lapangan)         │
  │ tanggal                                │
  │ jam_mulai                              │
  │ durasi                                 │
  │ total_harga                            │
  │ status                                 │
  └──────────────────┬─────────────────────┘
                     │
                     │ 1
                     │
                     │ memiliki
                     │
                     │ 1
                     │
  ┌──────────────────┴─────────────────────┐
  │             PEMBAYARAN                  │
  │────────────────────────────────────────│
  │ id_pembayaran  (PK)                    │
  │ id_booking     (FK → booking)          │
  │ jumlah                                 │
  │ metode                                 │
  │ status                                 │
  │ tgl_bayar                              │
  └────────────────────────────────────────┘
```

**Penjelasan Relasi:**
- **Pelanggan → Booking** : One-to-Many (1:N) — Satu pelanggan dapat memiliki banyak booking.
- **Lapangan → Booking** : One-to-Many (1:N) — Satu lapangan dapat digunakan dalam banyak booking.
- **Booking → Pembayaran** : One-to-One (1:1) — Setiap booking memiliki tepat satu catatan pembayaran.

---

### 1.7. Konsep Paradigma OOP yang Diimplementasikan dalam Program

Aplikasi FutsalKu mengimplementasikan empat pilar utama paradigma Object-Oriented Programming (OOP) sebagai berikut:

#### A. Inheritance (Pewarisan)

Inheritance diterapkan pada dua hierarki class:

**Hierarki Lapangan:**
- `Lapangan` (abstract class) sebagai parent class.
- `LapanganIndoor` dan `LapanganOutdoor` sebagai subclass yang mewarisi seluruh atribut dan method dari `Lapangan`.

**Hierarki Pembayaran:**
- `Pembayaran` (abstract class) sebagai parent class.
- `PembayaranCash` dan `PembayaranTransfer` sebagai subclass yang mewarisi dari `Pembayaran`.

Penerapan inheritance memungkinkan penggunaan kembali kode (*code reuse*) dan membentuk hierarki class yang logis sesuai dengan domain permasalahan.

#### B. Encapsulation (Enkapsulasi)

Enkapsulasi diterapkan pada seluruh entity class dalam package `model`:
- Seluruh atribut class dideklarasikan dengan access modifier `private`.
- Akses terhadap atribut dilakukan melalui method `getter` (untuk membaca) dan `setter` (untuk mengubah).
- Validasi data dapat disisipkan di dalam method `setter` untuk menjaga integritas data.

Contoh penerapan pada class `Lapangan`:
```java
private double hargaPerJam;       // atribut private

public double getHargaPerJam() {  // getter
    return hargaPerJam;
}

public void setHargaPerJam(double hargaPerJam) {  // setter
    this.hargaPerJam = hargaPerJam;
}
```

#### C. Polymorphism (Polimorfisme)

Polimorfisme diterapkan melalui mekanisme *method overriding*:

- Method `hitungHarga(int durasi)` pada class `Lapangan` di-override oleh `LapanganIndoor` (menambahkan surcharge 10% untuk AC) dan `LapanganOutdoor` (tanpa biaya tambahan).
- Method `prosesTransaksi()` pada class `Pembayaran` di-override oleh `PembayaranCash` (langsung set status "Lunas") dan `PembayaranTransfer` (set status "Pending" untuk verifikasi).

Dengan polimorfisme, satu referensi bertipe parent class dapat memanggil method yang berbeda tergantung pada objek subclass yang sesungguhnya (*dynamic binding*).

#### D. Abstraction (Abstraksi)

Abstraksi diterapkan melalui dua mekanisme:

**Abstract Class:**
- `Lapangan` dan `Pembayaran` dideklarasikan sebagai abstract class yang tidak dapat di-instantiasi secara langsung, melainkan harus melalui subclass konkret.

**Interface:**
- `Printable` — mendefinisikan kontrak method `cetakBukti()` untuk mencetak bukti transaksi.
- `Searchable<T>` — mendefinisikan kontrak generik method `cari(String keyword)` untuk pencarian data.

Penggunaan abstraksi memungkinkan pemisahan antara *what* (apa yang harus dilakukan) dan *how* (bagaimana melakukannya), sehingga kode menjadi lebih modular dan mudah dikembangkan.

---

## 2. Peran

Berikut adalah pembagian peran dan tanggung jawab masing-masing anggota kelompok:

| No. | Nama | NIM | Peran | Tanggung Jawab |
|:---:|------|-----|-------|----------------|
| 1 | Hasan Shofiyyur Rahman | 2410512011 | Project Lead & Backend Developer | Arsitektur sistem, desain database, koneksi database (`DatabaseConnection`), DAO Booking & Pembayaran, GUI MainFrame & BookingPanel, integrasi keseluruhan sistem |
| 2 | Muhammad Akbar Al Islami | 2410512012 | Backend Developer | Model class (Lapangan, Pembayaran + subclass), DAO Lapangan, GUI DashboardPanel & PembayaranPanel & RiwayatPanel, implementasi Inheritance & Polymorphism |
| 3 | Rifqi Afif Zhain | 2410512013 | Backend Developer | Model class (Pelanggan, Booking), Interface Printable & Searchable, DAO Pelanggan, GUI LapanganPanel & PelangganPanel |
| 4 | Cantika Nurwulan Suci | 2410512022 | UI Designer & Documentation | Class diagram, wireframe/mockup GUI, data dummy untuk testing, pembuatan laporan akhir, video demo program |
| 5 | Varisha Aira Dalimunthe | 2410512027 | UI Designer & Documentation | Class diagram, wireframe/mockup GUI, data dummy untuk testing, pembuatan laporan akhir, video demo program |

---

*Catatan: Dokumen ini merupakan draft BAB I yang dapat diperbarui sesuai perkembangan implementasi program.*
