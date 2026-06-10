package com.futsalku.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Class Booking
 * Menerapkan konsep: Encapsulation
 */
public class Booking {
    private int idBooking;
    private int idLapangan;
    private int idPelanggan;
    private Date tanggal;
    private Time jamMulai;
    private int durasi;
    private double totalHarga;
    private String status;

    // Constructor
    public Booking() {}

    public Booking(int idBooking, int idLapangan, int idPelanggan, Date tanggal,
                   Time jamMulai, int durasi, double totalHarga, String status) {
        this.idBooking = idBooking;
        this.idLapangan = idLapangan;
        this.idPelanggan = idPelanggan;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.durasi = durasi;
        this.totalHarga = totalHarga;
        this.status = status;
    }

    // Getter & Setter
    public int getIdBooking() { return idBooking; }
    public void setIdBooking(int idBooking) { this.idBooking = idBooking; }

    public int getIdLapangan() { return idLapangan; }
    public void setIdLapangan(int idLapangan) { this.idLapangan = idLapangan; }

    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }

    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }

    public Time getJamMulai() { return jamMulai; }
    public void setJamMulai(Time jamMulai) { this.jamMulai = jamMulai; }

    public int getDurasi() { return durasi; }
    public void setDurasi(int durasi) { this.durasi = durasi; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
