package com.futsalku.gui;

import com.futsalku.dao.BookingDAO;
import com.futsalku.dao.PembayaranDAO;
import com.futsalku.model.Booking;
import com.futsalku.model.Pembayaran;
import com.futsalku.model.PembayaranCash;
import com.futsalku.model.PembayaranTransfer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// PembayaranPanel — Panel proses pembayaran (Masterpiece Version)
// Menampilkan UX Kasir, Polimorfisme, dan Simulasi Cetak Struk
// PIC: Muhammad Akbar Al Islami & Rifqi Afif Zhain
public class PembayaranPanel extends JPanel {

    private final PembayaranDAO pembayaranDAO = new PembayaranDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    // Komponen Form Kasir
    private JTextField txtIdBooking, txtTagihan, txtJumlahBayar;
    private JComboBox<String> cmbMetode;
    
    // Komponen Tabel
    private JTable tblAntrean, tblRiwayat;
    private DefaultTableModel modelAntrean, modelRiwayat;
    private List<Booking> listAntrean;

    // Formatters
    @SuppressWarnings("deprecation")
    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

    public PembayaranPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. HEADER
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. SPLIT PANE UTAMA
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450); // Area kiri (kasir) lebih lebar
        splitPane.setDividerSize(1);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        splitPane.setLeftComponent(createKasirPanel());
        splitPane.setRightComponent(createRiwayatPanel());

        add(splitPane, BorderLayout.CENTER);

        // 3. LOAD DATA
        refreshAllData();
    }

    // ================= UI BUILDERS ================= //

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel("Kasir & Pembayaran");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 41, 59));
        
        JLabel lblSubtitle = new JLabel("Pilih antrean booking, proses pembayaran, dan cetak struk");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 116, 139));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(lblTitle);
        textPanel.add(lblSubtitle);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JPanel createKasirPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 15));
        wrapper.setOpaque(false);

        // BAGIAN ATAS: Tabel Antrean (Booking yang belum dibayar)
        JPanel pnlAntrean = new JPanel(new BorderLayout());
        pnlAntrean.setBackground(Color.WHITE);
        pnlAntrean.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        pnlAntrean.setPreferredSize(new Dimension(0, 200));

        JLabel lblAntrean = new JLabel(" Antrean Belum Dibayar");
        lblAntrean.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAntrean.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pnlAntrean.add(lblAntrean, BorderLayout.NORTH);

        String[] colAntrean = {"ID", "Tanggal", "Jam", "Tagihan"};
        modelAntrean = new DefaultTableModel(colAntrean, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblAntrean = new JTable(modelAntrean);
        tblAntrean.setRowHeight(25);
        tblAntrean.getTableHeader().setBackground(new Color(241, 245, 249));
        tblAntrean.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblAntrean.getSelectedRow() != -1) {
                int row = tblAntrean.getSelectedRow();
                Booking b = listAntrean.get(row);
                txtIdBooking.setText(String.valueOf(b.getIdBooking()));
                txtTagihan.setText(rupiahFormat.format(b.getTotalHarga()));
                
                // Set default jumlah bayar sama dengan tagihan (agar kasir tidak capek ngetik)
                txtJumlahBayar.setText(String.valueOf((long)b.getTotalHarga()));
            }
        });
        pnlAntrean.add(new JScrollPane(tblAntrean), BorderLayout.CENTER);
        wrapper.add(pnlAntrean, BorderLayout.NORTH);

        // BAGIAN BAWAH: Form Pembayaran
        JPanel pnlForm = new JPanel(new BorderLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel formContent = new JPanel();
        formContent.setLayout(new BoxLayout(formContent, BoxLayout.Y_AXIS));
        formContent.setBackground(Color.WHITE);

        txtIdBooking = addReadOnlyField(formContent, "ID Booking Terpilih");
        txtTagihan = addReadOnlyField(formContent, "Total Tagihan");

        formContent.add(createLabel("Jumlah Bayar (Rp)"));
        txtJumlahBayar = new JTextField();
        txtJumlahBayar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtJumlahBayar.setForeground(new Color(16, 185, 129));
        txtJumlahBayar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formContent.add(txtJumlahBayar);
        formContent.add(Box.createVerticalStrut(15));

        formContent.add(createLabel("Metode Pembayaran"));
        cmbMetode = new JComboBox<>(new String[]{"Cash", "Transfer"});
        cmbMetode.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbMetode.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cmbMetode.setBackground(Color.WHITE);
        formContent.add(cmbMetode);
        formContent.add(Box.createVerticalStrut(25));

        JButton btnProses = new JButton("Proses & Cetak Struk");
        btnProses.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnProses.setBackground(new Color(37, 99, 235));
        btnProses.setForeground(Color.WHITE);
        btnProses.setFocusPainted(false);
        btnProses.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProses.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnProses.addActionListener(e -> prosesPembayaran());
        formContent.add(btnProses);

        pnlForm.add(formContent, BorderLayout.NORTH);
        wrapper.add(pnlForm, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createRiwayatPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 15, 0, 0),
                BorderFactory.createLineBorder(new Color(226, 232, 240))
        ));

        JLabel lblRiwayat = new JLabel(" Riwayat Transaksi");
        lblRiwayat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRiwayat.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        wrapper.add(lblRiwayat, BorderLayout.NORTH);

        String[] colRiwayat = {"ID Bayar", "ID Book", "Metode", "Jumlah", "Status"};
        modelRiwayat = new DefaultTableModel(colRiwayat, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblRiwayat = new JTable(modelRiwayat);
        tblRiwayat.setRowHeight(35);
        tblRiwayat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblRiwayat.getTableHeader().setBackground(new Color(241, 245, 249));

        // VISUALISASI DATA: Custom Cell Renderer untuk Status
        tblRiwayat.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 12));
                if ("Lunas".equals(value)) {
                    setForeground(new Color(16, 185, 129)); // Hijau
                } else {
                    setForeground(new Color(245, 158, 11)); // Oranye
                }
                return c;
            }
        });

        wrapper.add(new JScrollPane(tblRiwayat), BorderLayout.CENTER);
        return wrapper;
    }

    // ================= HELPER UI ================= //

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(71, 85, 105));
        return lbl;
    }

    private JTextField addReadOnlyField(JPanel parent, String label) {
        parent.add(createLabel(label));
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txt.setBackground(new Color(241, 245, 249)); // Warna abu-abu (readonly)
        txt.setEditable(false);
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        parent.add(txt);
        parent.add(Box.createVerticalStrut(15));
        return txt;
    }

    // ================= LOGIKA OOP & DATABASE ================= //

    private void refreshAllData() {
        try {
            // 1. Refresh Tabel Antrean (Booking yang statusnya belum 'Done' dan belum ada di tabel pembayaran)
            modelAntrean.setRowCount(0);
            listAntrean = new ArrayList<>();
            List<Booking> allBookings = bookingDAO.getAll();
            List<Pembayaran> allPembayaran = pembayaranDAO.getAll();

            for (Booking b : allBookings) {
                // Cek apakah booking ini sudah ada di tabel pembayaran
                boolean sudahDibayar = allPembayaran.stream().anyMatch(p -> p.getIdBooking() == b.getIdBooking());
                if (!sudahDibayar && !b.getStatus().equals("Cancelled")) {
                    listAntrean.add(b);
                    modelAntrean.addRow(new Object[]{
                            b.getIdBooking(),
                            b.getTanggal(),
                            b.getJamMulai().toString().substring(0,5),
                            rupiahFormat.format(b.getTotalHarga())
                    });
                }
            }

            // 2. Refresh Tabel Riwayat Pembayaran
            modelRiwayat.setRowCount(0);
            for (Pembayaran p : allPembayaran) {
                modelRiwayat.addRow(new Object[]{
                        p.getIdPembayaran(),
                        p.getIdBooking(),
                        p.getMetode(),
                        rupiahFormat.format(p.getJumlah()),
                        p.getStatus()
                });
            }

            // Reset Form
            txtIdBooking.setText("");
            txtTagihan.setText("");
            txtJumlahBayar.setText("");
            tblAntrean.clearSelection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void prosesPembayaran() {
        if (txtIdBooking.getText().isEmpty() || txtJumlahBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih antrean dan masukkan jumlah bayar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idBooking = Integer.parseInt(txtIdBooking.getText());
            double tagihanAsli = listAntrean.get(tblAntrean.getSelectedRow()).getTotalHarga();
            double jumlahBayar = Double.parseDouble(txtJumlahBayar.getText().trim());

            if (jumlahBayar < tagihanAsli) {
                JOptionPane.showMessageDialog(this, "Jumlah bayar kurang dari total tagihan!", "Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String metode = cmbMetode.getSelectedItem().toString();

            // PEMBUKTIAN POLIMORFISME TINGKAT TINGGI
            Pembayaran p;
            if (metode.equals("Cash")) {
                p = new PembayaranCash(); 
            } else {
                p = new PembayaranTransfer();
            }

            p.setIdBooking(idBooking);
            p.setJumlah(jumlahBayar);
            p.prosesTransaksi(); // Method Override tereksekusi!

            // Simpan ke database
            if (pembayaranDAO.insert(p)) {
                // Update status booking menjadi Done jika Lunas
                if (p.getStatus().equals("Lunas")) {
                    bookingDAO.updateStatus(idBooking, "Done");
                }
                
                // Tampilkan Struk (Pembuktian Abstraction/Printable)
                tampilkanStruk(p, tagihanAsli);
                
                // Segarkan layar
                refreshAllData();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nominal bayar harus berupa angka valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Simulasi Fitur Interface Printable
    private void tampilkanStruk(Pembayaran p, double tagihan) {
        StringBuilder struk = new StringBuilder();
        struk.append("====================================\n");
        struk.append("          FUTSALKU OFFICIAL         \n");
        struk.append("        BUKTI PEMBAYARAN SAH        \n");
        struk.append("====================================\n");
        struk.append("Waktu       : ").append(dateFormat.format(p.getTglBayar())).append("\n");
        struk.append("ID Bayar    : ").append(p.getIdPembayaran()).append("\n");
        struk.append("ID Booking  : ").append(p.getIdBooking()).append("\n");
        struk.append("Metode      : ").append(p.getMetode()).append("\n");
        struk.append("Status      : ").append(p.getStatus()).append("\n");
        struk.append("------------------------------------\n");
        struk.append("Total Biaya : ").append(rupiahFormat.format(tagihan)).append("\n");
        struk.append("Tunai/Bayar : ").append(rupiahFormat.format(p.getJumlah())).append("\n");
        
        if (p.getMetode().equals("Cash")) {
            double kembalian = p.getJumlah() - tagihan;
            struk.append("Kembalian   : ").append(rupiahFormat.format(kembalian)).append("\n");
        }
        
        struk.append("====================================\n");
        struk.append("  Terima Kasih Atas Kunjungan Anda! \n");

        JTextArea txtStruk = new JTextArea(struk.toString());
        txtStruk.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtStruk.setEditable(false);
        txtStruk.setBackground(new Color(250, 250, 250));
        txtStruk.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JOptionPane.showMessageDialog(this, txtStruk, "Cetak Struk Pembayaran", JOptionPane.PLAIN_MESSAGE);
    }
}