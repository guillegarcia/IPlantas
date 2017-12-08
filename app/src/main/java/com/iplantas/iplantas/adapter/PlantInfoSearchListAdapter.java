package com.iplantas.iplantas.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.fragments.PlantsSearchListFragment.OnListFragmentInteractionListener;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.PlantInfo;

import java.util.ArrayList;
import java.util.List;

public class PlantInfoSearchListAdapter extends RecyclerView.Adapter<PlantInfoSearchListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private  List<PlantInfo> mValues;
    private final OnListFragmentInteractionListener mListener;
    private ValueFilter valueFilter;
    private List<PlantInfo> mStringFilterList;

    public PlantInfoSearchListAdapter(Context context, List<PlantInfo> items, OnListFragmentInteractionListener listener) {
        this.context=context;
        mValues = items;
        mStringFilterList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = position;
        holder.plantName.setText(mValues.get(pos).getName());
        holder.plantImage.setImageResource(mValues.get(pos).getImgResourceId(context));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.plantName.getText().toString(),mValues.get(pos).getImgResourceId(context), holder.plantImage );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView plantName;
        public final ImageView plantImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            plantName = (TextView) view.findViewById(R.id.plant_name);
            plantImage = (ImageView)view.findViewById(R.id.plant_image_thumbnail);
        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<PlantInfo> filterList = new ArrayList();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toString().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mValues = (List<PlantInfo>) results.values;
            notifyDataSetChanged();
        }
    }
}
