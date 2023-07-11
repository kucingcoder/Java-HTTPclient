package org.shohibulcoding.httpclient;

import java.util.ArrayList;

public class Respon {
    private int kode;
    private String pesan, data;

    ArrayList<Header> daftar_header = new ArrayList<>();

    public Respon(){}

    public Respon(int kode, String pesan, String data, ArrayList<Header> daftar_header){
        this.kode = kode;
        this.pesan = pesan;
        this.data = data;
        this.daftar_header = daftar_header;
    }

    public int get_Kode() {
        return this.kode;
    }

    public void set_Kode(int kode) {
        this.kode = kode;
    }

    public String get_Pesan() {
        return this.pesan;
    }

    public void set_Pesan(String pesan) {
        this.pesan = pesan;
    }

    public ArrayList<Header> get_DaftarHeader() {
        return this.daftar_header;
    }

    public void tambah_Header(String kunci, String nilai) {
        daftar_header.add(new Header(kunci, nilai));
    }

    public String get_Data() {
        return this.data;
    }

    public void set_Data(String data) {
        this.data = data;
    }
}
