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
import com.example.agnis.mobres.Model.ResultProvider;
import com.example.agnis.mobres.ProfilActivity;
import com.example.agnis.mobres.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rizzabas on 02/09/2018.
 */

public class RecyclerAdapterProvider2 extends RecyclerView.Adapter<RecyclerAdapterProvider2.ViewHolder> {
    private ArrayList<ResultProvider> result;
    private Context context;

    public RecyclerAdapterProvider2(ArrayList<ResultProvider> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterProvider2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_agen, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterProvider2.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama_agen());
        viewHolder.t_alamat.setText(result.get(i).getAlamat());
        viewHolder.t_harga.setText(result.get(i).getHarga());
        viewHolder.t_tersedia.setText(result.get(i).getTersedia());
        viewHolder.t_jarak.setText(result.get(i).getJarak());


        Glide.with(context).load(Config.URL_IMG_PROVIDER+result.get(i).getFoto())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_card)
                .crossFade()
                .error(R.mipmap.ic_card)
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
        @BindView(R.id.t_nama)
        TextView t_nama;
        @BindView(R.id.t_alamat)
        TextView t_alamat;
        @BindView(R.id.t_tersedia)
        TextView t_tersedia;
        @BindView(R.id.t_jarak)
        TextView t_jarak;
        @BindView(R.id.t_harga)
        TextView t_harga;
        @BindView(R.id.img_recycler)
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
                intent.putExtra("id_agen", result.get(i).getId_agen_kartu());
                intent.putExtra("nama_agen", result.get(i).getNama_agen());
                intent.putExtra("alamat", result.get(i).getAlamat());
                intent.putExtra("keterangan", result.get(i).getKeterangan());
                intent.putExtra("foto", result.get(i).getFoto());
                intent.putExtra("lt", result.get(i).getLatitude());
                intent.putExtra("lg", result.get(i).getLongitude());


                context.startActivity(intent);
            }
        }
    }
}
