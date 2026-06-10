package com.futsalku.model;

import java.util.Date;

/**
 * PembayaranTransfer — Subclass dari Pembayaran
 * Menerapkan konsep: Inheritance, Polymorphism
 */
public class PembayaranTransfer extends Pembayaran {

    public PembayaranTransfer() {
        super();
        setMetode("Transfer");
    }

    public PembayaranTransfer(int idPembayaran, int idBooking, double jumlah, String status, Date tglBayar) {
        super(idPembayaran, idBooking, jumlah, "Transfer", status, tglBayar);
    }

    /**
     * Polymorphism: Override prosesTransaksi
     * Transfer — status pending sampai dikonfirmasi
     */
    @Override
    public boolean prosesTransaksi() {
        setStatus("Pending");
        setTglBayar(new Date());
        // Transfer butuh verifikasi manual oleh admin
        return true;
    }
}
