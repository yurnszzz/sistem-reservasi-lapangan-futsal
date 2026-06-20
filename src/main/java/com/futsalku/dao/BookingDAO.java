package com.futsalku.dao;

import com.futsalku.model.Booking;
import com.futsalku.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingDAO — Data Access Object untuk tabel booking
 * Mengelola operasi CRUD ke database

 * PIC: Hasan (Hasan Shofiyyur Rahman)
 */
public class BookingDAO {

    // Mengambil semua data booking dari database
    public List<Booking> getAll() throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking ORDER BY tanggal DESC, jam_mulai ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(createBookingFromResultSet(rs));
            }
        }
        return list;
    }

    // Mengambil data booking berdasarkan ID
    public Booking getById(int id) throws SQLException {
        String sql = "SELECT * FROM booking WHERE id_booking = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createBookingFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // Mengambil daftar booking berdasarkan tanggal tertentu
    // Berguna untuk melihat jadwal booking pada tanggal tertentu
    public List<Booking> getByDate(Date tanggal) throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE tanggal = ? ORDER BY jam_mulai ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, tanggal);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(createBookingFromResultSet(rs));
                }
            }
        }
        return list;
    }

    // Mengambil daftar booking berdasarkan ID pelanggan
    // Berguna untuk melihat riwayat booking pelanggan tertentu
    public List<Booking> getByPelanggan(int idPelanggan) throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE id_pelanggan = ? ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPelanggan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(createBookingFromResultSet(rs));
                }
            }
        }
        return list;
    }

    // Mengecek apakah lapangan sudah dibooking pada tanggal dan jam tertentu
    // Mencegah double booking
    public boolean isLapanganBooked(int idLapangan, Date tanggal, Time jamMulai, int durasi) throws SQLException {
        String sql = "SELECT COUNT(*) FROM booking " +
                     "WHERE id_lapangan = ? AND tanggal = ? AND status != 'Cancelled' " +
                     "AND ((jam_mulai <= ? AND ADDTIME(jam_mulai, SEC_TO_TIME(durasi * 3600)) > ?) " +
                     "OR (jam_mulai < ADDTIME(?, SEC_TO_TIME(? * 3600)) AND jam_mulai >= ?))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLapangan);
            ps.setDate(2, tanggal);
            ps.setTime(3, jamMulai);
            ps.setTime(4, jamMulai);
            ps.setTime(5, jamMulai);
            ps.setInt(6, durasi);
            ps.setTime(7, jamMulai);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Menambahkan booking baru ke database
    public boolean insert(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (id_lapangan, id_pelanggan, tanggal, jam_mulai, durasi, total_harga, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, booking.getIdLapangan());
            ps.setInt(2, booking.getIdPelanggan());
            ps.setDate(3, booking.getTanggal());
            ps.setTime(4, booking.getJamMulai());
            ps.setInt(5, booking.getDurasi());
            ps.setDouble(6, booking.getTotalHarga());
            ps.setString(7, booking.getStatus());

            int affected = ps.executeUpdate();

            // Set ID dari auto-generated key
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        booking.setIdBooking(keys.getInt(1));
                    }
                }
            }

            return affected > 0;
        }
    }

    // Mengupdate data booking di database
    public boolean update(Booking booking) throws SQLException {
        String sql = "UPDATE booking SET id_lapangan = ?, id_pelanggan = ?, tanggal = ?, " +
                     "jam_mulai = ?, durasi = ?, total_harga = ?, status = ? WHERE id_booking = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, booking.getIdLapangan());
            ps.setInt(2, booking.getIdPelanggan());
            ps.setDate(3, booking.getTanggal());
            ps.setTime(4, booking.getJamMulai());
            ps.setInt(5, booking.getDurasi());
            ps.setDouble(6, booking.getTotalHarga());
            ps.setString(7, booking.getStatus());
            ps.setInt(8, booking.getIdBooking());

            return ps.executeUpdate() > 0;
        }
    }

    // Mengupdate status booking saja
    public boolean updateStatus(int idBooking, String status) throws SQLException {
        String sql = "UPDATE booking SET status = ? WHERE id_booking = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, idBooking);

            return ps.executeUpdate() > 0;
        }
    }

    // Menghapus data booking dari database berdasarkan ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM booking WHERE id_booking = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Menghitung total booking hari ini (untuk Dashboard)
    public int countTodayBookings() throws SQLException {
        String sql = "SELECT COUNT(*) FROM booking WHERE tanggal = CURRENT_DATE AND status != 'Cancelled'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Helper method: Membuat objek Booking dari ResultSet
    private Booking createBookingFromResultSet(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id_booking"),
                rs.getInt("id_lapangan"),
                rs.getInt("id_pelanggan"),
                rs.getDate("tanggal"),
                rs.getTime("jam_mulai"),
                rs.getInt("durasi"),
                rs.getDouble("total_harga"),
                rs.getString("status")
        );
    }
}
