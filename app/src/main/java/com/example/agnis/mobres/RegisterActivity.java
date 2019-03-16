package com.example.agnis.mobres;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNama, etAlamat, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, UtamaActivity.class));
            return;
        }

        etNama = (EditText) findViewById(R.id.etNama);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        etEmail = (EditText) findViewById(R.id.etEmail);
     //   etPassword = (EditText) findViewById(R.id.etPassword);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        //BUTTON KEMBALI KE MENU LOGIN
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


    }

    private void registerUser() {
        final String nama = etNama.getText().toString().trim();
        final String alamat = etAlamat.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
    //    final String password = etPassword.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(nama)) {
            etNama.setError("Nama tidak boleh kosong");
            etNama.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(alamat)) {
            etAlamat.setError("Alamat tidak boleh kosong");
            etAlamat.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Masukkan Email dengan benar");
            etEmail.requestFocus();
            return;
        }
//
//        if (TextUtils.isEmpty(password)) {
//            etPassword.setError("Masukkan Password");
//            etPassword.requestFocus();
//            return;
//        }


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("nama",nama);
        builder.addFormDataPart("alamat",alamat);
        builder.addFormDataPart("email",email);
    //    builder.addFormDataPart("password",password);
        MultipartBody requestBody = builder.build();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseBody> call = api.signup(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");

                    if (status.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

//                        for (int i=0;i<jsonArray.length();i++){
//                            JSONObject c = jsonArray.getJSONObject(i);
//                            User user = new User(
//                                    c.getString("id_user"),
//                                    c.getString("nama"),
//                                    c.getString("alamat"),
//                                    c.getString("email"),
//                                    c.getString("password"),
//                                    c.getString("foto")
//                            );
//
//                            //storing the user in shared preferences
//                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                            //starting the profile activity
//                            finish();
//                            startActivity(new Intent(getApplicationContext(), UtamaActivity.class));
//                        }

                        Toast.makeText(getApplicationContext(), "Password telah dikirim ke email Anda, Silakan cek email!", Toast.LENGTH_LONG).show();
                     //
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Nama atau Email telah ada", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });


    }

}
