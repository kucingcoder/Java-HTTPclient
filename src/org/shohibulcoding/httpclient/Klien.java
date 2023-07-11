package org.shohibulcoding.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Klien {
    private String full_link, agent;
    int timeout = 60000;
    public Permintaan permintaan = new Permintaan();
    public Respon respon = new Respon();

    public Klien(){}
    public Klien(int milidetik, String nama_agent, Permintaan request, Respon respon){
        this.timeout = milidetik;
        this.agent = nama_agent;
        this.permintaan = request;
        this.respon = respon;
    }

    public void set_Agent(String nama_agent){
        this.agent = nama_agent;
    }

    public void set_Timeout(int milidetik){
        this.timeout = milidetik;
    }

    public void eksekusi() throws IOException {
        this.permintaan.tambah_Header("User-Agent", this.agent);

        full_link = this.permintaan.get_Link();

        if (this.permintaan.get_DaftarParameter() != null){
            full_link += "?";
            int index = 1;

            for (Query parameter:this.permintaan.get_DaftarParameter()) {
                full_link += parameter.key + "=" + parameter.value;

                if (index != this.permintaan.get_DaftarParameter().size()){
                    full_link += "&";
                }

                index++;
            }
        }

        URL server = new URL(full_link);
        HttpURLConnection koneksi = (HttpURLConnection) server.openConnection();
        if (this.permintaan.get_Permintaan().toString() == "PATCH") {
            koneksi.setRequestMethod("POST");
            this.permintaan.tambah_Header("X-HTTP-Method-Override", "PATCH");
        } else {
            koneksi.setRequestMethod(this.permintaan.get_Permintaan().toString());
        }

        koneksi.setConnectTimeout(this.timeout);
        koneksi.setReadTimeout(this.timeout);

        if (this.permintaan.get_Permintaan().toString() != "GET" && this.permintaan.get_Data() != null) {
            if (this.permintaan.get_Data().startsWith("{") && this.permintaan.get_Data().endsWith("}")) {
                this.permintaan.tambah_Header("Content-Type", "application/json");
            }
        }

        if(this.permintaan.get_DaftarHeader() != null){
            for (Header header:this.permintaan.get_DaftarHeader()) {
                koneksi.setRequestProperty(header.key, header.value);
            }
        }

        if (this.permintaan.get_Permintaan().toString() != "GET" && this.permintaan.get_Data() != null) {
            koneksi.setDoOutput(true);
            OutputStream outputStream = koneksi.getOutputStream();
            outputStream.write(this.permintaan.get_Data().getBytes());
            outputStream.flush();
            outputStream.close();
        }

        this.respon.set_Kode(koneksi.getResponseCode());
        this.respon.set_Pesan(koneksi.getResponseMessage());

        for(int i = 0; koneksi.getHeaderFieldKey(i) != null || koneksi.getHeaderField(i) != null; ++i) {
            this.respon.tambah_Header(koneksi.getHeaderFieldKey(i), koneksi.getHeaderField(i));
        }

        BufferedReader reader;
        if (this.respon.get_Kode() >= 200 && this.respon.get_Kode() < 300) {
            reader = new BufferedReader(new InputStreamReader(koneksi.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(koneksi.getErrorStream()));
        }

        StringBuilder responseBody = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null) {
            responseBody.append(line);
        }

        this.respon.set_Data(responseBody.toString());
        reader.close();
        koneksi.disconnect();
    }
}