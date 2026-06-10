package com.futsalku.model;

import java.util.Date;

/**
 * PembayaranCash — Subclass dari Pembayaran
 * Menerapkan konsep: Inheritance, Polymorphism
 */
public class PembayaranCash extends Pembayaran {

    public PembayaranCash() {
        super();
        setMetode("Cash");
    }

    public PembayaranCash(int idPembayaran, int idBooking, double jumlah, String status, Date tglBayar) {
        super(idPembayaran, idBooking, jumlah, "Cash", status, tglBayar);
    }

    /**
     * Polymorphism: Override prosesTransaksi
     * Cash — langsung lunas saat dibayar
     */
    @Override
    public boolean prosesTransaksi() {
        setStatus("Lunas");
        setTglBayar(new Date());
        return true;
    }
}
