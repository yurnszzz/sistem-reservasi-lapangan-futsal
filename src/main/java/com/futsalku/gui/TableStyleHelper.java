package com.futsalku.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// TableStyleHelper — Utility untuk memberikan gaya tabel (JTable) modern dan estetik
// PIC: cantika Nurwulan Suci & Hasan Shofiyyur Rahman
public class TableStyleHelper {

    private static final Color HEADER_BG = new Color(37, 99, 235);       // Blue Accent (#2563EB)
    private static final Color HEADER_FG = Color.WHITE;                  // White Text
    private static final Color GRID_COLOR = new Color(226, 232, 240);     // Slate 200
    private static final Color SELECTION_BG = new Color(219, 234, 254);   // Blue 100
    private static final Color SELECTION_FG = new Color(30, 41, 59);      // Slate 800
    private static final Color ZEBRA_LIGHT = new Color(248, 250, 252);    // Slate 50 (belang-belang)

    /**
     * Memberikan gaya seragam pada JTable.
     * @param table JTable yang ingin diubah gayanya.
     * @param centerCols Array indeks kolom yang ingin diposisikan di tengah (center-aligned).
     */
    public static void styleTable(JTable table, int[] centerCols) {
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(GRID_COLOR);
        table.setSelectionBackground(SELECTION_BG);
        table.setSelectionForeground(SELECTION_FG);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Style Header Tabel
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setReorderingAllowed(false);

        // Custom renderer untuk header agar warna latar biru dan tulisan putih tampil sempurna di semua L&F
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setOpaque(true);
                setBackground(HEADER_BG);
                setForeground(HEADER_FG);
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                setHorizontalAlignment(JLabel.CENTER);
                // Grid/border pembatas kanan dan bawah
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(29, 78, 216))); // Blue 700 border
                return this;
            }
        });

        // Set kolom yang rata tengah
        java.util.Set<Integer> centerSet = new java.util.HashSet<>();
        if (centerCols != null) {
            for (int col : centerCols) {
                centerSet.add(col);
            }
        }

        // Custom renderer untuk zebra striping & padding sel
        for (int i = 0; i < table.getColumnCount(); i++) {
            final boolean shouldCenter = centerSet.contains(i);
            
            // Simpan renderer default jika ada kustomisasi khusus dari panel asal
            table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                    
                    // Padding horizontal 12px agar teks tidak menempel border
                    ((JComponent) c).setBorder(new EmptyBorder(0, 12, 0, 12));

                    if (shouldCenter) {
                        setHorizontalAlignment(JLabel.CENTER);
                    } else {
                        setHorizontalAlignment(JLabel.LEFT);
                    }

                    // Warna zebra
                    if (isSelected) {
                        c.setBackground(SELECTION_BG);
                        c.setForeground(SELECTION_FG);
                    } else {
                        if (row % 2 == 0) {
                            c.setBackground(Color.WHITE);
                        } else {
                            c.setBackground(ZEBRA_LIGHT);
                        }
                        c.setForeground(new Color(30, 41, 59));
                    }
                    return c;
                }
            });
        }
    }

    /**
     * Memanggil styleTable tanpa menentukan kolom tengah.
     */
    public static void styleTable(JTable table) {
        styleTable(table, null);
    }
}
