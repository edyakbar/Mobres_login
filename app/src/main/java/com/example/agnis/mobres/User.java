package com.example.agnis.mobres;

/**
 * Created by Rizzabas on 06/07/2018.
 */

public class User {
    private String id_user, nama, alamat, email, password, foto;

    public User(String id_user, String nama, String alamat,String email, String password){
        this.id_user = id_user;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.password = password;
   //     this.foto = foto;
    }

    public String getId_user() {
        return id_user;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

  //  public String getFoto() {
  //      return foto;

}
