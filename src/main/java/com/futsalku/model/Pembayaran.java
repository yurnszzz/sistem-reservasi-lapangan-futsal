package com.futsalku.model;

import java.util.Date;

/**
 * Abstract class Pembayaran
 * Base class untuk PembayaranCash dan PembayaranTransfer
 * 
 * Menerapkan konsep: Inheritance, Encapsulation, Abstraction
 */
public abstract class Pembayaran {
    private int idPembayaran;
    private int idBooking;
    private double jumlah;
    private String metode;
    private String status;
    private Date tglBayar;

    // Constructor
    public Pembayaran() {}

    public Pembayaran(int idPembayaran, int idBooking, double jumlah, String metode, String status, Date tglBayar) {
        this.idPembayaran = idPembayaran;
        this.idBooking = idBooking;
        this.jumlah = jumlah;
        this.metode = metode;
        this.status = status;
        this.tglBayar = tglBayar;
    }

    // Abstract method — Polymorphism
    public abstract boolean prosesTransaksi();

    // Getter & Setter
    public int getIdPembayaran() { return idPembayaran; }
    public void setIdPembayaran(int idPembayaran) { this.idPembayaran = idPembayaran; }

    public int getIdBooking() { return idBooking; }
    public void setIdBooking(int idBooking) { this.idBooking = idBooking; }

    public double getJumlah() { return jumlah; }
    public void setJumlah(double jumlah) { this.jumlah = jumlah; }

    public String getMetode() { return metode; }
    public void setMetode(String metode) { this.metode = metode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getTglBayar() { return tglBayar; }
    public void setTglBayar(Date tglBayar) { this.tglBayar = tglBayar; }
}
