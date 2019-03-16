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
import com.example.agnis.mobres.Model.ResultKuota;
import com.example.agnis.mobres.ProfilActivity;
import com.example.agnis.mobres.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edy akbar on 12/09/2018.
 */

public class RecyclerAdapterKuota extends RecyclerView.Adapter<RecyclerAdapterKuota.ViewHolder> {
    private ArrayList<ResultKuota> result;
    private Context context;

    public RecyclerAdapterKuota(ArrayList<ResultKuota> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterKuota.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_kuota, viewGroup, false);
        return new RecyclerAdapterKuota.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterKuota.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama_provider());
        viewHolder.t_jumlah.setText(result.get(i).getJumlah());


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

    public void setFilter(ArrayList<ResultKuota> models) {
        result = new ArrayList<>();
        result.addAll(models);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.t_nama)
        TextView t_nama;
        @BindView(R.id.t_jumlah)
        TextView t_jumlah;
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
                Intent intent = new Intent(context, AgenKuota.class);
                intent.putExtra("id_provider", result.get(i).getId_provider());
                intent.putExtra("nama_provider", result.get(i).getNama_provider());
                intent.putExtra("jumlah", result.get(i).getJumlah());

                context.startActivity(intent);
            }
        }
    }
}
