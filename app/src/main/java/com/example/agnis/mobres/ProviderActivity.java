package com.example.agnis.mobres;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.agnis.mobres.Adapter.RecyclerAdapterListAll;
import com.example.agnis.mobres.Adapter.RecyclerAdapterProvider;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.Config.UserAPIService;
import com.example.agnis.mobres.Model.ResultAll;
import com.example.agnis.mobres.Model.ResultProvider;
import com.example.agnis.mobres.Model.Value;
import com.example.agnis.mobres.Model.ValueProvider;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


        private ProgressDialog pDialog;
        private MenuItem mSearchMenuItem;
        private ArrayList<ResultProvider> resultProviders;
        private RecyclerAdapterProvider adapter;
        @BindView(R.id.card_recycler_view)
        RecyclerView recyclerView;
        String id_agen_kartu;

        Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_provider);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//            Bundle b = getIntent().getExtras();
//            id_agen_kartu = b.getString("id_agen");
            ButterKnife.bind(this);

            getSupportActionBar().setTitle("Daftar Provider");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            initViews();
            // isi_dropdown();
        }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    private void getData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueProvider> call = api.get_all_kuota2();
        call.enqueue(new Callback<ValueProvider>() {
            @Override
            public void onResponse(Call<ValueProvider> call, Response<ValueProvider> response) {
                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueProvider value = response.body();
                    resultProviders= new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterProvider(resultProviders, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(ProviderActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueProvider> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(ProviderActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
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
                adapter.setFilter(resultProviders);
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
        final ArrayList<ResultProvider> filteredModelList = filter(resultProviders, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }

    private ArrayList<ResultProvider> filter(ArrayList<ResultProvider> models, String query) {
        query = query.toLowerCase();final ArrayList<ResultProvider> filteredModelList = new ArrayList<>();
        for (ResultProvider model : models) {
            final String text = model.getNama_provider().toLowerCase();
            final String text2 = model.getJumlah().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }else if (text2.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}

