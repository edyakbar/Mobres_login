package com.example.agnis.mobres;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {
    String id_user, nama, alamat, no_telp, email;
    @BindView(R.id.editNama)
    EditText editNama;
    @BindView(R.id.editAlamat)
    EditText editAlamat;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.btnSimpanEdit)
    Button btnSimpanEdit;
    private ArrayList resultAlls;

    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        User user = SharedPrefManager.getInstance(this).getUser();
        id_user = user.getId_user();
        nama = user.getNama();
        alamat = user.getAlamat();
        email = user.getEmail();

        editNama.setText(nama);
        editAlamat.setText(alamat);

        editEmail.setText(email);

        getSupportActionBar().setTitle("My Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @OnClick(R.id.btnSimpanEdit)
    void OnClick_HapusReservasi() {
        AlertDialog.Builder mydialogbuilder = new AlertDialog.Builder(this);
        mydialogbuilder
                .setTitle("Konfirmasi")
                .setMessage("Yakin mengubah profil anda?")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ubahprofil();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog mydialog = mydialogbuilder.create();
        mydialog.show();
    }


    @OnClick(R.id.btnLogout)
    void logout() {
        AlertDialog.Builder mydialogbuilder = new AlertDialog.Builder(this);
        mydialogbuilder
                .setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin untuk logout?")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog mydialog = mydialogbuilder.create();
        mydialog.show();


    }

    private void ubahprofil() {
        final String nama = editNama.getText().toString().trim();
        final String alamat = editAlamat.getText().toString().trim();
       // final String no_telp = editHP.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Silahkan Tunggu....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (TextUtils.isEmpty(nama)) {
            editNama.setError("Nama tidak boleh kosong");
            editNama.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(alamat)) {
            editAlamat.setError("Alamat tidak boleh kosong");
            editAlamat.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Email tidak boleh kosong");
            editEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Masukkan Email dengan benar");
            editEmail.requestFocus();
            return;
        }
        id_user = SharedPrefManager.getInstance(this).getUser().getId_user();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("id_user", id_user);
        builder.addFormDataPart("nama", nama);
        builder.addFormDataPart("alamat", alamat);
        builder.addFormDataPart("email", email);
        //builder.addFormDataPart("no_hp", no_telp);
        MultipartBody requestBody = builder.build();


        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseBody> call = api.update_profil(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //  String value = response.body().getStatus();
                //String message = response.body().getMessage();
                pDialog.dismiss();
                if (response.isSuccessful()) {
//                    Log.i("catatan", id_absen);
                    Toast.makeText(UpdateProfile.this,"berhasil",Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    finish();
                } else {
                    Toast.makeText(UpdateProfile.this, "gagal bos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismiss();
                Toast.makeText(UpdateProfile.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
