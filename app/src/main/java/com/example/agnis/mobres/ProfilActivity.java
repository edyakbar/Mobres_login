package com.example.agnis.mobres;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfilActivity extends AppCompatActivity {
    TextView textUsername, textUsername1, textEmail, textAlamat;
    private CircleImageView ProfilBulat;

    EditText etPassword, etPasswordlama;
    CheckBox etLihatpass;
    SharedPrefManager sharedPrefManager;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ProgressDialog loading;
    String id_user;
    Context mContext;
    Retrofit mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        ProfilBulat = (CircleImageView) findViewById(R.id.imgProfilBulat);
//        textNama = (TextView) findViewById(R.id.textNama);
        textUsername = (TextView) findViewById(R.id.textUsername);
        textUsername1 = (TextView) findViewById(R.id.textUsername1);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textAlamat = (TextView) findViewById(R.id.textAlamat);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        Glide.with(this).load(Config.URL_IMG_PROFIL+ProfilBulat)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(ProfilBulat);
//        textNama.setText(String.valueOf(user.getId()));
        textUsername.setText(user.getNama());
        textUsername1.setText(user.getNama());
        textEmail.setText(user.getEmail());
        textAlamat.setText(user.getAlamat());

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        findViewById(R.id.btnUbahPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               formUbahpass();
            }
        });
    }
    //Untuk toolbar kembali ke Home
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tombol_home, menu);//buat notifiikasi
//        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.kembalihome) {
            Intent intentku= new Intent(ProfilActivity.this,UtamaActivity.class);
            startActivity(intentku);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void formUbahpass(){
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_password_alert, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        mContext = this;
        mApiService = com.example.agnis.mobres.Config.Config.getRetrofit(); // meng-init yang ada di package apihelper
        sharedPrefManager = new SharedPrefManager(this);

        etPasswordlama    = (EditText) dialogView.findViewById(R.id.etPasswordlama);
        etPassword    = (EditText) dialogView.findViewById(R.id.etPassword);
        etLihatpass    = (CheckBox) dialogView.findViewById(R.id.show_hide_password);

        etLihatpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button,
                                         boolean isChecked) {

                // If it is checkec then show password else hide
                // password
                if (isChecked) {

                    etPasswordlama.setInputType(InputType.TYPE_CLASS_TEXT);
                    etPasswordlama.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {

                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("UBAH", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ubahPassword();
            }
        });

        dialog.show();

    }


    private void ubahPassword() {
        id_user = (SharedPrefManager.getInstance(this).getUser().getId_user());
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Masukan Pasword Baru Anda");
            etPassword.requestFocus();
        }else if(TextUtils.isEmpty(etPasswordlama.getText().toString())) {
            etPassword.setError("Masukan Pasword Lama Anda");
            etPassword.requestFocus();
        }else {
            loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
            UserAPIService getResponse = Config.getRetrofit().create(UserAPIService.class);
            Call<ResponseBody> call = getResponse.change_passwd(id_user,etPasswordlama.getText().toString(),etPassword.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.i("debug", "onResponse: BERHASIL");
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("status").equals("1")) {
                                        Toast.makeText(mContext, "Password Berhasil diubah", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //  String error_message = jsonRESULTS.getString("error_msg");
                                        Toast.makeText(mContext, "Password Lama Salah", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.i("debug", "onResponse: GA BERHASIL");
                                loading.dismiss();
                                Toast.makeText(mContext, "Password Lama Salah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}
