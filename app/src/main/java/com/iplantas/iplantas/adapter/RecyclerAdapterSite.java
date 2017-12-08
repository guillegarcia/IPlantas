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

public class RecyclerAdapterSite extends RecyclerView.Adapter <RecyclerAdapterSite.SiteViewHolder> {

    private List<Site> sites;
    private Context contex;
    public RecyclerAdapterSiteListener myListener;

    public RecyclerAdapterSite(Context context, List<Site> sites, RecyclerAdapterSiteListener myListener){
        this.contex=context;
        this.sites=sites;
        this.myListener=myListener;
    }

    @Override
    public SiteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()) .inflate(R.layout.element_site_card, viewGroup, false);
        return new SiteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SiteViewHolder viewHolder, int position) {
        int type=sites.get(position).getType();
        switch (type){
            case Site.TYPE_EXAMPLE:{
                viewHolder.image.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.empty_home));
                break;
            }
            case Site.TYPE_MAIN:{
                viewHolder.image.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.my_home));
                break;
            }
            case Site.TYPE_OTHER:{
                viewHolder.image.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.second_home));
                break;
            }
            case Site.TYPE_WORK:{
                viewHolder.image.setImageDrawable(ContextCompat.getDrawable(contex, R.drawable.work_home));
                break;
            }
        }

        viewHolder.name.setText(sites.get(position).getName());
        viewHolder.num_plants.setText(this.contex.getResources().getString(R.string.site_subtitle));
        if(sites.get(position).getType()==Site.TYPE_EXAMPLE) {
            viewHolder.num_plants.setText(this.contex.getResources().getString(R.string.add_site));
            viewHolder.link.setVisibility(View.INVISIBLE);
        }
        else{
            viewHolder.link.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return this.sites.size();
    }

    //Metod to reload data
    public void swap(List<Site> newSites){
        if(newSites==null){
            return;
        }
        if(newSites!=null){
            this.sites.clear();
        }
        this.sites.addAll(newSites);
        notifyDataSetChanged();
    }

    public class SiteViewHolder extends RecyclerView.ViewHolder{
        // Campos respectivos de un item
        public ImageView image;
        public TextView name;
        public TextView num_plants;
        public TextView link;

        public SiteViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.site_image);
            name = (TextView) v.findViewById(R.id.site_name);
            num_plants = (TextView) v.findViewById(R.id.site_num_plants);
            link = (TextView) v.findViewById(R.id.site_edit_link);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.editTextViewOnClick(v,getAdapterPosition());
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.cardViewOnClick(v,getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerAdapterSiteListener{

        void editTextViewOnClick(View v, int position);

        void cardViewOnClick(View v, int position);

    }

}
