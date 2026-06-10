package com.futsalku.model;

/**
 * LapanganIndoor — Subclass dari Lapangan
 * Menerapkan konsep: Inheritance, Polymorphism
 */
public class LapanganIndoor extends Lapangan {

    public LapanganIndoor() {
        super();
        setTipe("Indoor");
    }

    public LapanganIndoor(int idLapangan, String nama, double hargaPerJam, String status, String fasilitas) {
        super(idLapangan, nama, "Indoor", hargaPerJam, status, fasilitas);
    }

    /**
     * Polymorphism: Override hitungHarga
     * Indoor memiliki tambahan biaya AC sebesar 10%
     */
    @Override
    public double hitungHarga(int durasi) {
        return getHargaPerJam() * durasi * 1.10; // +10% biaya AC
    }
}
