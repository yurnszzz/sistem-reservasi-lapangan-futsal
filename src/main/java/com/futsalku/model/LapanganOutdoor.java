package com.futsalku.model;

/**
 * LapanganOutdoor — Subclass dari Lapangan
 * Menerapkan konsep: Inheritance, Polymorphism
 */
public class LapanganOutdoor extends Lapangan {

    public LapanganOutdoor() {
        super();
        setTipe("Outdoor");
    }

    public LapanganOutdoor(int idLapangan, String nama, double hargaPerJam, String status, String fasilitas) {
        super(idLapangan, nama, "Outdoor", hargaPerJam, status, fasilitas);
    }

    /**
     * Polymorphism: Override hitungHarga
     * Outdoor tidak ada biaya tambahan
     */
    @Override
    public double hitungHarga(int durasi) {
        return getHargaPerJam() * durasi;
    }
}
