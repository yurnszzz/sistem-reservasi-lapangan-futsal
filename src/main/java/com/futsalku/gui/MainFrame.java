package com.futsalku.gui;

import javax.swing.*;
import java.awt.*;

// MainFrame — Frame utama aplikasi FutsalKu
// Menggunakan Java Swing dengan sidebar navigasi dan CardLayout
// PIC: Hasan (Hasan Shofiyyur Rahman)
public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton activeButton;

    // Warna tema
    private static final Color SIDEBAR_BG = new Color(30, 41, 59);       // dark blue-gray
    private static final Color SIDEBAR_HOVER = new Color(51, 65, 85);    // lighter
    private static final Color SIDEBAR_ACTIVE = new Color(37, 99, 235);  // blue accent
    private static final Color CONTENT_BG = new Color(245, 245, 250);    // light gray
    private static final Color TEXT_LIGHT = new Color(226, 232, 240);    // light text
    private static final Color TITLE_COLOR = new Color(255, 255, 255);   // white

    public MainFrame() {
        setTitle("FutsalKu — Sistem Reservasi Lapangan Futsal");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar (navigasi kiri)
        add(createSidebar(), BorderLayout.WEST);

        // Content area (panel utama)
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        // Tambahkan semua panel ke CardLayout
        contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new LapanganPanel(), "lapangan");
        contentPanel.add(new PelangganPanel(), "pelanggan");
        contentPanel.add(new BookingPanel(), "booking");
        contentPanel.add(new PembayaranPanel(), "pembayaran");
        contentPanel.add(new RiwayatPanel(), "riwayat");

        add(contentPanel, BorderLayout.CENTER);

        // Tampilkan dashboard di awal
        cardLayout.show(contentPanel, "dashboard");
    }

    // Membuat sidebar navigasi
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Header / Logo area
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(SIDEBAR_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        headerPanel.setMaximumSize(new Dimension(220, 90));
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel("FutsalKu");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Reservasi Lapangan");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(subtitleLabel);
        sidebar.add(headerPanel);

        // Separator
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(220, 1));
        sep.setForeground(new Color(51, 65, 85));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(15));

        // Menu label
        JLabel menuLabel = new JLabel("   MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        menuLabel.setForeground(new Color(100, 116, 139));
        menuLabel.setMaximumSize(new Dimension(220, 20));
        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(8));

        // Tombol navigasi
        JButton btnDashboard  = createNavButton("Dashboard",    "dashboard");
        JButton btnLapangan   = createNavButton("Lapangan",     "lapangan");
        JButton btnPelanggan  = createNavButton("Pelanggan",    "pelanggan");
        JButton btnBooking    = createNavButton("Booking",      "booking");
        JButton btnPembayaran = createNavButton("Pembayaran",   "pembayaran");
        JButton btnRiwayat    = createNavButton("Riwayat",      "riwayat");

        sidebar.add(btnDashboard);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(btnLapangan);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(btnPelanggan);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(btnBooking);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(btnPembayaran);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(btnRiwayat);

        // Push sisa ruang ke bawah
        sidebar.add(Box.createVerticalGlue());

        // Footer info
        JLabel footerLabel = new JLabel("   v1.0 — Kelompok 5");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footerLabel.setForeground(new Color(100, 116, 139));
        footerLabel.setMaximumSize(new Dimension(220, 30));
        sidebar.add(footerLabel);
        sidebar.add(Box.createVerticalStrut(15));

        // Set default active
        setActiveButton(btnDashboard);

        return sidebar;
    }

    // Membuat tombol navigasi sidebar
    private JButton createNavButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(TEXT_LIGHT);
        btn.setBackground(SIDEBAR_BG);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(220, 42));
        btn.setPreferredSize(new Dimension(220, 42));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 10));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(SIDEBAR_HOVER);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(SIDEBAR_BG);
                }
            }
        });

        // Klik navigasi
        btn.addActionListener(e -> {
            cardLayout.show(contentPanel, cardName);
            setActiveButton(btn);
        });

        return btn;
    }

    // Set tombol aktif (highlight)
    private void setActiveButton(JButton btn) {
        if (activeButton != null) {
            activeButton.setBackground(SIDEBAR_BG);
            activeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        activeButton = btn;
        activeButton.setBackground(SIDEBAR_ACTIVE);
        activeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
}
