package com.futsalku.model;

import java.util.Date;

/**
 * Class Pelanggan
 * Menerapkan konsep: Encapsulation
 */
public class Pelanggan {
    private int idPelanggan;
    private String nama;
    private String noTelp;
    private String email;
    private boolean isMember;
    private Date tglDaftar;

    // Constructor
    public Pelanggan() {}

    public Pelanggan(int idPelanggan, String nama, String noTelp, String email, boolean isMember, Date tglDaftar) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.noTelp = noTelp;
        this.email = email;
        this.isMember = isMember;
        this.tglDaftar = tglDaftar;
    }

    // Getter & Setter
    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isMember() { return isMember; }
    public void setMember(boolean member) { isMember = member; }

    public Date getTglDaftar() { return tglDaftar; }
    public void setTglDaftar(Date tglDaftar) { this.tglDaftar = tglDaftar; }
}
