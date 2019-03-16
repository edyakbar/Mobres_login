package com.example.agnis.mobres;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;
import com.example.agnis.mobres.Model.ResultAll;
import com.example.agnis.mobres.Model.Value;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetaActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap map;
    private ProgressDialog pDialog;
    private List<ResultAll> resultAll = new ArrayList<>();
//    private static final LatLngBounds DUKUHTURI_BOUNDS = new LatLngBounds(new LatLng(-6.904100, 109.120963),
//            new LatLng(-6.904100, 109.120963));
    private LocationManager locationManager;
    private Double lati,longi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.peta);
        supportMapFragment.getMapAsync(this);

        get_maps();
    }
    private void get_maps(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.getJSON();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                pDialog.dismiss();
                Toast.makeText(PetaActivity.this,"Sukses",Toast.LENGTH_SHORT).show();
                Value value = response.body();
                resultAll = new ArrayList<>(Arrays.asList(value.getResult()));
                setMaps();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(PetaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }
    private void setMaps(){
        for (int i=0;i<=resultAll.size()-1;i++){
            //Toast.makeText(MapsAllActivity.this,resultAll.get(i).getNama(),Toast.LENGTH_SHORT).show();
            LatLng peta = new LatLng(Double.valueOf(resultAll.get(i).getLatitude()), Double.valueOf(resultAll.get(i).getLongitude()));
            Log.d("hasil_peta",resultAll.get(i).getLatitude()+"/"+resultAll.get(i).getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(peta).zoom(10).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            map.addMarker(new MarkerOptions().position(peta).title(resultAll.get(i).getNama_agen())
                    .snippet(resultAll.get(i).getAlamat()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //map.setLatLngBoundsForCameraTarget(DUKUHTURI_BOUNDS);

        //googleMap.addMarker(new MarkerOptions().position(PETA).title(nama).snippet(alamat));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PETA, 15));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
    }

//    @OnClick(R.id.btnTerdekat) void OnClick_terdekat(){
//        Toast.makeText(this,"Cari...",Toast.LENGTH_SHORT).show();
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
//    }

    @Override
    public void onLocationChanged(Location location) {
        lati = location.getLatitude();
        longi = location.getLongitude();
        if (lati != 0 && longi != 0){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati,longi),16));
            map.clear();
            map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title("Lokasi saya").position(new LatLng(lati,longi)));
            getTerdekat();
        }
    }

    private void getTerdekat() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("lati", String.valueOf(lati));
        builder.addFormDataPart("longi", String.valueOf(longi));
        MultipartBody requestBody = builder.build();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseBody> call = api.getTerdekat(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        Double lt = Double.valueOf(c.getString("latitude"));
                        Double lg = Double.valueOf(c.getString("longitude"));

                        map.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                .title(c.getString("nama_agen"))
                                .snippet(c.getString("alamat"))
                                .position(new LatLng(lt,lg)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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
            Intent intentku= new Intent(PetaActivity.this,UtamaActivity.class);
            startActivity(intentku);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
