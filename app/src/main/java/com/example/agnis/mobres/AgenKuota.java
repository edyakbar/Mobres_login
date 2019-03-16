package com.example.agnis.mobres;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.agnis.mobres.Adapter.RecyclerAdapterProvider;
import com.example.agnis.mobres.Adapter.RecyclerAdapterProvider2;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;
import com.example.agnis.mobres.Model.ResultAll;
import com.example.agnis.mobres.Model.ResultProvider;
import com.example.agnis.mobres.Model.ValueProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgenKuota extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ProgressDialog pDialog;
    private MenuItem mSearchMenuItem;
    private ArrayList<ResultProvider> resultAlls;
    private RecyclerAdapterProvider2 adapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    String nama_provider, jumlah;
    private LocationManager locationManager;
    private Double lati,longi;

    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agen_kuota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        nama_provider = b.getString("nama_provider");
        jumlah = b.getString("jumlah");

        getSupportActionBar().setTitle(nama_provider);
        getSupportActionBar().setSubtitle(jumlah);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                return;
//            }
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,1, this);
//        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,1,this);
//        } else {
//            Toast.makeText(this,"Tolong nyalakan GPS",Toast.LENGTH_SHORT).show();
//        }



        initViews();
        // isi_dropdown();
    }


//    @Override
//    public void onLocationChanged(Location location) {
//        lati = location.getLatitude();
//        Log.i("catatan",String.valueOf(lati));
//        longi = location.getLongitude();
//
//        if (lati != 0 && longi != 0){
//            getDataTerdekat();
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getDataTerdekat();
    }

    private void getData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueProvider> call = api.get_agen_kuota(nama_provider,jumlah);
        call.enqueue(new Callback<ValueProvider>() {
            @Override
            public void onResponse(Call<ValueProvider> call, Response<ValueProvider> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueProvider value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterProvider2(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(AgenKuota.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueProvider> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(AgenKuota.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }


    private void getDataTerdekat(){
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        builder.addFormDataPart("lati", String.valueOf(lati));
//        builder.addFormDataPart("longi", String.valueOf(longi));
//        builder.addFormDataPart("nama_provider", nama_provider);
//        builder.addFormDataPart("jumlah",jumlah);
//        MultipartBody requestBody = builder.build(); "-6.868527","109.1053909"


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueProvider> call = api.getTerdekat2("-6.868527","109.1053909",nama_provider,jumlah);
        Log.i("catatan",String.valueOf(lati));
        call.enqueue(new Callback<ValueProvider>() {
            @Override
            public void onResponse(Call<ValueProvider> call, Response<ValueProvider> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueProvider value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterProvider2(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(AgenKuota.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueProvider> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(AgenKuota.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                adapter.setFilter(resultAlls);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<ResultProvider> filteredModelList = filter(resultAlls, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }

    private ArrayList<ResultProvider> filter(ArrayList<ResultProvider> models, String query) {
        query = query.toLowerCase();final ArrayList<ResultProvider> filteredModelList = new ArrayList<>();
        for (ResultProvider model : models) {
            final String text = model.getNama_agen().toLowerCase();
            final String text2 = model.getAlamat().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }else if (text2.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



}
