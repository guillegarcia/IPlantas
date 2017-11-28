package com.iplantas.iplantas.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Plant;

import java.util.ArrayList;
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

    public ListUserPlantAdapter(Context context, List<Plant> list) {
        this.list = list;
        this.context = context;
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
        Boolean thereIsConnection = true;
        final Plant objIncome = list.get(position);
        //String principalTextPlant = objIncome.getPlantName();
        //holder.userListPlantName.setText(objIncome.getPlantName());
        String principalTextPlant = objIncome.getIdPlant() + " - " + objIncome.getPlantName();
        holder.userListPlantName.setText(principalTextPlant);
        String infoPlant = objIncome.getPlantLastWatered().toString();
        holder.userListPlantInfo.setText(infoPlant);

        thereIsConnection = isNetworkConnected();
        if(thereIsConnection) {
/*            imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }

                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }
            });
            imageLoader.get(objIncome.getPlantImageUrl(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    holder.userListPlantImage.setImageBitmap(bitmap);
                    holder.userListPlantImage.invalidate();
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.userListPlantImage.setImageResource(R.drawable.img_plant_two);
                }
            });*/
            holder.userListPlantImage.setImageResource(R.drawable.img_plant_two);
        }else{
            holder.userListPlantImage.setImageResource(R.drawable.img_plant_two);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userListPlantName;
        public TextView userListPlantInfo;
        public ImageView userListPlantImage;

        ViewHolder(View itemView) {
            super(itemView);
            userListPlantName = (TextView) itemView.findViewById(R.id.list_plant_name);
            userListPlantInfo = (TextView) itemView.findViewById(R.id.list_plant_text);
            userListPlantImage = (ImageView) itemView.findViewById(R.id.list_plant_image);
        }
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}