package com.futsalku.gui;

import com.futsalku.dao.BookingDAO;
import com.futsalku.dao.LapanganDAO;
import com.futsalku.dao.PembayaranDAO;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

// DashboardPanel — Panel ringkasan statistik (Masterpiece Version)
// PIC: Rifqi Afif Zhain
public class DashboardPanel extends JPanel {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final LapanganDAO lapanganDAO = new LapanganDAO();
    private final PembayaranDAO pembayaranDAO = new PembayaranDAO();

    private JLabel lblTotalBooking, lblLapanganTersedia, lblPendapatan;

    @SuppressWarnings("deprecation")
    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public DashboardPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 1. HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Dashboard Hari Ini");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(30, 41, 59));
        
        JButton btnRefresh = new JButton("Perbarui Data");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(37, 99, 235));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setPreferredSize(new Dimension(150, 40));
        btnRefresh.addActionListener(e -> loadDataStatistik());
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnRefresh, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // 2. KARTU STATISTIK (CARDS)
        JPanel cardContainer = new JPanel(new GridLayout(1, 3, 25, 0));
        cardContainer.setOpaque(false);

        lblTotalBooking = createStatLabel("0");
        lblLapanganTersedia = createStatLabel("0");
        lblPendapatan = createStatLabel("Rp 0");

        cardContainer.add(createCard("Total Booking Aktif", lblTotalBooking, new Color(59, 130, 246))); // Biru
        cardContainer.add(createCard("Lapangan Tersedia", lblLapanganTersedia, new Color(16, 185, 129))); // Hijau
        cardContainer.add(createCard("Pendapatan Lunas (Total)", lblPendapatan, new Color(245, 158, 11))); // Oranye

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(cardContainer, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

        // 3. INISIALISASI DATA
        loadDataStatistik();
    }

    private JPanel createCard(String title, JLabel statLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(100, 116, 139));

        // Garis aksen di sisi kiri untuk estetika modern
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setPreferredSize(new Dimension(6, 0));

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(lblTitle, BorderLayout.NORTH);
        content.add(statLabel, BorderLayout.CENTER);

        card.add(accentLine, BorderLayout.WEST);
        card.add(content, BorderLayout.CENTER);

        return card;
    }

    private JLabel createStatLabel(String defaultText) {
        JLabel lbl = new JLabel(defaultText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Ukuran font besar untuk angka
        lbl.setForeground(new Color(30, 41, 59));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        return lbl;
    }

    private void loadDataStatistik() {
        try {
            int bookings = bookingDAO.countTodayBookings();
            int lapangan = lapanganDAO.getAvailable().size();
            double pendapatan = pembayaranDAO.getTotalPendapatan();

            lblTotalBooking.setText(String.valueOf(bookings));
            lblLapanganTersedia.setText(String.valueOf(lapangan));
            lblPendapatan.setText(rupiahFormat.format(pendapatan));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat statistik database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}