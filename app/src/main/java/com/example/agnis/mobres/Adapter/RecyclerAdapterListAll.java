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
import com.example.agnis.mobres.Config.Config;
import com.example.agnis.mobres.DetailAgenActivity;
import com.example.agnis.mobres.Model.ResultAll;
import com.example.agnis.mobres.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rizzabas on 17/08/2018.
 */

public class RecyclerAdapterListAll extends RecyclerView.Adapter<RecyclerAdapterListAll.ViewHolder> {
    private ArrayList<ResultAll> result;
    private Context context;

    public RecyclerAdapterListAll(ArrayList<ResultAll> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterListAll.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_agen, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterListAll.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama_agen());
        viewHolder.t_alamat.setText(result.get(i).getAlamat());


        Glide.with(context).load(Config.URL_IMG_AGEN+result.get(i).getFoto())
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

    public void setFilter(ArrayList<ResultAll> models) {
        result = new ArrayList<>();
        result.addAll(models);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.t_nama)
        TextView t_nama;
        @BindView(R.id.t_alamat)
        TextView t_alamat;
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
                intent.putExtra("id_agen", result.get(i).getId_agen());
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
