package com.futsalku.gui;

import javax.swing.*;
import java.awt.*;

// TODO: Implementasi panel pembayaran
// PIC: Akbar (Muhammad Akbar Al Islami)

// PembayaranPanel — Panel proses pembayaran
public class PembayaranPanel extends JPanel {

    public PembayaranPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel label = new JLabel("Pembayaran — Coming Soon", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(new Color(120, 120, 140));
        add(label, BorderLayout.CENTER);
    }
}
