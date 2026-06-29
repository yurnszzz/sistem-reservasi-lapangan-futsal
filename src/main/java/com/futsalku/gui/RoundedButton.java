package com.futsalku.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// RoundedButton — Tombol kustom dengan sudut melengkung dan efek hover modern
// PIC: Cantika Nurwulan Suci & Hasan Shofiyyur Rahman
public class RoundedButton extends JButton {
    private Color normalBg;
    private Color hoverBg;
    private Color normalFg;
    private int radius = 12; // Radius sudut melengkung

    public RoundedButton(String text, Color bg, Color fg) {
        super(text);
        this.normalBg = bg;
        this.normalFg = fg;
        // Tentukan warna hover otomatis sedikit lebih terang
        this.hoverBg = getHoverColor(bg);

        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(fg);
        setBackground(bg);
        
        // Mematikan visualisasi default agar paintComponent kustom berjalan sempurna
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efek hover mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBg);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalBg);
                repaint();
            }
        });
    }

    // Constructor tambahan dengan custom radius
    public RoundedButton(String text, Color bg, Color fg, int radius) {
        this(text, bg, fg);
        this.radius = radius;
    }

    private Color getHoverColor(Color color) {
        // Buat warna sedikit lebih gelap/terang untuk hover
        int r = Math.max(0, Math.min(255, color.getRed() + (color.getRed() > 128 ? -20 : 25)));
        int g = Math.max(0, Math.min(255, color.getGreen() + (color.getGreen() > 128 ? -20 : 25)));
        int b = Math.max(0, Math.min(255, color.getBlue() + (color.getBlue() > 128 ? -20 : 25)));
        return new Color(r, g, b);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Gambar background bulat dengan warna sesuai status aktif/non-aktif
        if (!isEnabled()) {
            g2.setColor(new Color(226, 232, 240)); // Slate 200 (Abu-abu non-aktif)
            setForeground(new Color(148, 163, 184)); // Slate 400 (Teks non-aktif)
        } else {
            g2.setColor(getBackground());
            setForeground(normalFg != null ? normalFg : Color.WHITE);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        g2.dispose();
        super.paintComponent(g);
    }
}
