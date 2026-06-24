package com.futsalku.gui;

import com.futsalku.dao.BookingDAO;
import com.futsalku.dao.LapanganDAO;
import com.futsalku.dao.PelangganDAO;
import com.futsalku.model.Booking;
import com.futsalku.model.Lapangan;
import com.futsalku.model.Pelanggan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// BookingPanel — Panel reservasi lapangan dengan form input dan tabel data
// PIC: Hasan (Hasan Shofiyyur Rahman)
public class BookingPanel extends JPanel {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final LapanganDAO lapanganDAO = new LapanganDAO();
    private final PelangganDAO pelangganDAO = new PelangganDAO();

    // Komponen form
    private JComboBox<String> cmbLapangan;
    private JComboBox<String> cmbPelanggan;
    private JComboBox<Integer> cmbTanggalHari;
    private JComboBox<String> cmbTanggalBulan;
    private JComboBox<Integer> cmbTanggalTahun;
    private JComboBox<String> cmbJamMulai;
    private JSpinner spnDurasi;
    private JLabel lblTotalHarga;

    // Tabel
    private JTable tblBooking;
    private DefaultTableModel tableModel;

    // Data cache
    private List<Lapangan> lapanganList;
    private List<Pelanggan> pelangganList;

    // Format Rupiah
    // @SuppressWarnings("deprecation")
    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public BookingPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(245, 245, 250));

        // Header
        add(createHeader(), BorderLayout.NORTH);

        // Split: Form (kiri) + Tabel (tengah)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(1);
        splitPane.setBorder(null);

        splitPane.setLeftComponent(createFormPanel());
        splitPane.setRightComponent(createTablePanel());

        add(splitPane, BorderLayout.CENTER);

        // Load data awal
        loadComboBoxData();
        refreshTable();
    }

    // Header panel
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(37, 99, 235));
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel title = new JLabel("Booking Lapangan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Kelola reservasi lapangan futsal");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(191, 219, 254));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.WEST);

        return header;
    }

    // Panel form input (sisi kiri)
    private JPanel createFormPanel() {
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);

        JLabel formTitle = new JLabel("Form Booking Baru");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(formTitle);
        form.add(Box.createVerticalStrut(20));

        // Lapangan
        form.add(createLabel("Pilih Lapangan"));
        cmbLapangan = new JComboBox<>();
        cmbLapangan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbLapangan.setMaximumSize(new Dimension(280, 32));
        cmbLapangan.setAlignmentX(Component.LEFT_ALIGNMENT);
        cmbLapangan.addActionListener(e -> updateTotalHarga());
        form.add(cmbLapangan);
        form.add(Box.createVerticalStrut(12));

        // Pelanggan
        form.add(createLabel("Pilih Pelanggan"));
        cmbPelanggan = new JComboBox<>();
        cmbPelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbPelanggan.setMaximumSize(new Dimension(280, 32));
        cmbPelanggan.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(cmbPelanggan);
        form.add(Box.createVerticalStrut(12));

        // Tanggal
        form.add(createLabel("Tanggal Booking (Tgl - Bln - Thn)"));
        JPanel datePanel = new JPanel(new GridLayout(1, 3, 5, 0));
        datePanel.setOpaque(false);
        datePanel.setMaximumSize(new Dimension(280, 32));
        datePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Dropdown Tanggal (1-31)
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) days[i] = i + 1;
        cmbTanggalHari = new JComboBox<>(days);
        cmbTanggalHari.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTanggalHari.setBackground(Color.WHITE);

        // Dropdown Bulan (01-12)
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        cmbTanggalBulan = new JComboBox<>(months);
        cmbTanggalBulan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTanggalBulan.setBackground(Color.WHITE);

        // Dropdown Tahun (Tahun saat ini + 5 tahun ke depan)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[6];
        for (int i = 0; i < 6; i++) years[i] = currentYear + i;
        cmbTanggalTahun = new JComboBox<>(years);
        cmbTanggalTahun.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTanggalTahun.setBackground(Color.WHITE);

        // Set default ke hari ini
        Calendar calToday = Calendar.getInstance();
        cmbTanggalHari.setSelectedItem(calToday.get(Calendar.DAY_OF_MONTH));
        cmbTanggalBulan.setSelectedIndex(calToday.get(Calendar.MONTH)); // Indeks 0 = "01"
        cmbTanggalTahun.setSelectedItem(calToday.get(Calendar.YEAR));

        datePanel.add(cmbTanggalHari);
        datePanel.add(cmbTanggalBulan);
        datePanel.add(cmbTanggalTahun);
        form.add(datePanel);
        form.add(Box.createVerticalStrut(12));

        // Jam Mulai
        form.add(createLabel("Jam Mulai"));
        cmbJamMulai = new JComboBox<>(new String[]{
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00", "22:00"
        });
        cmbJamMulai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbJamMulai.setMaximumSize(new Dimension(280, 32));
        cmbJamMulai.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(cmbJamMulai);
        form.add(Box.createVerticalStrut(12));

        // Durasi
        form.add(createLabel("Durasi (jam)"));
        spnDurasi = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        spnDurasi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spnDurasi.setMaximumSize(new Dimension(280, 32));
        spnDurasi.setAlignmentX(Component.LEFT_ALIGNMENT);
        spnDurasi.addChangeListener(e -> updateTotalHarga());
        form.add(spnDurasi);
        form.add(Box.createVerticalStrut(20));

        // Total Harga
        JPanel hargaPanel = new JPanel(new BorderLayout());
        hargaPanel.setBackground(new Color(240, 249, 255));
        hargaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(186, 230, 253), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        hargaPanel.setMaximumSize(new Dimension(280, 55));
        hargaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hargaLabel = new JLabel("Total Harga:");
        hargaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hargaLabel.setForeground(new Color(30, 64, 175));

        lblTotalHarga = new JLabel("Rp 0");
        lblTotalHarga.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalHarga.setForeground(new Color(30, 64, 175));

        hargaPanel.add(hargaLabel, BorderLayout.NORTH);
        hargaPanel.add(lblTotalHarga, BorderLayout.CENTER);
        form.add(hargaPanel);
        form.add(Box.createVerticalStrut(20));

        // Tombol aksi
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setMaximumSize(new Dimension(280, 40));
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedButton btnTambah = new RoundedButton("Tambah", new Color(37, 99, 235), Color.WHITE);
        btnTambah.addActionListener(e -> tambahBooking());

        RoundedButton btnHapus = new RoundedButton("Hapus", new Color(220, 38, 38), Color.WHITE);
        btnHapus.addActionListener(e -> hapusBooking());

        RoundedButton btnRefresh = new RoundedButton("Refresh", new Color(100, 116, 139), Color.WHITE);
        btnRefresh.addActionListener(e -> {
            loadComboBoxData();
            refreshTable();
        });

        btnPanel.add(btnTambah);
        btnPanel.add(btnHapus);
        btnPanel.add(btnRefresh);
        form.add(btnPanel);

        formWrapper.add(form, BorderLayout.NORTH);
        return formWrapper;
    }

    // Panel tabel booking (sisi kanan)
    private JPanel createTablePanel() {
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 15));

        JLabel tableTitle = new JLabel("  Daftar Booking");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tableWrapper.add(tableTitle, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID", "Lapangan", "Pelanggan", "Tanggal", "Jam", "Durasi", "Total Harga", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };

        tblBooking = new JTable(tableModel);
        TableStyleHelper.styleTable(tblBooking, new int[]{0, 4, 5, 7});

        // Atur lebar kolom
        tblBooking.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        tblBooking.getColumnModel().getColumn(1).setPreferredWidth(120);  // Lapangan
        tblBooking.getColumnModel().getColumn(2).setPreferredWidth(120);  // Pelanggan
        tblBooking.getColumnModel().getColumn(3).setPreferredWidth(90);   // Tanggal
        tblBooking.getColumnModel().getColumn(4).setPreferredWidth(60);   // Jam
        tblBooking.getColumnModel().getColumn(5).setPreferredWidth(50);   // Durasi
        tblBooking.getColumnModel().getColumn(6).setPreferredWidth(110);  // Total Harga
        tblBooking.getColumnModel().getColumn(7).setPreferredWidth(80);   // Status

        JScrollPane scrollPane = new JScrollPane(tblBooking);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        return tableWrapper;
    }

    // Load data lapangan & pelanggan ke ComboBox
    private void loadComboBoxData() {
        try {
            // Load lapangan
            cmbLapangan.removeAllItems();
            lapanganList = lapanganDAO.getAvailable();
            for (Lapangan lap : lapanganList) {
                String item = lap.getNama() + " (" + lap.getTipe() + ") - " +
                              rupiahFormat.format(lap.getHargaPerJam()) + "/jam";
                cmbLapangan.addItem(item);
            }

            // Load pelanggan
            cmbPelanggan.removeAllItems();
            pelangganList = pelangganDAO.getAll();
            for (Pelanggan pel : pelangganList) {
                String member = pel.isMember() ? " [Member]" : "";
                cmbPelanggan.addItem(pel.getNama() + " - " + pel.getNoTelp() + member);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Refresh tabel booking
    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Booking> bookings = bookingDAO.getAll();
            for (Booking b : bookings) {
                // Ambil nama lapangan dan pelanggan
                String namaLapangan = "-";
                String namaPelanggan = "-";

                Lapangan lap = lapanganDAO.getById(b.getIdLapangan());
                if (lap != null) namaLapangan = lap.getNama() + " (" + lap.getTipe() + ")";

                Pelanggan pel = pelangganDAO.getById(b.getIdPelanggan());
                if (pel != null) namaPelanggan = pel.getNama();

                tableModel.addRow(new Object[]{
                        b.getIdBooking(),
                        namaLapangan,
                        namaPelanggan,
                        b.getTanggal(),
                        b.getJamMulai().toString().substring(0, 5), // HH:mm
                        b.getDurasi() + " jam",
                        rupiahFormat.format(b.getTotalHarga()),
                        b.getStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data booking: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hitung dan update total harga otomatis
    private void updateTotalHarga() {
        int idx = cmbLapangan.getSelectedIndex();
        if (lapanganList != null && idx >= 0 && idx < lapanganList.size()) {
            Lapangan lap = lapanganList.get(idx);
            int durasi = (int) spnDurasi.getValue();
            double total = lap.hitungHarga(durasi); // Polymorphism!
            lblTotalHarga.setText(rupiahFormat.format(total));
        }
    }

    // Aksi: Tambah booking baru
    private void tambahBooking() {
        try {
            // Validasi
            int lapIdx = cmbLapangan.getSelectedIndex();
            int pelIdx = cmbPelanggan.getSelectedIndex();

            if (lapIdx < 0) {
                JOptionPane.showMessageDialog(this, "Pilih lapangan terlebih dahulu!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (pelIdx < 0) {
                JOptionPane.showMessageDialog(this, "Pilih pelanggan terlebih dahulu!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Dapatkan tanggal dari Dropdown (format YYYY-MM-DD)
            int hari = (int) cmbTanggalHari.getSelectedItem();
            String bulan = (String) cmbTanggalBulan.getSelectedItem();
            int tahun = (int) cmbTanggalTahun.getSelectedItem();
            
            String tanggalStr = String.format("%d-%s-%02d", tahun, bulan, hari);
            Date tanggal;
            try {
                tanggal = Date.valueOf(tanggalStr);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Tanggal yang dipilih tidak valid (misal: 31 Februari)!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Parse jam
            String jamStr = (String) cmbJamMulai.getSelectedItem();
            Time jamMulai = Time.valueOf(jamStr + ":00");

            int durasi = (int) spnDurasi.getValue();
            Lapangan lap = lapanganList.get(lapIdx);
            Pelanggan pel = pelangganList.get(pelIdx);

            // Cek double booking
            if (bookingDAO.isLapanganBooked(lap.getIdLapangan(), tanggal, jamMulai, durasi)) {
                JOptionPane.showMessageDialog(this,
                        "Lapangan sudah dibooking pada waktu tersebut!\nSilakan pilih waktu lain.",
                        "Double Booking", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Hitung total harga (Polymorphism!)
            double totalHarga = lap.hitungHarga(durasi);

            // Buat objek booking
            Booking booking = new Booking();
            booking.setIdLapangan(lap.getIdLapangan());
            booking.setIdPelanggan(pel.getIdPelanggan());
            booking.setTanggal(tanggal);
            booking.setJamMulai(jamMulai);
            booking.setDurasi(durasi);
            booking.setTotalHarga(totalHarga);
            booking.setStatus("Confirmed");

            // Insert ke database
            if (bookingDAO.insert(booking)) {
                JOptionPane.showMessageDialog(this,
                        "Booking berhasil ditambahkan!\n" +
                        "ID Booking: " + booking.getIdBooking() + "\n" +
                        "Total: " + rupiahFormat.format(totalHarga),
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Aksi: Hapus booking yang dipilih
    private void hapusBooking() {
        int selectedRow = tblBooking.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih booking yang ingin dihapus!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus booking ID " + id + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (bookingDAO.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Booking berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus booking!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal menghapus: " + e.getMessage() +
                        "\nPastikan tidak ada pembayaran yang terkait.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helper: Buat label form
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(71, 85, 105));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return label;
    }

    // Helper: Buat tombol aksi
   /*  private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(80, 32));
        return btn;
    } */

    public void refreshData() {
        loadComboBoxData();
        refreshTable();
    }
}
