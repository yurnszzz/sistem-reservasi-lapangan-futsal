package com.futsalku.gui;

import javax.swing.*;
import java.awt.*;

// TODO: Implementasi panel kelola pelanggan
// PIC: Akbar (Muhammad Akbar Al Islami)

// PelangganPanel — Panel CRUD data pelanggan
public class PelangganPanel extends JPanel {

    public PelangganPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel label = new JLabel("👥 Kelola Pelanggan — Coming Soon", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(new Color(120, 120, 140));
        add(label, BorderLayout.CENTER);
    }
}
