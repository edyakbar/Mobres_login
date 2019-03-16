package com.example.agnis.mobres.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.agnis.mobres.AgenKuota;
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.DetailAgenActivity;
import com.example.agnis.mobres.Model.ResultAll;
import com.example.agnis.mobres.Model.ResultKomen;
import com.example.agnis.mobres.Model.ResultProvider;
import com.example.agnis.mobres.ProfilActivity;
import com.example.agnis.mobres.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rizzabas on 02/09/2018.
 */

public class RecyclerAdapterProvider extends RecyclerView.Adapter<RecyclerAdapterProvider.ViewHolder> {
    private ArrayList<ResultProvider> result;
    private Context context;

    public RecyclerAdapterProvider(ArrayList<ResultProvider> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterProvider.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_provider, viewGroup, false);
        return new RecyclerAdapterProvider.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterProvider.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama_provider());
        viewHolder.t_alamat.setText(result.get(i).getJumlah());


        Glide.with(context).load(Config.URL_IMG_PROVIDER+result.get(i).getFoto_provider())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.img_list);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void setFilter(ArrayList<ResultProvider> models) {
        result = new ArrayList<>();
        result.addAll(models);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txtNamaPro)
        TextView t_nama;
        @BindView(R.id.txtGb)
        TextView t_alamat;
        @BindView(R.id.img_provider)
        ImageView img_list;
        @BindView(R.id.cardview)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {
                int i = getAdapterPosition();
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailAgenActivity.class);
                intent.putExtra("id_agen", result.get(i).getId_agen());
                intent.putExtra("nama_agen", result.get(i).getNama_agen());
                intent.putExtra("alamat", result.get(i).getAlamat());
                intent.putExtra("keterangan", result.get(i).getKeterangan());
                intent.putExtra("foto", result.get(i).getFoto());
//                intent.putExtra("lt", result.get(i).getLatitude());
//                intent.putExtra("lg", result.get(i).getLongitude());


                context.startActivity(intent);
            }
        }
    }
    }

