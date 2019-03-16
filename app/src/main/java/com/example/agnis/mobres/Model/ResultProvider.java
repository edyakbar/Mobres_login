package com.example.agnis.mobres.Model;

import android.util.Half;

/**
 * Created by Rizzabas on 02/09/2018.
 */

public class ResultProvider {
    String id_provider;
    String id_agen_kartu;
    String nama_provider;
    String jumlah;
    String harga;
    String id_agen;
    String foto;
    String nama_agen;
    String alamat;
    String tersedia,jarak;

    public String getTersedia() {
        return tersedia;
    }

    public void setTersedia(String tersedia) {
        this.tersedia = tersedia;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    String keterangan,latitude,longitude;


    public String getFoto_provider() {
        return foto_provider;
    }

    public void setFoto_provider(String foto_provider) {
        this.foto_provider = foto_provider;
    }

    String foto_provider;

        public String getId_provider() {
            return id_provider;
        }

        public void setId(String id_provider) {
            this.id_provider = id_provider;
        }

    public void setId_provider(String id_provider) {
        this.id_provider = id_provider;
    }

    public String getId_agen_kartu() {
        return id_agen_kartu;
    }

    public void setId_agen_kartu(String id_agen_kartu) {
        this.id_agen_kartu = id_agen_kartu;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNama_agen() {return nama_agen;}

        public void setNama_agen(String nama_agen) {
            this.nama_agen = nama_agen;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }
        public String getHarga() {return harga;}

        public String getId_agen() {
                return id_agen;
            }

        public void setId_agen(String id_agen) {this.id_agen = id_agen;}

        public String getNama_provider() {
            return nama_provider;
        }

        public void setNama_provider(String nama_provider) {
            this.nama_provider = nama_provider;
        }

        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

}
