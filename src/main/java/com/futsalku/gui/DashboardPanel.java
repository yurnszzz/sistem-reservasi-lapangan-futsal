package com.futsalku.gui;

import com.futsalku.dao.BookingDAO;
import com.futsalku.dao.LapanganDAO;
import com.futsalku.dao.PembayaranDAO;
import com.futsalku.model.Booking;
import com.futsalku.model.Lapangan;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// DashboardPanel — Panel ringkasan statistik dan grafik (Masterpiece Version)
// PIC: Rifqi Afif Zhain & Hasan Shofiyyur Rahman
public class DashboardPanel extends JPanel {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final LapanganDAO lapanganDAO = new LapanganDAO();
    private final PembayaranDAO pembayaranDAO = new PembayaranDAO();

    private JLabel lblTotalBooking, lblLapanganTersedia, lblPendapatan;
    private DashboardChart dashboardChart;

    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public DashboardPanel() {
        rupiahFormat.setMaximumFractionDigits(0);
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 1. HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Dashboard Hari Ini");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(30, 41, 59));
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // 2. KARTU STATISTIK (CARDS)
        JPanel cardContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        cardContainer.setOpaque(false);
        cardContainer.setPreferredSize(new Dimension(900, 110)); // Ditambah ke 900px agar lebih lega

        lblTotalBooking = createStatLabel("0");
        lblLapanganTersedia = createStatLabel("0");
        lblPendapatan = createStatLabel("Rp 0");

        // Menggunakan emoji dengan unicode escape agar aman dari bug encoding compiler Windows (Cp1252)
        cardContainer.add(createCard("Total Booking Aktif", lblTotalBooking, new Color(59, 130, 246), "\uD83D\uDCC5")); // 📅 Calendar
        cardContainer.add(createCard("Lapangan Tersedia", lblLapanganTersedia, new Color(16, 185, 129), "\u26BD")); // ⚽ Soccer Ball
        cardContainer.add(createCard("Pendapatan Lunas", lblPendapatan, new Color(245, 158, 11), "\uD83D\uDCB0")); // 💰 Money Bag

        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cardWrapper.setOpaque(false);
        cardWrapper.add(cardContainer);

        // 3. GRAFIK (CHART)
        dashboardChart = new DashboardChart();

        JPanel centerWrapper = new JPanel(new BorderLayout(0, 25)); // Jarak vertikal antara kartu dan grafik
        centerWrapper.setOpaque(false);
        centerWrapper.add(cardWrapper, BorderLayout.NORTH);
        centerWrapper.add(dashboardChart, BorderLayout.CENTER);
        
        add(centerWrapper, BorderLayout.CENTER);

        // 4. INISIALISASI DATA
        loadDataStatistik();
    }

    private JPanel createCard(String title, JLabel statLabel, Color accentColor, String initialText) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(15, 18, 15, 18) // Mengurangi padding agar muat lebih banyak teks
        ));

        // Panel Teks (Title di atas, Angka di bawah)
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 2, 0));
        textPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(148, 163, 184)); // Slate 400

        textPanel.add(lblTitle);
        textPanel.add(statLabel);

        // Icon bulat dengan latar transparan 10% dan emoji di tengahnya
        JLabel lblIcon = new JLabel(initialText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 25));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        // Menggunakan font Segoe UI Emoji untuk rendering emoji yang sempurna
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        lblIcon.setForeground(accentColor);
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setPreferredSize(new Dimension(50, 50));

        card.add(textPanel, BorderLayout.CENTER);
        card.add(lblIcon, BorderLayout.EAST);

        return card;
    }

    private JLabel createStatLabel(String defaultText) {
        JLabel lbl = new JLabel(defaultText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Diubah dari 26px ke 22px agar pas di kolom kecil
        lbl.setForeground(new Color(30, 41, 59));
        return lbl;
    }

    private void loadDataStatistik() {
        try {
            // Load angka statistik utama
            int bookings = bookingDAO.countTodayBookings();
            List<Lapangan> availableFields = lapanganDAO.getAvailable();
            int lapangan = availableFields.size();
            double pendapatan = pembayaranDAO.getTotalPendapatan();

            lblTotalBooking.setText(String.valueOf(bookings));
            lblLapanganTersedia.setText(String.valueOf(lapangan));
            lblPendapatan.setText(rupiahFormat.format(pendapatan));

            // Load data untuk grafik booking per lapangan
            List<Lapangan> lapanganList = lapanganDAO.getAll();
            List<Booking> bookingList = bookingDAO.getAll();

            int[] bookingCounts = new int[lapanganList.size()];
            String[] fieldLabels = new String[lapanganList.size()];

            for (int i = 0; i < lapanganList.size(); i++) {
                Lapangan lap = lapanganList.get(i);
                fieldLabels[i] = lap.getNama();
                
                final int lapId = lap.getIdLapangan();
                bookingCounts[i] = (int) bookingList.stream()
                        .filter(b -> b.getIdLapangan() == lapId && !b.getStatus().equals("Cancelled"))
                        .count();
            }

            dashboardChart.setData(bookingCounts, fieldLabels);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat statistik database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshData() {
        loadDataStatistik();
    }

    // ================= CUSTOM CHART COMPONENT ================= //
    private class DashboardChart extends JPanel {
        private int[] data = {};
        private String[] labels = {};
        private final Color barColor = new Color(59, 130, 246); // Blue Accent

        public DashboardChart() {
            setPreferredSize(new Dimension(0, 300));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                    BorderFactory.createEmptyBorder(25, 25, 25, 25)
            ));
        }

        public void setData(int[] newData, String[] newLabels) {
            this.data = newData;
            this.labels = newLabels;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int paddingLeftRight = 60;
            int paddingTopBottom = 50;
            int chartWidth = width - 2 * paddingLeftRight;
            int chartHeight = height - 2 * paddingTopBottom;

            // Judul Grafik
            g2.setColor(new Color(30, 41, 59));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString("Statistik Total Reservasi per Lapangan", paddingLeftRight, paddingTopBottom - 20);

            if (data == null || data.length == 0) {
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                g2.drawString("Tidak ada data untuk ditampilkan.", width / 2 - 100, height / 2);
                g2.dispose();
                return;
            }

            // Cari nilai tertinggi untuk kalkulasi skala Y
            int maxVal = 0;
            for (int val : data) {
                if (val > maxVal) maxVal = val;
            }
            if (maxVal == 0) maxVal = 1; // Cegah division by zero

            // Gambar Garis Grid Horisontal & Label Nilai Y
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            int numGridlines = 4;
            for (int i = 0; i <= numGridlines; i++) {
                int y = paddingTopBottom + chartHeight - (i * chartHeight / numGridlines);
                
                // Garis putus-putus abu-abu
                g2.setColor(new Color(241, 245, 249));
                g2.drawLine(paddingLeftRight, y, paddingLeftRight + chartWidth, y);
                
                // Label Y
                g2.setColor(new Color(148, 163, 184));
                int labelVal = (int) Math.round((double) i * maxVal / numGridlines);
                g2.drawString(String.valueOf(labelVal), paddingLeftRight - 30, y + 4);
            }

            // Gambar Batang Bar Chart
            int numBars = data.length;
            int barGap = 35;
            int totalGapWidth = barGap * (numBars + 1);
            int barWidth = (chartWidth - totalGapWidth) / numBars;

            // Batasi barWidth agar tidak terlalu gemuk pada layar lebar
            if (barWidth > 70) {
                barWidth = 70;
                // Selaraskan jarak horizontal agar tetap di tengah
                int adjustedWidth = (barWidth + barGap) * numBars + barGap;
                paddingLeftRight = (width - adjustedWidth) / 2;
            }

            for (int i = 0; i < numBars; i++) {
                int x = paddingLeftRight + barGap + i * (barWidth + barGap);
                int barValHeight = (int) (((double) data[i] / maxVal) * chartHeight);
                int y = paddingTopBottom + chartHeight - barValHeight;

                // Menggambar batang dengan sudut atas bulat
                g2.setColor(barColor);
                g2.fillRoundRect(x, y, barWidth, barValHeight, 10, 10);
                
                // Ratakan bagian bawah batang agar menempel garis dasar
                if (barValHeight > 5) {
                    g2.fillRect(x, y + 5, barWidth, barValHeight - 5);
                }

                // Angka value di atas batang
                g2.setColor(new Color(71, 85, 105));
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                String valStr = String.valueOf(data[i]);
                int valWidth = g2.getFontMetrics().stringWidth(valStr);
                g2.drawString(valStr, x + (barWidth - valWidth) / 2, y - 6);

                // Label X (Nama Lapangan)
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                g2.setColor(new Color(100, 116, 139));
                String label = labels[i];
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, x + (barWidth - labelWidth) / 2, paddingTopBottom + chartHeight + 20);
            }

            // Garis Dasar Chart (X Axis)
            g2.setColor(new Color(203, 213, 225));
            g2.drawLine(paddingLeftRight, paddingTopBottom + chartHeight, paddingLeftRight + chartWidth, paddingTopBottom + chartHeight);

            g2.dispose();
        }
    }
}