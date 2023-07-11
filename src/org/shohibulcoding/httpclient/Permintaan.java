package org.shohibulcoding.httpclient;

import java.util.ArrayList;

public class Permintaan {
    private String link, data;
    private Metode permintaan;
    ArrayList<Query> daftar_parameter = new ArrayList<>();
    ArrayList<Header> daftar_header = new ArrayList<>();

    public Permintaan(){}

    public Permintaan(String link, String data, Metode permintaan, ArrayList<Query> daftar_parameter, ArrayList<Header> daftar_header){
        this.link = link;
        this.data = data;
        this.permintaan = permintaan;
        this.daftar_parameter = daftar_parameter;
        this.daftar_header = daftar_header;
    }

    public String get_Link() {
        return this.link;
    }

    public void set_Link(String link) {
        this.link = link;
    }

    public String get_Data() {
        return this.data;
    }

    public void set_Data(String data) {
        this.data = data;
    }

    public Metode get_Permintaan() {
        return this.permintaan;
    }

    public void set_Permintaan(Metode permintaan) {
        this.permintaan = permintaan;
    }

    public ArrayList<Query> get_DaftarParameter() {
        return this.daftar_parameter;
    }

    public void tambah_Parameter(String kunci, String nilai){
        this.daftar_parameter.add(new Query(kunci, nilai));
    }

    public ArrayList<Header> get_DaftarHeader() {
        return this.daftar_header;
    }

    public void tambah_Header(String kunci, String nilai) {
        this.daftar_header.add(new Header(kunci, nilai));
    }
}
