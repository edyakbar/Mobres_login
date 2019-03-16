package com.example.agnis.mobres;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.agnis.mobres.Config.Config;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailAgenActivity extends AppCompatActivity implements OnMapReadyCallback {
    LatLng PETA;
    Double lt, lg;
    String id_agen,nama_agen,alamat,keterangan,foto, latitude, longitude;

    @BindView(R.id.nama)
    TextView t_nama;
    @BindView(R.id.alamat) TextView t_alamat;
    @BindView(R.id.keterangan) TextView t_keterangan;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.textKomentar)
    TextView t_komen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        id_agen = b.getString("id_agen");
        nama_agen = b.getString("nama_agen");
        alamat = b.getString("alamat");
        keterangan = b.getString("keterangan");
        latitude = b.getString("lt");
        foto = b.getString("foto");
        longitude = b.getString("lg");

        lt = Double.valueOf(b.getString("lt"));
        lg = Double.valueOf(b.getString("lg"));
        PETA = new LatLng(lt, lg);

        t_nama.setText(nama_agen);
        t_alamat.setText(alamat);
        t_keterangan.setText(keterangan);

        Glide.with(this).load(Config.URL_IMG_AGEN+foto)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img);
        getSupportActionBar().setTitle(nama_agen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.textKomentar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
//                startActivity(new Intent(DetailAgenActivity.this, KomentarActivity.class));
                Context context = view.getContext();
                Intent intent = new Intent(context, KomentarActivity.class);
                intent.putExtra("id_agen", id_agen);
                intent.putExtra("foto", foto);
                context.startActivity(intent);
            }
        });

//        findViewById(R.id.textKuota).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                finish();
////                startActivity(new Intent(DetailAgenActivity.this, KomentarActivity.class));
//                Context context = view.getContext();
//                Intent intent = new Intent(context, ProviderActivity.class);
//                intent.putExtra("id_agen", id_agen);
////                intent.putExtra("id_provider", id_provider);
////                intent.putExtra("id_provider", id_provider);
//                intent.putExtra("foto", foto);
//                context.startActivity(intent);
//            }
//        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Marker ragunan = googleMap.addMarker(new MarkerOptions().position(PETA).title(nama_agen).snippet(alamat));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PETA, 15));
        if (android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && android.support.v4.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


}



