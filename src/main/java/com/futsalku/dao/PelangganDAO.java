package com.futsalku.dao;

import com.futsalku.interfaces.Searchable;
import com.futsalku.model.Pelanggan;
import com.futsalku.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PelangganDAO — Data Access Object untuk tabel pelanggan
 * Mengelola operasi CRUD ke database
 * Mengimplementasikan interface Searchable (Abstraction)
 * 
 * PIC: Akbar (Muhammad Akbar Al Islami)
 */
public class PelangganDAO implements Searchable<Pelanggan> {

    /**
     * Mengambil semua data pelanggan dari database
     */
    public List<Pelanggan> getAll() throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan ORDER BY nama";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(createPelangganFromResultSet(rs));
            }
        }
        return list;
    }

    /**
     * Mengambil data pelanggan berdasarkan ID
     */
    public Pelanggan getById(int id) throws SQLException {
        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createPelangganFromResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Mengambil daftar pelanggan yang berstatus member
     */
    public List<Pelanggan> getMembers() throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan WHERE is_member = TRUE ORDER BY nama";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(createPelangganFromResultSet(rs));
            }
        }
        return list;
    }

    /**
     * Menambahkan pelanggan baru ke database
     */
    public boolean insert(Pelanggan pelanggan) throws SQLException {
        String sql = "INSERT INTO pelanggan (nama, no_telp, email, is_member) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelanggan.getNama());
            ps.setString(2, pelanggan.getNoTelp());
            ps.setString(3, pelanggan.getEmail());
            ps.setBoolean(4, pelanggan.isMember());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Mengupdate data pelanggan di database
     */
    public boolean update(Pelanggan pelanggan) throws SQLException {
        String sql = "UPDATE pelanggan SET nama = ?, no_telp = ?, email = ?, is_member = ? WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelanggan.getNama());
            ps.setString(2, pelanggan.getNoTelp());
            ps.setString(3, pelanggan.getEmail());
            ps.setBoolean(4, pelanggan.isMember());
            ps.setInt(5, pelanggan.getIdPelanggan());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Menghapus data pelanggan dari database berdasarkan ID
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Implementasi interface Searchable — Abstraction
     * Mencari pelanggan berdasarkan keyword (nama, no_telp, atau email)
     */
    @Override
    public List<Pelanggan> cari(String keyword) {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan WHERE nama LIKE ? OR no_telp LIKE ? OR email LIKE ? ORDER BY nama";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(createPelangganFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Helper method: Membuat objek Pelanggan dari ResultSet
     */
    private Pelanggan createPelangganFromResultSet(ResultSet rs) throws SQLException {
        return new Pelanggan(
                rs.getInt("id_pelanggan"),
                rs.getString("nama"),
                rs.getString("no_telp"),
                rs.getString("email"),
                rs.getBoolean("is_member"),
                rs.getDate("tgl_daftar")
        );
    }
}
