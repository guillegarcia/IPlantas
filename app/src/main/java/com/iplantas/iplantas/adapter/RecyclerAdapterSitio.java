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
import com.iplantas.iplantas.model.Sitio;

import java.util.List;

/**
 * Created by vicch on 13/11/2017.
 */

public class RecyclerAdapterSitio extends RecyclerView.Adapter <RecyclerAdapterSitio.SitioViewHolder> {

    private List<Sitio> sitios;
    private Context contex;

    public RecyclerAdapterSitio(Context context, List<Sitio> sitios){
        this.contex=context;
        this.sitios=sitios;
    }

    @Override
    public SitioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()) .inflate(R.layout.elemento_sitio, viewGroup, false);
        return new SitioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SitioViewHolder viewHolder, int position) {
        viewHolder.imagen.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.ic_place_black_24dp));
        viewHolder.nombre.setText(sitios.get(position).getName());
        String ubicacion="Sin ubicación";
        if(sitios.get(position).getLat()>=0){
            ubicacion="Con ubicación";
        }
        viewHolder.elementos.setText(ubicacion);
    }

    @Override
    public int getItemCount() {
        return this.sitios.size();
    }

    public static class SitioViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView elementos;
        public SitioViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            elementos = (TextView) v.findViewById(R.id.ubicacion);
        }
    }

}
