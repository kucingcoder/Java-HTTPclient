package org.shohibulcoding.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class M_IBRAHIM_HANIF_client {
    private String full_link, agent;
    int timeout = 60000;
    public Request request = new Request();
    public Response response = new Response();

    public M_IBRAHIM_HANIF_client(){}
    public M_IBRAHIM_HANIF_client(int milidetik, String nama_agent, Request request, Response response){
        this.timeout = milidetik;
        this.agent = nama_agent;
        this.request = request;
        this.response = response;
    }

    public void set_Agent(String nama_agent){
        this.agent = nama_agent;
    }

    public void set_Timeout(int milidetik){
        this.timeout = milidetik;
    }

    public void eksekusi() throws IOException {
        this.request.tambah_Header("User-Agent", this.agent);

        full_link = this.request.get_Link();

        if (this.request.get_DaftarParameter() != null){
            full_link += "?";
            int index = 1;

            for (Query parameter:this.request.get_DaftarParameter()) {
                full_link += parameter.key + "=" + parameter.value;

                if (index != this.request.get_DaftarParameter().size()){
                    full_link += "&";
                }

                index++;
            }
        }

        URL server = new URL(full_link);
        HttpURLConnection koneksi = (HttpURLConnection) server.openConnection();
        if (this.request.get_Permintaan().toString() == "PATCH") {
            koneksi.setRequestMethod("POST");
            this.request.tambah_Header("X-HTTP-Method-Override", "PATCH");
        } else {
            koneksi.setRequestMethod(this.request.get_Permintaan().toString());
        }

        koneksi.setConnectTimeout(this.timeout);
        koneksi.setReadTimeout(this.timeout);

        if (this.request.get_Permintaan().toString() != "GET" && this.request.get_Data() != null) {
            if (this.request.get_Data().startsWith("{") && this.request.get_Data().endsWith("}")) {
                this.request.tambah_Header("Content-Type", "application/json");
            }
        }

        if(this.request.get_DaftarHeader() != null){
            for (Header header:this.request.get_DaftarHeader()) {
                koneksi.setRequestProperty(header.key, header.value);
            }
        }

        if (this.request.get_Permintaan().toString() != "GET" && this.request.get_Data() != null) {
            koneksi.setDoOutput(true);
            OutputStream outputStream = koneksi.getOutputStream();
            outputStream.write(this.request.get_Data().getBytes());
            outputStream.flush();
            outputStream.close();
        }

        this.response.set_Kode(koneksi.getResponseCode());
        this.response.set_Pesan(koneksi.getResponseMessage());

        for(int i = 0; koneksi.getHeaderFieldKey(i) != null || koneksi.getHeaderField(i) != null; ++i) {
            this.response.tambah_Header(koneksi.getHeaderFieldKey(i), koneksi.getHeaderField(i));
        }

        BufferedReader reader;
        if (this.response.get_Kode() >= 200 && this.response.get_Kode() < 300) {
            reader = new BufferedReader(new InputStreamReader(koneksi.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(koneksi.getErrorStream()));
        }

        StringBuilder responseBody = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null) {
            responseBody.append(line);
        }

        this.response.set_Data(responseBody.toString());
        reader.close();
        koneksi.disconnect();
    }
}