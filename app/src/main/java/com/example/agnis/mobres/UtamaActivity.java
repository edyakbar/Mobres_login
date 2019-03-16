package com.example.agnis.mobres;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UtamaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textNamaProfil, textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Biar nyambung ke akun
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
         //   }
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Nambah Nama di header navigation
        View headerView = navigationView.getHeaderView(0);
        textNamaProfil = (TextView)headerView.findViewById(R.id.textNamaProfil);
        textNamaProfil.setText(SharedPrefManager.getInstance(this).getUser().getNama());
        textEmail = (TextView)headerView.findViewById(R.id.textEmail);
        textEmail.setText(SharedPrefManager.getInstance(this).getUser().getEmail());
        //sampai sini

        navigationView.setNavigationItemSelectedListener(this);

        //button List Map
        CardView btnAgen = (CardView) findViewById(R.id.btnAgen);
        btnAgen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), KuotaActivity.class);
                startActivity(i);
            }
        });

        //button List Map
        CardView btnMaps = (CardView) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), PetaActivity.class);
                startActivity(i);
            }
        });

        //button List Bantuan
        CardView btnFeed = (CardView) findViewById(R.id.btnFeed);
        btnFeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(i);
            }
        });

        //button List Profil
        CardView btnProfil = (CardView) findViewById(R.id.btnProfil);
        btnProfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.utama, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {
            Intent intentku = new Intent(UtamaActivity.this, ProfilActivity.class);
            startActivity(intentku);

        } else if (id == R.id.nav_bantu) {
            Intent intentku = new Intent(UtamaActivity.this, BantuanActivity.class);
            startActivity(intentku);

        } else if (id == R.id.nav_info) {
            Tools.showDialogAbout(this);

        } else if (id == R.id.nav_keluar) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
