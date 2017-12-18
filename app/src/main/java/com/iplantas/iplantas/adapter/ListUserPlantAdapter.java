package com.iplantas.iplantas.adapter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.activity.ListUserPlantActivity;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.PlantInfo;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStoragePlants;
import com.iplantas.iplantas.persistence.MyStoragePlantsPlain;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fernando on 28/11/2017.
 */

public class ListUserPlantAdapter extends RecyclerView.Adapter<ListUserPlantAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Plant> list = new ArrayList<Plant>();
    protected View.OnClickListener onClickListener;
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;
    private Context context;
    List<PlantInfo> plantList;
    MyStorage ms;

    public ListUserPlantAdapter(Context context, List<Plant> list) {
        this.list = list;
        this.context = context;
        MyStoragePlants msp=new MyStoragePlantsPlain(context);
        plantList =msp.getPlantsInfo();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_user_plant_item, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
  //      Boolean thereIsConnection = true;
        final Plant objIncome = list.get(position);
        //String principalTextPlant = objIncome.getPlantName();
        //holder.userListPlantName.setText(objIncome.getPlantName());
        String principalTextPlant = objIncome.getIdPlant() + " - " + objIncome.getPlantName();
        holder.userListPlantName.setText(principalTextPlant);
        String infoPlant = objIncome.getPlantLastWatered().toString();
        holder.userListPlantInfo.setText(infoPlant);
        ms=new MyStorageSQLite(context);
        MyStoragePlantsPlain myStoragePlantsPlain = new MyStoragePlantsPlain(context);
        final PlantInfo plantInfo = myStoragePlantsPlain.getPlantInfoById(objIncome.getIdSpecies());
        int idSpecie = plantInfo.getImgResourceId(context);
        holder.userListPlantImage.setImageResource(idSpecie);

        long idPlace=list.get(position).getIdPlace();
        final Site site=ms.getSiteById(idPlace);
        final double lat=site.getLat();

        Date ultimoRiego=list.get(position).getPlantLastWatered();
        Date ultimoAbono=list.get(position).getPlantDateOfAddition();

        String proximoRiego=plantInfo.getNextWateringDateFormat(lat,ultimoRiego);
        String proximoAbono=plantInfo.getNextSoilDateFormat(lat,ultimoAbono);

        holder.textFechaRiego.setText(proximoRiego);
        holder.textFechaAbono.setText(proximoAbono);



        holder.botonRiego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date d=plantInfo.getNextWateringDate(lat);
                objIncome.setPlantLastWatered(d);
                MyStorage ms=new MyStorageSQLite(context);
                ms.updateLastWatered(objIncome);
                String s=plantInfo.getNextWateringDateFormat(lat,d);
                holder.textFechaRiego.setText(s);
            }
        });

        holder.popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(holder.itemView.getContext(), holder.popupMenu);
                popup.getMenuInflater().inflate(R.menu.menu_list_plants, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menu_edit_plant) {
                            Toast.makeText(holder.itemView.getContext(),"Planta editada",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(holder.itemView.getContext(),"Planta borrada",Toast.LENGTH_SHORT).show();
                            ms.deletePlant(objIncome);

                            Intent intent = new Intent(context, ListUserPlantActivity.class);
                            intent.putExtra("idSite", site.getId());
                            intent.putExtra("nameSite", site.getName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            context.startActivity(intent);

                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userListPlantName;
        public TextView userListPlantInfo;
        public ImageView userListPlantImage;
        public ImageButton popupMenu;

        public TextView textFechaRiego;
        public TextView textFechaAbono;
        public Button botonRiego;

        ViewHolder(View itemView) {
            super(itemView);
            userListPlantName = (TextView) itemView.findViewById(R.id.list_plant_name);
            userListPlantInfo = (TextView) itemView.findViewById(R.id.list_plant_text);
            userListPlantImage = (ImageView) itemView.findViewById(R.id.list_plant_image);
            popupMenu = (ImageButton )itemView.findViewById(R.id.plant_added_popup_button);

            textFechaRiego=(TextView) itemView.findViewById(R.id.fecha_riego);
            textFechaAbono=(TextView) itemView.findViewById(R.id.fecha_abono);
            botonRiego=(Button) itemView.findViewById(R.id.boton_riego);
        }
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
