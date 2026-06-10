-- ============================================================
-- Smart Expense Tracker — Database Schema
-- Project Akhir OOP | Semester 4 | 2026
-- ============================================================

-- Buat database
CREATE DATABASE IF NOT EXISTS smart_expense_tracker;
USE smart_expense_tracker;

-- ============================================================
-- Tabel: users
-- Menyimpan data akun pengguna
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- Tabel: kategori
-- Menyimpan kategori transaksi (pemasukan/pengeluaran)
-- ============================================================
CREATE TABLE IF NOT EXISTS kategori (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(50) NOT NULL,
    tipe ENUM('PEMASUKAN', 'PENGELUARAN') NOT NULL,
    icon VARCHAR(50) DEFAULT NULL
);

-- ============================================================
-- Tabel: transaksi
-- Menyimpan data transaksi keuangan
-- ============================================================
CREATE TABLE IF NOT EXISTS transaksi (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    kategori_id INT NOT NULL,
    jumlah DECIMAL(15,2) NOT NULL,
    deskripsi TEXT,
    tanggal DATE NOT NULL,
    tipe ENUM('PEMASUKAN', 'PENGELUARAN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (kategori_id) REFERENCES kategori(id) ON DELETE RESTRICT
);

-- ============================================================
-- Tabel: target_tabungan
-- Menyimpan data target tabungan pengguna
-- ============================================================
CREATE TABLE IF NOT EXISTS target_tabungan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    nama_target VARCHAR(100) NOT NULL,
    jumlah_target DECIMAL(15,2) NOT NULL,
    jumlah_terkumpul DECIMAL(15,2) DEFAULT 0.00,
    deadline DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- Seed Data: Kategori Default
-- ============================================================

-- Kategori Pemasukan
INSERT INTO kategori (nama, tipe, icon) VALUES
('Uang Saku', 'PEMASUKAN', '💵'),
('Gaji Part-Time', 'PEMASUKAN', '💼'),
('Freelance', 'PEMASUKAN', '💻'),
('Beasiswa', 'PEMASUKAN', '🎓'),
('Lainnya', 'PEMASUKAN', '📦');

-- Kategori Pengeluaran
INSERT INTO kategori (nama, tipe, icon) VALUES
('Makan & Minum', 'PENGELUARAN', '🍔'),
('Transportasi', 'PENGELUARAN', '🚌'),
('Hiburan', 'PENGELUARAN', '🎮'),
('Belanja', 'PENGELUARAN', '🛒'),
('Pendidikan', 'PENGELUARAN', '📚'),
('Kesehatan', 'PENGELUARAN', '💊'),
('Tagihan', 'PENGELUARAN', '📱'),
('Lainnya', 'PENGELUARAN', '📦');
