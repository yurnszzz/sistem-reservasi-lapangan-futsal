package com.futsalku.gui;

import com.futsalku.dao.PelangganDAO;
import com.futsalku.model.Pelanggan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// PelangganPanel — Panel CRUD data pelanggan
// PIC: Rifqi Afif Zhain (Apip)
public class PelangganPanel extends JPanel {

    private final PelangganDAO pelangganDAO = new PelangganDAO();

    // Komponen Form
    private JTextField txtId, txtNama, txtNoTelp, txtEmail;
    private JCheckBox chkMember;
    private JTextField txtSearch;

    // Komponen Tabel
    private JTable tblPelanggan;
    private DefaultTableModel tableModel;

    public PelangganPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. HEADER (Judul & Pencarian)
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. SPLIT PANE (Form di Kiri, Tabel di Kanan)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350); // Lebar form kiri
        splitPane.setDividerSize(1);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        splitPane.setLeftComponent(createFormPanel());
        splitPane.setRightComponent(createTablePanel());

        add(splitPane, BorderLayout.CENTER);

        // 3. Load Data Awal
        refreshTableData("");
    }

    // ================= PABRIK KOMPONEN UI ================= //

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel("Manajemen Pelanggan");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 41, 59));
        panel.add(lblTitle, BorderLayout.WEST);

        // Area Pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(200, 35));
        
        JButton btnCari = createButton("Cari", new Color(100, 116, 139));
        btnCari.addActionListener(e -> refreshTableData(txtSearch.getText().trim()));
        
        searchPanel.add(new JLabel("Cari: "));
        searchPanel.add(txtSearch);
        searchPanel.add(btnCari);

        panel.add(searchPanel, BorderLayout.EAST);
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

        JLabel lblForm = new JLabel("Form Data Pelanggan");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formContent.add(lblForm);
        formContent.add(Box.createVerticalStrut(20));

        // Field ID (Hidden/Read-only)
        txtId = new JTextField();
        txtId.setVisible(false); // Disembunyikan karena auto-increment

        // Pabrik Input Field
        txtNama = addFormField(formContent, "Nama Lengkap");
        txtNoTelp = addFormField(formContent, "Nomor Telepon");
        txtEmail = addFormField(formContent, "Alamat Email");

        // Checkbox Member
        chkMember = new JCheckBox("Daftarkan sebagai Member");
        chkMember.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkMember.setBackground(Color.WHITE);
        formContent.add(chkMember);
        formContent.add(Box.createVerticalStrut(20));

        // Area Tombol Aksi
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBackground(Color.WHITE);

        JButton btnSimpan = createButton("Simpan", new Color(37, 99, 235));
        JButton btnUbah = createButton("Ubah", new Color(245, 158, 11));
        JButton btnHapus = createButton("Hapus", new Color(220, 38, 38));
        JButton btnClear = createButton("Bersihkan", new Color(100, 116, 139));

        // Event Listeners (Aksi Tombol)
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

        String[] columns = {"ID", "Nama Lengkap", "No. Telepon", "Email", "Status", "Tgl Daftar"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel read-only
            }
        };

        tblPelanggan = new JTable(tableModel);
        tblPelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblPelanggan.setRowHeight(30);
        tblPelanggan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblPelanggan.getTableHeader().setBackground(new Color(241, 245, 249));

        // Menengahkan teks di kolom tertentu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblPelanggan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblPelanggan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        // Event saat baris tabel diklik (mengisi form otomatis)
        tblPelanggan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblPelanggan.getSelectedRow() != -1) {
                int row = tblPelanggan.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNama.setText(tableModel.getValueAt(row, 1).toString());
                txtNoTelp.setText(tableModel.getValueAt(row, 2).toString());
                txtEmail.setText(tableModel.getValueAt(row, 3).toString());
                chkMember.setSelected(tableModel.getValueAt(row, 4).toString().equals("Member"));
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblPelanggan);
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        return tableWrapper;
    }

    // Helper membuat label + input text
    private JTextField addFormField(JPanel parent, String labelName) {
        JLabel lbl = new JLabel(labelName);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(71, 85, 105));
        parent.add(lbl);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        parent.add(txt);
        parent.add(Box.createVerticalStrut(15));
        return txt;
    }

    // Helper membuat tombol modern
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

    // ================= LOGIKA DATABASE (CRUD) ================= //

    private void refreshTableData(String keyword) {
        tableModel.setRowCount(0); // Bersihkan tabel
        try {
            List<Pelanggan> list;
            if (keyword.isEmpty()) {
                list = pelangganDAO.getAll();
            } else {
                list = pelangganDAO.cari(keyword); // Implementasi fitur Searchable!
            }

            for (Pelanggan p : list) {
                tableModel.addRow(new Object[]{
                        p.getIdPelanggan(),
                        p.getNama(),
                        p.getNoTelp(),
                        p.getEmail(),
                        p.isMember() ? "Member" : "Reguler",
                        p.getTglDaftar()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void simpanData() {
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Pelanggan p = new Pelanggan();
            p.setNama(txtNama.getText().trim());
            p.setNoTelp(txtNoTelp.getText().trim());
            p.setEmail(txtEmail.getText().trim());
            p.setMember(chkMember.isSelected());

            if (pelangganDAO.insert(p)) {
                JOptionPane.showMessageDialog(this, "Data Pelanggan berhasil disimpan!");
                clearForm();
                refreshTableData("");
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

        try {
            Pelanggan p = new Pelanggan();
            p.setIdPelanggan(Integer.parseInt(txtId.getText()));
            p.setNama(txtNama.getText().trim());
            p.setNoTelp(txtNoTelp.getText().trim());
            p.setEmail(txtEmail.getText().trim());
            p.setMember(chkMember.isSelected());

            if (pelangganDAO.update(p)) {
                JOptionPane.showMessageDialog(this, "Data Pelanggan berhasil diperbarui!");
                clearForm();
                refreshTableData("");
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

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus pelanggan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (pelangganDAO.delete(Integer.parseInt(txtId.getText()))) {
                    JOptionPane.showMessageDialog(this, "Data Pelanggan berhasil dihapus!");
                    clearForm();
                    refreshTableData("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Data pelanggan ini tidak bisa dihapus karena memiliki riwayat Booking!", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtNoTelp.setText("");
        txtEmail.setText("");
        chkMember.setSelected(false);
        txtSearch.setText("");
        tblPelanggan.clearSelection();
    }
}