package com.example.agnis.mobres.Model;

/**
 * Created by Rizzabas on 17/08/2018.
 */

public class ResultKomen {
    String id_feed;

    public String getId_agen_kartu() {
        return id_agen_kartu;
    }

    public void setId_agen_kartu(String id_agen_kartu) {
        this.id_agen_kartu = id_agen_kartu;
    }

    String id_agen_kartu;
    String id_user;
    String komentar;
    String foto;
    String nama;
    String nama_agen;

    public String getId_feed() {
        return id_feed;
    }

    public void setId_feed(String id_feed) {
        this.id_feed = id_feed;
    }



    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getNama_agen() {
        return nama_agen;
    }

    public void setNama_agen(String nama_agen) {
        this.nama = nama_agen;
    }
}
