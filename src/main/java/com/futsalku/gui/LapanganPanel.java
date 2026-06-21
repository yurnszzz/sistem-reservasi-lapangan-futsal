package com.futsalku.gui;

import com.futsalku.dao.LapanganDAO;
import com.futsalku.model.Lapangan;
import com.futsalku.model.LapanganIndoor;
import com.futsalku.model.LapanganOutdoor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// LapanganPanel — Panel CRUD data lapangan
// PIC: Muhammad Akbar Al Islami & Rifqi Afif Zhain
public class LapanganPanel extends JPanel {

    private final LapanganDAO lapanganDAO = new LapanganDAO();

    // Komponen Form
    private JTextField txtId, txtNama, txtHarga, txtFasilitas;
    private JComboBox<String> cmbTipe, cmbStatus;

    // Komponen Tabel
    private JTable tblLapangan;
    private DefaultTableModel tableModel;

    // Format Rupiah
    @SuppressWarnings("deprecation")
    private final NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public LapanganPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. HEADER
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. SPLIT PANE (Form Kiri, Tabel Kanan)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(1);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        splitPane.setLeftComponent(createFormPanel());
        splitPane.setRightComponent(createTablePanel());

        add(splitPane, BorderLayout.CENTER);

        // 3. Load Data
        refreshTableData();
    }

    // ================= PABRIK KOMPONEN UI ================= //

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel("Manajemen Lapangan");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 41, 59));
        
        JLabel lblSubtitle = new JLabel("Kelola data lapangan beserta tipe dan harganya");
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

    private JPanel createFormPanel() {
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel formContent = new JPanel();
        formContent.setLayout(new BoxLayout(formContent, BoxLayout.Y_AXIS));
        formContent.setBackground(Color.WHITE);

        JLabel lblForm = new JLabel("Form Data Lapangan");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formContent.add(lblForm);
        formContent.add(Box.createVerticalStrut(20));

        txtId = new JTextField();
        txtId.setVisible(false); // Disembunyikan karena auto-increment

        txtNama = addFormField(formContent, "Nama Lapangan");
        
        // ComboBox Tipe
        formContent.add(createLabel("Tipe Lapangan"));
        cmbTipe = new JComboBox<>(new String[]{"Indoor", "Outdoor"});
        styleComboBox(cmbTipe);
        formContent.add(cmbTipe);
        formContent.add(Box.createVerticalStrut(15));

        txtHarga = addFormField(formContent, "Harga per Jam (Rp)");

        // ComboBox Status
        formContent.add(createLabel("Status"));
        cmbStatus = new JComboBox<>(new String[]{"Tersedia", "Tidak Tersedia"});
        styleComboBox(cmbStatus);
        formContent.add(cmbStatus);
        formContent.add(Box.createVerticalStrut(15));

        txtFasilitas = addFormField(formContent, "Fasilitas");

        // Area Tombol Aksi
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBackground(Color.WHITE);

        JButton btnSimpan = createButton("Simpan", new Color(37, 99, 235));
        JButton btnUbah = createButton("Ubah", new Color(245, 158, 11));
        JButton btnHapus = createButton("Hapus", new Color(220, 38, 38));
        JButton btnClear = createButton("Bersihkan", new Color(100, 116, 139));

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnClear.addActionListener(e -> clearForm());

        btnPanel.add(btnSimpan);
        btnPanel.add(btnUbah);
        btnPanel.add(btnHapus);
        btnPanel.add(btnClear);

        formContent.add(btnPanel);
        formWrapper.add(formContent, BorderLayout.NORTH);

        return formWrapper;
    }

    private JPanel createTablePanel() {
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        String[] columns = {"ID", "Nama Lapangan", "Tipe", "Harga/Jam", "Status", "Fasilitas"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLapangan = new JTable(tableModel);
        tblLapangan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblLapangan.setRowHeight(30);
        tblLapangan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblLapangan.getTableHeader().setBackground(new Color(241, 245, 249));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblLapangan.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblLapangan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblLapangan.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblLapangan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        // Event saat baris diklik
        tblLapangan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblLapangan.getSelectedRow() != -1) {
                int row = tblLapangan.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNama.setText(tableModel.getValueAt(row, 1).toString());
                cmbTipe.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                
                // Menghilangkan format rupiah saat dimasukkan kembali ke form input
                String hargaBersih = tableModel.getValueAt(row, 3).toString()
                        .replace("Rp", "").replace(".", "").replace(",00", "").trim();
                txtHarga.setText(hargaBersih);
                
                cmbStatus.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                txtFasilitas.setText(tableModel.getValueAt(row, 5).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblLapangan);
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        return tableWrapper;
    }

    // Helper UI Methods
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(71, 85, 105));
        return lbl;
    }

    private JTextField addFormField(JPanel parent, String labelName) {
        parent.add(createLabel(labelName));
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        parent.add(txt);
        parent.add(Box.createVerticalStrut(15));
        return txt;
    }

    private void styleComboBox(JComboBox<String> cmb) {
        cmb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cmb.setBackground(Color.WHITE);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ================= LOGIKA OOP & DATABASE (CRUD) ================= //

    private void refreshTableData() {
        tableModel.setRowCount(0);
        try {
            List<Lapangan> list = lapanganDAO.getAll();
            for (Lapangan l : list) {
                tableModel.addRow(new Object[]{
                        l.getIdLapangan(),
                        l.getNama(),
                        l.getTipe(),
                        rupiahFormat.format(l.getHargaPerJam()), // Format Rupiah
                        l.getStatus(),
                        l.getFasilitas()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void simpanData() {
        if (!validasiInput()) return;

        try {
            // PEMBUKTIAN POLIMORFISME: Instansiasi dinamis berdasarkan pilihan ComboBox
            Lapangan lapanganBaru;
            String tipeTerpilih = cmbTipe.getSelectedItem().toString();

            if (tipeTerpilih.equals("Indoor")) {
                lapanganBaru = new LapanganIndoor(); 
            } else {
                lapanganBaru = new LapanganOutdoor();
            }

            // Enkapsulasi: Menggunakan setter untuk mengisi data
            lapanganBaru.setNama(txtNama.getText().trim());
            lapanganBaru.setHargaPerJam(Double.parseDouble(txtHarga.getText().trim()));
            lapanganBaru.setStatus(cmbStatus.getSelectedItem().toString());
            lapanganBaru.setFasilitas(txtFasilitas.getText().trim());

            if (lapanganDAO.insert(lapanganBaru)) {
                JOptionPane.showMessageDialog(this, "Data Lapangan berhasil disimpan!");
                clearForm();
                refreshTableData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void ubahData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validasiInput()) return;

        try {
            // Polimorfisme: Regenerasi objek dengan identitas yang sesuai
            Lapangan lapanganUbah;
            if (cmbTipe.getSelectedItem().toString().equals("Indoor")) {
                lapanganUbah = new LapanganIndoor();
            } else {
                lapanganUbah = new LapanganOutdoor();
            }

            lapanganUbah.setIdLapangan(Integer.parseInt(txtId.getText()));
            lapanganUbah.setNama(txtNama.getText().trim());
            lapanganUbah.setHargaPerJam(Double.parseDouble(txtHarga.getText().trim()));
            lapanganUbah.setStatus(cmbStatus.getSelectedItem().toString());
            lapanganUbah.setFasilitas(txtFasilitas.getText().trim());

            if (lapanganDAO.update(lapanganUbah)) {
                JOptionPane.showMessageDialog(this, "Data Lapangan berhasil diperbarui!");
                clearForm();
                refreshTableData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void hapusData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus lapangan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (lapanganDAO.delete(Integer.parseInt(txtId.getText()))) {
                    JOptionPane.showMessageDialog(this, "Data Lapangan berhasil dihapus!");
                    clearForm();
                    refreshTableData();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data lapangan ini tidak bisa dihapus karena terkait dengan riwayat Booking!", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        cmbTipe.setSelectedIndex(0);
        txtHarga.setText("");
        cmbStatus.setSelectedIndex(0);
        txtFasilitas.setText("");
        tblLapangan.clearSelection();
    }

    private boolean validasiInput() {
        if (txtNama.getText().trim().isEmpty() || txtHarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan Harga Lapangan wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtHarga.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka valid (tanpa titik/koma)!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}