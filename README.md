# 💰 Smart Expense Tracker
### Pencatat Keuangan Mahasiswa — Aplikasi Desktop Berbasis Java

> Project Akhir — Pemrograman Berorientasi Objek (OOP) | Semester 4 | 2026

---

## 👥 Tim

| No. | Nama | Peran |
|-----|------|-------|
| 1 | Torikh Abdullah Naser | Lead Developer & Database |
| 2 | Anggota 2 | Backend Developer |
| 3 | Anggota 3 | Backend Developer |
| 4 | Anggota 4 | UI/UX & Dokumentasi |
| 5 | Anggota 5 | UI/UX & Testing |

---

## 📌 Deskripsi Proyek

**Smart Expense Tracker** adalah aplikasi desktop pencatat keuangan yang dirancang khusus untuk mahasiswa. Aplikasi ini membantu pengguna dalam mengelola keuangan pribadi melalui pencatatan pemasukan & pengeluaran, pengkategorian transaksi, visualisasi statistik bulanan, serta fitur target tabungan.

Dibangun menggunakan bahasa pemrograman **Java** dengan menerapkan prinsip-prinsip **Object-Oriented Programming (OOP)**, antarmuka **GUI (Graphical User Interface)** menggunakan Java Swing, dan integrasi **database MySQL** untuk penyimpanan data persisten.

### Alur Kerja Aplikasi

```
User Login / Register
        ↓
Dashboard Utama (Ringkasan Keuangan)
        ↓
┌───────────────────────────────────────────┐
│  Catat Transaksi (Pemasukan/Pengeluaran)  │
│  Pilih Kategori & Input Nominal           │
│  Simpan ke Database                       │
└───────────────────────────────────────────┘
        ↓
Lihat Riwayat Transaksi & Statistik Bulanan
        ↓
Pantau Target Tabungan
```

---

## 🎯 Fitur Aplikasi

| No. | Fitur | Deskripsi |
|-----|-------|-----------|
| 1 | **Autentikasi User** | Sistem login & register untuk keamanan data pribadi |
| 2 | **Catat Pemasukan** | Input pemasukan dengan kategori (Gaji, Uang Saku, Freelance, dll.) |
| 3 | **Catat Pengeluaran** | Input pengeluaran dengan kategori (Makan, Transportasi, Hiburan, dll.) |
| 4 | **Riwayat Transaksi** | Menampilkan daftar seluruh transaksi dengan filter tanggal & kategori |
| 5 | **Statistik Bulanan** | Visualisasi grafik pemasukan vs pengeluaran per bulan |
| 6 | **Target Tabungan** | Pengaturan target tabungan dengan progress tracking |
| 7 | **Manajemen Kategori** | CRUD kategori transaksi kustom |
| 8 | **Laporan Keuangan** | Ringkasan keuangan (total pemasukan, pengeluaran, saldo) |

---

## 🏗️ Konsep OOP yang Diterapkan

Proyek ini menerapkan **4 pilar utama OOP** sebagai berikut:

### 1. Inheritance (Pewarisan)
Class `Pemasukan` dan `Pengeluaran` mewarisi class abstract `Transaksi`:
```java
public abstract class Transaksi {
    protected double jumlah;
    protected String deskripsi;
    protected LocalDate tanggal;
    protected Kategori kategori;
    
    public abstract String getTipe();
}

public class Pemasukan extends Transaksi {
    @Override
    public String getTipe() { return "PEMASUKAN"; }
}

public class Pengeluaran extends Transaksi {
    @Override
    public String getTipe() { return "PENGELUARAN"; }
}
```

### 2. Encapsulation (Enkapsulasi)
Semua atribut bersifat `private` dengan akses melalui getter/setter:
```java
public class User {
    private int id;
    private String username;
    private String password;
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
```

### 3. Polymorphism (Polimorfisme)
Method `hitungTotal()` pada class `Laporan` berperilaku berbeda tergantung tipe transaksi:
```java
public class Laporan {
    public double hitungTotal(List<Transaksi> transaksiList) {
        double total = 0;
        for (Transaksi t : transaksiList) {
            if (t instanceof Pemasukan) {
                total += t.getJumlah();
            } else if (t instanceof Pengeluaran) {
                total -= t.getJumlah();
            }
        }
        return total;
    }
}
```

### 4. Abstraction (Abstraksi)
Class abstract `Transaksi` mendefinisikan blueprint umum tanpa implementasi spesifik:
```java
public abstract class Transaksi {
    public abstract String getTipe();
    public abstract double hitungDampakSaldo();
}
```

---

## 🛠️ Tech Stack

| Teknologi | Versi | Kegunaan |
|-----------|-------|----------|
| Java | JDK 17+ | Bahasa Pemrograman Utama |
| Java Swing | - | GUI Framework |
| MySQL | 8.0+ | Database Management System |
| JDBC | - | Database Connectivity |
| IntelliJ IDEA | 2024+ | IDE Development |
| Maven | 3.9+ | Dependency & Build Management |
| Git & GitHub | - | Version Control & Collaboration |

---

## 📁 Struktur Proyek

```
SmartExpenseTracker/
├── .gitignore
├── README.md
├── pom.xml                                   # Maven configuration
├── docs/
│   └── laporan_akhir.pdf                     # Laporan akhir project
├── sql/
│   └── schema.sql                            # Database schema & seed data
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── smartexpense/
        │           ├── Main.java             # Entry point aplikasi
        │           ├── model/
        │           │   ├── User.java         # Model user/akun
        │           │   ├── Transaksi.java    # Abstract class transaksi
        │           │   ├── Pemasukan.java    # Class pemasukan (extends Transaksi)
        │           │   ├── Pengeluaran.java  # Class pengeluaran (extends Transaksi)
        │           │   ├── Kategori.java     # Model kategori transaksi
        │           │   └── Laporan.java      # Model laporan keuangan
        │           ├── dao/
        │           │   ├── DatabaseConnection.java  # Koneksi database
        │           │   ├── UserDAO.java              # Data Access Object - User
        │           │   ├── TransaksiDAO.java         # Data Access Object - Transaksi
        │           │   └── KategoriDAO.java          # Data Access Object - Kategori
        │           ├── view/
        │           │   ├── LoginFrame.java           # Halaman login
        │           │   ├── RegisterFrame.java        # Halaman register
        │           │   ├── DashboardFrame.java       # Dashboard utama
        │           │   ├── TransaksiPanel.java       # Panel input transaksi
        │           │   ├── RiwayatPanel.java         # Panel riwayat transaksi
        │           │   ├── StatistikPanel.java       # Panel statistik grafik
        │           │   └── TabunganPanel.java        # Panel target tabungan
        │           └── util/
        │               ├── FormatUtil.java           # Utility formatting (currency, date)
        │               └── ValidationUtil.java       # Utility validasi input
        └── resources/
            ├── icons/                                # Icon dan asset gambar
            └── config.properties                     # Konfigurasi database
```

---

## 📐 Database Schema

```sql
-- Tabel User
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Kategori
CREATE TABLE kategori (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(50) NOT NULL,
    tipe ENUM('PEMASUKAN', 'PENGELUARAN') NOT NULL,
    icon VARCHAR(50)
);

-- Tabel Transaksi
CREATE TABLE transaksi (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    kategori_id INT NOT NULL,
    jumlah DECIMAL(15,2) NOT NULL,
    deskripsi TEXT,
    tanggal DATE NOT NULL,
    tipe ENUM('PEMASUKAN', 'PENGELUARAN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (kategori_id) REFERENCES kategori(id)
);

-- Tabel Target Tabungan
CREATE TABLE target_tabungan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    nama_target VARCHAR(100) NOT NULL,
    jumlah_target DECIMAL(15,2) NOT NULL,
    jumlah_terkumpul DECIMAL(15,2) DEFAULT 0,
    deadline DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## ⚙️ Cara Menjalankan

### 1. Prerequisites
- **JDK 17+** sudah terinstall
- **MySQL 8.0+** sudah terinstall dan berjalan
- **IntelliJ IDEA** (recommended) atau IDE Java lainnya
- **Maven** (biasanya sudah bundled di IntelliJ)

### 2. Setup Database
```bash
# Login ke MySQL
mysql -u root -p

# Buat database
CREATE DATABASE smart_expense_tracker;
USE smart_expense_tracker;

# Jalankan script schema
SOURCE sql/schema.sql;
```

### 3. Konfigurasi Koneksi Database
Edit file `src/main/resources/config.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/smart_expense_tracker
db.username=root
db.password=your_password
```

### 4. Clone & Jalankan Proyek
```bash
# Clone repository
git clone https://github.com/<username>/smart-expense-tracker.git
cd smart-expense-tracker

# Build & Run dengan Maven
mvn clean compile exec:java
```

Atau buka project di **IntelliJ IDEA**, lalu jalankan `Main.java`.

---

## 📋 Pembagian Tugas

### Fase 1 — Setup & Fondasi (Minggu 1)

| No. | Task | PIC | Status |
|-----|------|-----|--------|
| 1 | Setup repository GitHub & project structure | Torikh Abdullah Naser | ⬜ |
| 2 | Desain database schema & buat script SQL | Torikh Abdullah Naser | ⬜ |
| 3 | Implementasi `DatabaseConnection.java` | Torikh Abdullah Naser | ⬜ |
| 4 | Implementasi class model (`User`, `Transaksi`, `Kategori`, `Laporan`) | Anggota 2 | ⬜ |
| 5 | Implementasi class `Pemasukan` & `Pengeluaran` (inheritance) | Anggota 3 | ⬜ |

### Fase 2 — Backend & Database (Minggu 2)

| No. | Task | PIC | Status |
|-----|------|-----|--------|
| 1 | Implementasi `UserDAO.java` (CRUD user, login, register) | Torikh Abdullah Naser | ⬜ |
| 2 | Implementasi `TransaksiDAO.java` (CRUD transaksi) | Anggota 2 | ⬜ |
| 3 | Implementasi `KategoriDAO.java` (CRUD kategori) | Anggota 3 | ⬜ |
| 4 | Implementasi `Laporan.java` — polymorphism perhitungan | Anggota 2 | ⬜ |
| 5 | Implementasi utility classes (`FormatUtil`, `ValidationUtil`) | Anggota 3 | ⬜ |

### Fase 3 — GUI Development (Minggu 3)

| No. | Task | PIC | Status |
|-----|------|-----|--------|
| 1 | Desain & implementasi `LoginFrame` + `RegisterFrame` | Torikh Abdullah Naser | ⬜ |
| 2 | Desain & implementasi `DashboardFrame` | Torikh Abdullah Naser | ⬜ |
| 3 | Desain & implementasi `TransaksiPanel` | Anggota 2 | ⬜ |
| 4 | Desain & implementasi `RiwayatPanel` | Anggota 3 | ⬜ |
| 5 | Desain mockup/wireframe halaman GUI | Anggota 4 | ⬜ |
| 6 | Menyiapkan aset visual (icon, warna, font) untuk GUI | Anggota 5 | ⬜ |

### Fase 4 — Fitur Lanjutan & Integrasi (Minggu 4)

| No. | Task | PIC | Status |
|-----|------|-----|--------|
| 1 | Implementasi `StatistikPanel` (grafik bulanan) | Torikh Abdullah Naser | ⬜ |
| 2 | Implementasi `TabunganPanel` (target tabungan) | Anggota 2 | ⬜ |
| 3 | Integrasi seluruh panel ke `DashboardFrame` | Torikh Abdullah Naser | ⬜ |
| 4 | Penyusunan laporan akhir (BAB I - BAB III) | Anggota 4 | ⬜ |
| 5 | Penyusunan laporan akhir (BAB IV - Lampiran) | Anggota 5 | ⬜ |

### Fase 5 — Testing & Finalisasi (Minggu 5)

| No. | Task | PIC | Status |
|-----|------|-----|--------|
| 1 | Bug fixing & code review | Torikh Abdullah Naser | ⬜ |
| 2 | Testing keseluruhan fitur | Anggota 5 | ⬜ |
| 3 | Pembuatan video demo program | Anggota 4 | ⬜ |
| 4 | Finalisasi laporan akhir | Anggota 4 & Anggota 5 | ⬜ |
| 5 | Final review & submission | Torikh Abdullah Naser | ⬜ |

---

## 📊 Ringkasan Kontribusi

| Anggota | Fokus Utama | Estimasi Kontribusi |
|---------|-------------|---------------------|
| **Torikh Abdullah Naser** | Lead Developer — Setup, Database, GUI Core, Integrasi, Bug Fix | ⭐⭐⭐⭐⭐ |
| **Anggota 2** | Backend Developer — Model, DAO, Panel Transaksi & Tabungan | ⭐⭐⭐⭐ |
| **Anggota 3** | Backend Developer — Inheritance classes, DAO, Panel Riwayat, Utility | ⭐⭐⭐⭐ |
| **Anggota 4** | UI/UX & Dokumentasi — Desain mockup, Laporan akhir, Video demo | ⭐⭐⭐ |
| **Anggota 5** | UI/UX & Testing — Aset visual, Testing, Laporan akhir | ⭐⭐⭐ |

---

## 📚 Referensi

- Oracle Java Documentation: https://docs.oracle.com/en/java/
- Java Swing Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/
- MySQL Documentation: https://dev.mysql.com/doc/
- JDBC Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/
- Maven Getting Started: https://maven.apache.org/guides/getting-started/

---

## 📝 Catatan Penting

> **Ketentuan Project Akhir:**
> - ✅ Aplikasi berbasis objek menggunakan Java
> - ✅ Minimal 3 konsep OOP (Inheritance, Encapsulation, Polymorphism, Abstraction)
> - ✅ Menerapkan konsep GUI dalam Java (Java Swing)
> - ✅ Menampilkan data dari database (MySQL + JDBC)
> - ✅ Deliverables: Source Code, Video Demo, Laporan Akhir (PDF)

---

*Project Akhir — Pemrograman Berorientasi Objek | Semester 4 | 2026*
