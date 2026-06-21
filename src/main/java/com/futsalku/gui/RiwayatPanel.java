package com.futsalku.gui;

import com.futsalku.dao.BookingDAO;
import com.futsalku.dao.LapanganDAO;
import com.futsalku.dao.PelangganDAO;
import com.futsalku.model.Booking;
import com.futsalku.model.Lapangan;
import com.futsalku.model.Pelanggan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// RiwayatPanel — Panel riwayat booking (Masterpiece Version)
// Menampilkan relasi data lengkap (Nama, bukan sekadar ID) dan Visualisasi Status
// PIC: Muhammad Akbar Al Islami
public class RiwayatPanel extends JPanel {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final PelangganDAO pelangganDAO = new PelangganDAO();
    private final LapanganDAO lapanganDAO = new LapanganDAO();

    private JTable tblRiwayat;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> rowSorter;

    @SuppressWarnings("deprecation")
    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public RiwayatPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadRiwayat();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Judul
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Riwayat Reservasi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 41, 59));
        
        JLabel lblSubtitle = new JLabel("Daftar seluruh histori booking lapangan futsal");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 116, 139));
        
        textPanel.add(lblTitle);
        textPanel.add(lblSubtitle);
        panel.add(textPanel, BorderLayout.WEST);

        // Area Pencarian & Refresh
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(200, 35));
        
        // Fitur Live Search
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        JButton btnRefresh = new JButton("Refresh Data");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(37, 99, 235));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(120, 35));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadRiwayat();
        });

        actionPanel.add(new JLabel("Pencarian: "));
        actionPanel.add(txtSearch);
        actionPanel.add(btnRefresh);

        panel.add(actionPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        String[] columns = {"ID", "Tanggal", "Jam", "Nama Pelanggan", "Lapangan", "Durasi", "Total Biaya", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tblRiwayat = new JTable(tableModel);
        tblRiwayat.setRowHeight(35);
        tblRiwayat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblRiwayat.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblRiwayat.getTableHeader().setBackground(new Color(241, 245, 249));

        // Sorter untuk fitur pencarian
        rowSorter = new TableRowSorter<>(tableModel);
        tblRiwayat.setRowSorter(rowSorter);

        // Lebar Kolom Proporsional
        tblRiwayat.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tblRiwayat.getColumnModel().getColumn(1).setPreferredWidth(100); // Tanggal
        tblRiwayat.getColumnModel().getColumn(2).setPreferredWidth(60); // Jam
        tblRiwayat.getColumnModel().getColumn(3).setPreferredWidth(180); // Pelanggan
        tblRiwayat.getColumnModel().getColumn(4).setPreferredWidth(150); // Lapangan
        tblRiwayat.getColumnModel().getColumn(5).setPreferredWidth(80); // Durasi
        tblRiwayat.getColumnModel().getColumn(6).setPreferredWidth(120); // Biaya
        tblRiwayat.getColumnModel().getColumn(7).setPreferredWidth(100); // Status

        // Custom Cell Renderer untuk Status Booking
        tblRiwayat.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 12));
                
                String status = (value != null) ? value.toString() : "";
                switch (status) {
                    case "Done":
                        setForeground(new Color(16, 185, 129)); // Hijau
                        break;
                    case "Confirmed":
                        setForeground(new Color(37, 99, 235)); // Biru
                        break;
                    case "Pending":
                        setForeground(new Color(245, 158, 11)); // Oranye
                        break;
                    case "Cancelled":
                        setForeground(new Color(220, 38, 38)); // Merah
                        break;
                    default:
                        setForeground(Color.BLACK);
                }
                return c;
            }
        });

        wrapper.add(new JScrollPane(tblRiwayat), BorderLayout.CENTER);
        return wrapper;
    }

    private void filterTable() {
        String text = txtSearch.getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            // Filter berdasarkan semua kolom, case-insensitive
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void loadRiwayat() {
        tableModel.setRowCount(0);
        try {
            List<Booking> list = bookingDAO.getAll();
            for (Booking b : list) {
                // Konversi ID ke Nama Asli untuk UX yang lebih baik
                Pelanggan p = pelangganDAO.getById(b.getIdPelanggan());
                Lapangan l = lapanganDAO.getById(b.getIdLapangan());
                
                String namaPelanggan = (p != null) ? p.getNama() : "ID: " + b.getIdPelanggan();
                String namaLapangan = (l != null) ? l.getNama() : "ID: " + b.getIdLapangan();

                tableModel.addRow(new Object[]{
                        b.getIdBooking(),
                        b.getTanggal(),
                        b.getJamMulai().toString().substring(0, 5),
                        namaPelanggan,
                        namaLapangan,
                        b.getDurasi() + " Jam",
                        rupiahFormat.format(b.getTotalHarga()),
                        b.getStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat riwayat: " + e.getMessage());
        }
    }
}