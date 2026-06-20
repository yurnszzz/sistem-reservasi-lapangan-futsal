package com.futsalku.gui;

import javax.swing.*;
import java.awt.*;

// TODO: Implementasi panel kelola lapangan
// PIC: Akbar (Muhammad Akbar Al Islami)

// LapanganPanel — Panel CRUD data lapangan
public class LapanganPanel extends JPanel {

    public LapanganPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel label = new JLabel("🏟️ Kelola Lapangan — Coming Soon", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(new Color(120, 120, 140));
        add(label, BorderLayout.CENTER);
    }
}
