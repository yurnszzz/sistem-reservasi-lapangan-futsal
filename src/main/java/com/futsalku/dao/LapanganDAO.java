package com.futsalku.dao;

import com.futsalku.model.Lapangan;
import com.futsalku.model.LapanganIndoor;
import com.futsalku.model.LapanganOutdoor;
import com.futsalku.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LapanganDAO — Data Access Object untuk tabel lapangan
 * Mengelola operasi CRUD ke database
 * 
 * PIC: Apip (Rifqi Afif Zhain)
 */
public class LapanganDAO {

    // Mengambil semua data lapangan dari database
    // Menggunakan Polymorphism: return LapanganIndoor/LapanganOutdoor sesuai tipe
    public List<Lapangan> getAll() throws SQLException {
        List<Lapangan> list = new ArrayList<>();
        String sql = "SELECT * FROM lapangan";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lapangan lap = createLapanganFromResultSet(rs);
                list.add(lap);
            }
        }
        return list;
    }

    // Mengambil data lapangan berdasarkan ID
    public Lapangan getById(int id) throws SQLException {
        String sql = "SELECT * FROM lapangan WHERE id_lapangan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createLapanganFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // Mengambil lapangan yang tersedia saja
    public List<Lapangan> getAvailable() throws SQLException {
        List<Lapangan> list = new ArrayList<>();
        String sql = "SELECT * FROM lapangan WHERE status = 'Tersedia'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(createLapanganFromResultSet(rs));
            }
        }
        return list;
    }

    // Menambahkan data lapangan baru ke database
    public boolean insert(Lapangan lapangan) throws SQLException {
        String sql = "INSERT INTO lapangan (nama, tipe, harga_per_jam, status, fasilitas) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lapangan.getNama());
            ps.setString(2, lapangan.getTipe());
            ps.setDouble(3, lapangan.getHargaPerJam());
            ps.setString(4, lapangan.getStatus());
            ps.setString(5, lapangan.getFasilitas());

            return ps.executeUpdate() > 0;
        }
    }

    // Mengupdate data lapangan di database
    public boolean update(Lapangan lapangan) throws SQLException {
        String sql = "UPDATE lapangan SET nama = ?, tipe = ?, harga_per_jam = ?, status = ?, fasilitas = ? WHERE id_lapangan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lapangan.getNama());
            ps.setString(2, lapangan.getTipe());
            ps.setDouble(3, lapangan.getHargaPerJam());
            ps.setString(4, lapangan.getStatus());
            ps.setString(5, lapangan.getFasilitas());
            ps.setInt(6, lapangan.getIdLapangan());

            return ps.executeUpdate() > 0;
        }
    }

    // Menghapus data lapangan dari database berdasarkan ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM lapangan WHERE id_lapangan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Helper method: Membuat objek Lapangan dari ResultSet
    // Menggunakan Polymorphism — membuat LapanganIndoor atau LapanganOutdoor
    // berdasarkan tipe yang disimpan di database
    private Lapangan createLapanganFromResultSet(ResultSet rs) throws SQLException {
        String tipe = rs.getString("tipe");
        int id = rs.getInt("id_lapangan");
        String nama = rs.getString("nama");
        double harga = rs.getDouble("harga_per_jam");
        String status = rs.getString("status");
        String fasilitas = rs.getString("fasilitas");

        if ("Indoor".equalsIgnoreCase(tipe)) {
            return new LapanganIndoor(id, nama, harga, status, fasilitas);
        } else {
            return new LapanganOutdoor(id, nama, harga, status, fasilitas);
        }
    }
}
