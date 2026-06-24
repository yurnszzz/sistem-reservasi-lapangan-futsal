package com.futsalku.dao;

import com.futsalku.model.Pembayaran;
import com.futsalku.model.PembayaranCash;
import com.futsalku.model.PembayaranTransfer;
import com.futsalku.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PembayaranDAO — Data Access Object untuk tabel pembayaran
 * Mengelola operasi CRUD ke database
 * Menggunakan Polymorphism untuk membuat PembayaranCash / PembayaranTransfer
 
 * PIC: Hasan (Hasan Shofiyyur Rahman)
 */
public class PembayaranDAO {

    // Mengambil semua data pembayaran dari database
    // Menggunakan Polymorphism: return PembayaranCash/PembayaranTransfer sesuai metode
    public List<Pembayaran> getAll() throws SQLException {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran ORDER BY id_pembayaran ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(createPembayaranFromResultSet(rs));
            }
        }
        return list;
    }

    // Mengambil data pembayaran berdasarkan ID
    public Pembayaran getById(int id) throws SQLException {
        String sql = "SELECT * FROM pembayaran WHERE id_pembayaran = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createPembayaranFromResultSet(rs);
                }
            }
        }
        return null;
    }

    
    // Mengambil data pembayaran berdasarkan ID booking
    public Pembayaran getByBookingId(int idBooking) throws SQLException {
        String sql = "SELECT * FROM pembayaran WHERE id_booking = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBooking);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createPembayaranFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // Mengambil daftar pembayaran yang masih pending (untuk konfirmasi transfer)
    public List<Pembayaran> getPending() throws SQLException {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran WHERE status = 'Pending' ORDER BY tgl_bayar ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(createPembayaranFromResultSet(rs));
            }
        }
        return list;
    }

    // Menambahkan pembayaran baru ke database
    public boolean insert(Pembayaran pembayaran) throws SQLException {
        String sql = "INSERT INTO pembayaran (id_booking, jumlah, metode, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pembayaran.getIdBooking());
            ps.setDouble(2, pembayaran.getJumlah());
            ps.setString(3, pembayaran.getMetode());
            ps.setString(4, pembayaran.getStatus());

            int affected = ps.executeUpdate();

            // Set ID dari auto-generated key
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        pembayaran.setIdPembayaran(keys.getInt(1));
                    }
                }
            }

            return affected > 0;
        }
    }

    // Mengupdate status pembayaran (misal: dari Pending → Lunas)
    public boolean updateStatus(int idPembayaran, String status) throws SQLException {
        String sql = "UPDATE pembayaran SET status = ? WHERE id_pembayaran = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, idPembayaran);

            return ps.executeUpdate() > 0;
        }
    }

    // Menghapus data pembayaran dari database berdasarkan ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pembayaran WHERE id_pembayaran = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Menghitung total pendapatan (status Lunas saja, untuk Dashboard)
    public double getTotalPendapatan() throws SQLException {
        String sql = "SELECT COALESCE(SUM(jumlah), 0) FROM pembayaran WHERE status = 'Lunas'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    // Helper method: Membuat objek Pembayaran dari ResultSet
    // Menggunakan Polymorphism — membuat PembayaranCash atau PembayaranTransfer
    // berdasarkan metode yang disimpan di database
    private Pembayaran createPembayaranFromResultSet(ResultSet rs) throws SQLException {
        String metode = rs.getString("metode");
        int id = rs.getInt("id_pembayaran");
        int idBooking = rs.getInt("id_booking");
        double jumlah = rs.getDouble("jumlah");
        String status = rs.getString("status");
        Timestamp tglBayar = rs.getTimestamp("tgl_bayar");

        if ("Cash".equalsIgnoreCase(metode)) {
            return new PembayaranCash(id, idBooking, jumlah, status, tglBayar);
        } else {
            return new PembayaranTransfer(id, idBooking, jumlah, status, tglBayar);
        }
    }
}
