package com.example.agnis.mobres;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Rizzabas on 06/07/2018.
 */

public class SharedPrefManager {

    //the constants
    public static final String SHARED_PREF_NAME = "agensharedprefmanager";
    private static final String KEY_IDUSER = "keyid_user";
    private static final String KEY_NAMA = "keynama";
    private static final String KEY_ALAMAT = "keyalamat";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
  //  private static final String KEY_FOTO = "keyfoto";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IDUSER, user.getId_user());
        editor.putString(KEY_NAMA, user.getNama());
        editor.putString(KEY_ALAMAT, user.getAlamat());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
       // editor.putString(KEY_FOTO, user.getFoto());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_IDUSER, null),
                sharedPreferences.getString(KEY_NAMA, null),
                sharedPreferences.getString(KEY_ALAMAT, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null)
//                sharedPreferences.getString(KEY_FOTO, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
