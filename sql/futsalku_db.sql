-- ============================================
-- FutsalKu Database Schema
-- Sistem Reservasi Lapangan Futsal
-- ============================================

-- Buat database
CREATE DATABASE IF NOT EXISTS futsalku_db;
USE futsalku_db;

-- ============================================
-- Tabel: lapangan
-- Menyimpan data lapangan futsal
-- ============================================
CREATE TABLE IF NOT EXISTS lapangan (
    id_lapangan INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    tipe ENUM('Indoor', 'Outdoor') NOT NULL,
    harga_per_jam DECIMAL(12, 2) NOT NULL,
    status ENUM('Tersedia', 'Tidak Tersedia') DEFAULT 'Tersedia',
    fasilitas TEXT
) ENGINE=InnoDB;

-- ============================================
-- Tabel: pelanggan
-- Menyimpan data pelanggan/member
-- ============================================
CREATE TABLE IF NOT EXISTS pelanggan (
    id_pelanggan INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_telp VARCHAR(15),
    email VARCHAR(100),
    is_member BOOLEAN DEFAULT FALSE,
    tgl_daftar DATE DEFAULT (CURRENT_DATE)
) ENGINE=InnoDB;

-- ============================================
-- Tabel: booking
-- Menyimpan data reservasi lapangan
-- ============================================
CREATE TABLE IF NOT EXISTS booking (
    id_booking INT AUTO_INCREMENT PRIMARY KEY,
    id_lapangan INT NOT NULL,
    id_pelanggan INT NOT NULL,
    tanggal DATE NOT NULL,
    jam_mulai TIME NOT NULL,
    durasi INT NOT NULL COMMENT 'Durasi dalam jam',
    total_harga DECIMAL(12, 2) NOT NULL,
    status ENUM('Pending', 'Confirmed', 'Cancelled', 'Done') DEFAULT 'Pending',
    FOREIGN KEY (id_lapangan) REFERENCES lapangan(id_lapangan),
    FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan)
) ENGINE=InnoDB;

-- ============================================
-- Tabel: pembayaran
-- Menyimpan data transaksi pembayaran
-- ============================================
CREATE TABLE IF NOT EXISTS pembayaran (
    id_pembayaran INT AUTO_INCREMENT PRIMARY KEY,
    id_booking INT NOT NULL,
    jumlah DECIMAL(12, 2) NOT NULL,
    metode ENUM('Cash', 'Transfer') NOT NULL,
    status ENUM('Pending', 'Lunas') DEFAULT 'Pending',
    tgl_bayar DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_booking) REFERENCES booking(id_booking)
) ENGINE=InnoDB;

-- ============================================
-- Data Dummy (Opsional)
-- ============================================

-- Insert data lapangan
INSERT INTO lapangan (nama, tipe, harga_per_jam, status, fasilitas) VALUES
('Lapangan A1', 'Indoor', 150000.00, 'Tersedia', 'AC, Lampu LED, Tribun Penonton'),
('Lapangan A2', 'Indoor', 150000.00, 'Tersedia', 'AC, Lampu LED'),
('Lapangan B1', 'Outdoor', 100000.00, 'Tersedia', 'Lampu Sorot, Parkir Luas'),
('Lapangan B2', 'Outdoor', 100000.00, 'Tersedia', 'Lampu Sorot'),
('Lapangan C1', 'Indoor', 200000.00, 'Tersedia', 'AC, Lampu LED, Tribun, Sound System');

-- Insert data pelanggan
INSERT INTO pelanggan (nama, no_telp, email, is_member) VALUES
('Ahmad Fauzi', '081234567890', 'ahmad@email.com', TRUE),
('Budi Santoso', '082345678901', 'budi@email.com', FALSE),
('Citra Dewi', '083456789012', 'citra@email.com', TRUE),
('Dian Pratama', '084567890123', 'dian@email.com', FALSE),
('Eko Wijaya', '085678901234', 'eko@email.com', TRUE);
