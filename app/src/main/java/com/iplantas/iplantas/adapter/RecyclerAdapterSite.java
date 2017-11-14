package com.iplantas.iplantas.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Site;

import java.util.List;

/**
 * Created by vicch on 13/11/2017.
 */

public class RecyclerAdapterSite extends RecyclerView.Adapter <RecyclerAdapterSite.SitioViewHolder> {

    private List<Site> sites;
    private Context contex;

    public RecyclerAdapterSite(Context context, List<Site> sites){
        this.contex=context;
        this.sites=sites;
    }

    @Override
    public SitioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()) .inflate(R.layout.element_site, viewGroup, false);
        return new SitioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SitioViewHolder viewHolder, int position) {
        viewHolder.image.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.ic_place_black_24dp));
        viewHolder.name.setText(sites.get(position).getName());
        String location="Sin ubicación";
        if(sites.get(position).getLat()>=0){
            location="Con ubicación";
        }
        viewHolder.location.setText(location);
    }

    @Override
    public int getItemCount() {
        return this.sites.size();
    }

    public static class SitioViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView image;
        public TextView name;
        public TextView location;
        public SitioViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            location = (TextView) v.findViewById(R.id.location);
        }
    }

}
