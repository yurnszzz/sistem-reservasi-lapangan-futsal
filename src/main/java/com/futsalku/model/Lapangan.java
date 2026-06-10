package com.futsalku.model;

/**
 * Abstract class Lapangan
 * Base class untuk LapanganIndoor dan LapanganOutdoor
 * 
 * Menerapkan konsep: Inheritance, Encapsulation, Abstraction
 */
public abstract class Lapangan {
    // Encapsulation: atribut private
    private int idLapangan;
    private String nama;
    private String tipe;
    private double hargaPerJam;
    private String status;
    private String fasilitas;

    // Constructor
    public Lapangan() {}

    public Lapangan(int idLapangan, String nama, String tipe, double hargaPerJam, String status, String fasilitas) {
        this.idLapangan = idLapangan;
        this.nama = nama;
        this.tipe = tipe;
        this.hargaPerJam = hargaPerJam;
        this.status = status;
        this.fasilitas = fasilitas;
    }

    // Abstract method — Polymorphism
    public abstract double hitungHarga(int durasi);

    // Getter & Setter
    public int getIdLapangan() { return idLapangan; }
    public void setIdLapangan(int idLapangan) { this.idLapangan = idLapangan; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }

    public double getHargaPerJam() { return hargaPerJam; }
    public void setHargaPerJam(double hargaPerJam) { this.hargaPerJam = hargaPerJam; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFasilitas() { return fasilitas; }
    public void setFasilitas(String fasilitas) { this.fasilitas = fasilitas; }
}
