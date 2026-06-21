# ⚽ FutsalKu — Sistem Reservasi Lapangan Futsal
### Aplikasi Desktop Berbasis Java dengan GUI & Database

> Project Akhir — Pemrograman Berorientasi Objek (OOP) | Semester 4 | 2026

---

## 👥 Tim

| No. | Nama | NIM | Peran |
|-----|------|-----|-------|
| 1 | Hasan Shofiyyur Rahman | 2410512011 | Project Lead & Backend Developer |
| 2 | Muhammad Akbar Al Islami | 2410512012 | Backend Developer |
| 3 | Rifqi Afif Zhain | 2410512013 | Backend Developer |
| 4 | Cantika Nurwulan Suci | 2410512022 | UI Designer & Documentation |
| 5 | Varisha Aira Dalimunthe | 2410512027 | UI Designer & Documentation |

---

## 📌 Deskripsi Proyek

**FutsalKu** adalah aplikasi desktop untuk mengelola reservasi lapangan futsal secara efisien. Aplikasi ini dibangun menggunakan **Java Swing (GUI)** dan terhubung ke **database MySQL** untuk penyimpanan data yang persisten.

Sistem ini dirancang untuk memudahkan pengelola lapangan futsal dalam mengatur jadwal booking, mengelola data pelanggan & member, serta mencatat transaksi pembayaran — semua dalam satu aplikasi yang terintegrasi.

### Alur Kerja Sistem

```
Operator membuka aplikasi FutsalKu
         ↓
Login ke sistem (Admin / Operator)
         ↓
Kelola data: Lapangan, Member, Booking
         ↓
Pelanggan melakukan reservasi lapangan
         ↓
Pilih lapangan (Indoor/Outdoor) & jadwal
         ↓
Proses pembayaran (Cash/Transfer)
         ↓
Bukti booking & riwayat tersimpan di database
```

---

## 🎯 Fitur Utama

| No. | Fitur | Deskripsi |
|-----|-------|-----------|
| 1 | **Manajemen Lapangan** | CRUD data lapangan futsal (Indoor & Outdoor) |
| 2 | **Manajemen Member** | Registrasi & pengelolaan data member/pelanggan |
| 3 | **Booking Jadwal** | Reservasi lapangan dengan kalender jadwal interaktif |
| 4 | **Pembayaran** | Pencatatan pembayaran (Cash & Transfer) |
| 5 | **Riwayat Booking** | Melihat histori reservasi & status pembayaran |
| 6 | **Dashboard** | Ringkasan statistik booking & pendapatan |

---

## 🧬 Penerapan Konsep OOP

Proyek ini menerapkan **4 pilar utama** Object-Oriented Programming:

### 1. Inheritance (Pewarisan)
```
Lapangan (Abstract Class)
├── LapanganIndoor
└── LapanganOutdoor

Pembayaran (Abstract Class)
├── PembayaranCash
└── PembayaranTransfer
```

### 2. Encapsulation (Enkapsulasi)
- Semua atribut class menggunakan access modifier `private`
- Akses data melalui method `getter` dan `setter`
- Validasi data dilakukan di dalam setter

### 3. Polymorphism (Polimorfisme)
- Method `hitungHarga()` di-override di setiap subclass `Lapangan`
- Method `prosesTransaksi()` di-override di setiap subclass `Pembayaran`
- Penggunaan dynamic binding untuk memproses pembayaran

### 4. Abstraction (Abstraksi)
- Interface `Printable` — untuk mencetak bukti booking & pembayaran
- Interface `Searchable` — untuk pencarian data member & booking
- Abstract class sebagai template untuk `Lapangan` dan `Pembayaran`

---

## 🗄️ Desain Database

Aplikasi menggunakan **MySQL** sebagai RDBMS dengan skema berikut:

### Entity Relationship

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   lapangan   │     │  pelanggan   │     │   booking    │     │  pembayaran  │
├──────────────┤     ├──────────────┤     ├──────────────┤     ├──────────────┤
│ id_lapangan  │◄────│ id_pelanggan │◄────│ id_booking   │────►│ id_pembayaran│
│ nama         │     │ nama         │     │ id_lapangan  │     │ id_booking   │
│ tipe         │     │ no_telp      │     │ id_pelanggan │     │ jumlah       │
│ harga_per_jam│     │ email        │     │ tanggal      │     │ metode       │
│ status       │     │ is_member    │     │ jam_mulai    │     │ status       │
│ fasilitas    │     │ tgl_daftar   │     │ durasi       │     │ tgl_bayar    │
└──────────────┘     └──────────────┘     │ total_harga  │     └──────────────┘
                                          │ status       │
                                          └──────────────┘
```

### Tabel Database

| Tabel | Deskripsi | Jumlah Kolom |
|-------|-----------|:------------:|
| `lapangan` | Data lapangan futsal | 6 |
| `pelanggan` | Data pelanggan/member | 6 |
| `booking` | Data reservasi | 8 |
| `pembayaran` | Data transaksi pembayaran | 6 |

---

## 🛠️ Tech Stack

| Teknologi | Versi | Kegunaan |
|-----------|-------|----------|
| Java | JDK 17+ | Bahasa Pemrograman Utama |
| Java Swing | — | GUI (Graphical User Interface) |
| MySQL | 8.0+ | Database Management System |
| JDBC | — | Koneksi Java ↔ MySQL |
| IntelliJ IDEA | 2024+ | IDE untuk Development |

---

## 📁 Struktur Proyek

```
FutsalKu/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── futsalku/
│                   ├── Main.java                    # Entry point aplikasi
│                   ├── model/                        # Entity / POJO classes
│                   │   ├── Lapangan.java             # Abstract class Lapangan
│                   │   ├── LapanganIndoor.java       # Subclass Indoor
│                   │   ├── LapanganOutdoor.java      # Subclass Outdoor
│                   │   ├── Pelanggan.java            # Class Pelanggan
│                   │   ├── Booking.java              # Class Booking
│                   │   ├── Pembayaran.java           # Abstract class Pembayaran
│                   │   ├── PembayaranCash.java       # Subclass Cash
│                   │   └── PembayaranTransfer.java   # Subclass Transfer
│                   ├── dao/                           # Data Access Object (CRUD)
│                   │   ├── LapanganDAO.java
│                   │   ├── PelangganDAO.java
│                   │   ├── BookingDAO.java
│                   │   └── PembayaranDAO.java
│                   ├── gui/                           # Java Swing GUI
│                   │   ├── MainFrame.java             # Frame utama
│                   │   ├── DashboardPanel.java        # Panel dashboard
│                   │   ├── LapanganPanel.java         # Panel kelola lapangan
│                   │   ├── PelangganPanel.java        # Panel kelola pelanggan
│                   │   ├── BookingPanel.java          # Panel booking
│                   │   ├── PembayaranPanel.java       # Panel pembayaran
│                   │   └── RiwayatPanel.java          # Panel riwayat booking
│                   ├── util/                           # Utility classes
│                   │   ├── DatabaseConnection.java    # Koneksi database
│                   │   └── ValidationHelper.java      # Helper validasi input
│                   └── interfaces/                    # Interface OOP
│                       ├── Printable.java             # Interface cetak bukti
│                       └── Searchable.java            # Interface pencarian
├── sql/
│   └── futsalku_db.sql                               # Script pembuatan database
├── lib/
│   └── mysql-connector-j-x.x.x.jar                  # JDBC Driver MySQL
├── docs/
│   └── laporan/                                      # Laporan akhir
├── .gitignore
└── README.md                                         # File ini
```

---

## ⚙️ Cara Menjalankan

### Prasyarat
- **Java JDK 17** atau lebih baru
- **MySQL Server 8.0/XAMPP/sejenisnya**
- **IntelliJ IDEA/IDE lainnya**

### 1. Clone Repository
```bash
git clone https://github.com/yurnszzz/sistem-reservasi-lapangan-futsal.git
cd sistem-reservasi-lapangan-futsal
```

### 2. Setup Database
```bash
# Login ke MySQL
mysql -u root -p

# Jalankan script SQL
source sql/futsalku_db.sql
```

### 3. Konfigurasi Koneksi Database
Edit file `src/main/java/com/futsalku/util/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/futsalku_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 4. Jalankan Aplikasi
Buka project di **IntelliJ IDEA**, lalu:
1. Tambahkan `mysql-connector-j-x.x.x.jar` ke project library
2. Klik kanan `Main.java` → **Run 'Main'**

---

## 📋 Pembagian Tugas

### Fase 1 — Foundation

| Task | PIC | Prioritas | Status |
|------|-----|:---------:|:------:|
| Setup repo GitHub & struktur proyek | Hasan | 🔴 High | ✅ |
| Desain database & SQL script | Hasan | 🔴 High | ✅ |
| Class diagram & dokumentasi OOP | Cantika, Varisha | 🔴 High | ✅ |
| `DatabaseConnection.java` (koneksi DB) | Hasan | 🔴 High | ✅ |
| Model classes (Lapangan, Pelanggan, Booking, Pembayaran) | Apip, Akbar | 🔴 High | ✅ |
| Interface `Printable` & `Searchable` | Akbar | 🟡 Med | ✅ |

### Fase 2 — Core Logic

| Task | PIC | Prioritas | Status |
|------|-----|:---------:|:------:|
| `LapanganDAO.java` (CRUD Lapangan) | Apip | 🔴 High | ✅ |
| `PelangganDAO.java` (CRUD Pelanggan) | Akbar | 🔴 High | ✅ |
| `BookingDAO.java` (CRUD Booking) | Hasan | 🔴 High | ✅ |
| `PembayaranDAO.java` (CRUD Pembayaran) | Hasan | 🔴 High | ✅ |
| Inheritance & Polymorphism implementation | Akbar | 🟡 Med | ✅ |
| Data dummy untuk testing | Cantika, Varisha | 🟡 Med | ✅ |

### Fase 3 — GUI Development

| Task | PIC | Prioritas | Status |
|------|-----|:---------:|:------:|
| `MainFrame.java` (layout utama) | Hasan | 🔴 High | ✅ |
| `DashboardPanel.java` | Apip | 🔴 High | ✅ |
| `LapanganPanel.java` & `PelangganPanel.java` | Akbar | 🔴 High | ✅ |
| `BookingPanel.java` (dengan kalender) | Hasan | 🔴 High | ✅ |
| `PembayaranPanel.java` & `RiwayatPanel.java` | Akbar | 🟡 Med | ✅ |
| Desain mockup GUI (wireframe) | Cantika, Varisha | 🟡 Med | ✅ |

### Fase 4 — Integration & Polish

| Task | PIC | Prioritas | Status |
|------|-----|:---------:|:------:|
| Integrasi GUI ↔ DAO ↔ Database | Hasan, Apip, Akbar | 🔴 High | ⬜ |
| Testing & Bug fixing | Hasan, Apip, Akbar | 🔴 High | ⬜ |
| Video demo program | Cantika, Varisha | 🔴 High | ⬜ |
| Laporan akhir (format PDF) | Cantika, Varisha | 🔴 High | ⬜ |
| Final review & submission | Full Team | 🔴 High | ⬜ |

### Ringkasan Peran

| Anggota | Fokus Utama | Estimasi Kontribusi |
|---------|-------------|:-------------------:|
| **Hasan** | Arsitektur, Database, Core GUI, Integrasi | ~30% |
| **Akbar** | Model OOP, DAO Layer, GUI Panel | ~25% |
| **Apip** | Model OOP, DAO Layer, GUI Panel | ~20% |
| **Cantika** | Class Diagram, Wireframe, Dokumentasi, Video | ~12.5% |
| **Varisha** | Class Diagram, Wireframe, Dokumentasi, Video | ~12.5% |

> 💡 **Catatan:** Pembagian tugas bersifat fleksibel dan dapat disesuaikan sesuai progres tim.

---

## 🔗 Screenshot Aplikasi

> *(Coming Soon — akan ditambahkan setelah development selesai)*

---

## 📚 Referensi

- Oracle Java Documentation: https://docs.oracle.com/en/java/
- Java Swing Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/
- MySQL Documentation: https://dev.mysql.com/doc/
- JDBC Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/
- Design Patterns in Java: https://refactoring.guru/design-patterns/java

---

## 📄 Lisensi

Proyek ini dibuat untuk keperluan akademik sebagai Project Akhir mata kuliah **Pemrograman Berorientasi Objek (OOP)** — Semester 4, 2026.

---

*Universitas Pembangunan Nasional "Veteran" Jakarta — S1 Sistem Informasi 2024*
