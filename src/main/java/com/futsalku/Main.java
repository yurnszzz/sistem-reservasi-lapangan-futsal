package com.futsalku;

import com.futsalku.gui.MainFrame;
import javax.swing.*;

// FutsalKu — Sistem Reservasi Lapangan Futsal
// Entry point aplikasi
public class Main {
    public static void main(String[] args) {
        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel agar tampilan lebih native
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Gunakan default jika gagal
                e.printStackTrace();
            }

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
