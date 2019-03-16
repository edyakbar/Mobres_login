package com.example.agnis.mobres.Model;

/**
 * Created by edy akbar on 12/09/2018.
 */

public class ResultKuota {
    String id_provider,id_agen,id_agen_kartu,nama_provider,jumlah,foto_provider;

    public void setId_provider(String id_provider) {
        this.id_provider = id_provider;
    }

    public void setId_agen(String id_agen) {
        this.id_agen = id_agen;
    }

    public void setId_agen_kartu(String id_agen_kartu) {
        this.id_agen_kartu = id_agen_kartu;
    }

    public void setNama_provider(String nama_provider) {
        this.nama_provider = nama_provider;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getId_provider() {
        return id_provider;
    }

    public String getId_agen() {
        return id_agen;
    }

    public String getId_agen_kartu() {
        return id_agen_kartu;
    }

    public String getNama_provider() {
        return nama_provider;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getFoto_provider() {
        return foto_provider;
    }

    public void setFoto_provider(String foto_provider) {

        this.foto_provider = foto_provider;
    }
}
