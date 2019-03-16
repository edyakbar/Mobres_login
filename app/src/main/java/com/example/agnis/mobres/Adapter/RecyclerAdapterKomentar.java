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
import com.example.agnis.mobres.KomentarActivity;
import com.example.agnis.mobres.Model.ResultKomen;
import com.example.agnis.mobres.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rizzabas on 17/08/2018.
 */

public class RecyclerAdapterKomentar extends RecyclerView.Adapter<RecyclerAdapterKomentar.ViewHolder> {
    private ArrayList<ResultKomen> result;
    private Context context;

    public RecyclerAdapterKomentar(ArrayList<ResultKomen> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerAdapterKomentar.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_komentar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterKomentar.ViewHolder viewHolder, int i) {
        viewHolder.t_nama.setText(result.get(i).getNama());
        viewHolder.t_komen.setText(result.get(i).getKomentar());


        Glide.with(context).load(Config.URL_IMG_AGEN+result.get(i).getFoto())
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

    public void setFilter(ArrayList<ResultKomen> models) {
        result = new ArrayList<>();
        result.addAll(models);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.txtNamaUser)
        TextView t_nama;
        @BindView(R.id.txtKomen)
        TextView t_komen;
        @BindView(R.id.img_komen)
        ImageView img_list;
        @BindView(R.id.cardview)
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
