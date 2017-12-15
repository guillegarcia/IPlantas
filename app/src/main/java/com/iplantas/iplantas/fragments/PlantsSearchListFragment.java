package com.iplantas.iplantas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iplantas.iplantas.OffsetDecorationRecyclerView;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.adapter.PlantInfoSearchListAdapter;
import com.iplantas.iplantas.adapter.PlantSearchListAdapter;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.PlantInfo;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStoragePlants;
import com.iplantas.iplantas.persistence.MyStoragePlantsPlain;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

import java.util.ArrayList;
import java.util.List;

public class PlantsSearchListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<String> plantsNamesArrayList = new ArrayList<>();
    //private PlantSearchListAdapter adapter;
    private PlantInfoSearchListAdapter adapter;

    public PlantsSearchListFragment() {
    }

    @SuppressWarnings("unused")
    public static PlantsSearchListFragment newInstance(int columnCount) {
        PlantsSearchListFragment fragment = new PlantsSearchListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        /*
        MyStorage myPlantsStorage = new MyStorageSQLite(getContext());
        List<Plant> plantList = myPlantsStorage.searchPlants("");
        adapter = new PlantSearchListAdapter(plantList, mListener);
        */

        MyStoragePlants msp=new MyStoragePlantsPlain(this.getContext());
        List<PlantInfo> plantList=msp.getPlantsInfo();
        adapter = new PlantInfoSearchListAdapter(this.getContext(),plantList,mListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plant_search_list_fragment, container, false);
        Context context = view.getContext();
        setupRecyclerView(view, context);
        setupSearchView(view);
        return view;
    }

    private void setupSearchView(View view) {
        final SearchView searchView = view.getRootView().findViewById(R.id.search);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void setupRecyclerView(View view, Context context) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        final int spacing = getContext().getResources()
                .getDimensionPixelSize(R.dimen.spacing_nano);
        recyclerView.addItemDecoration(new OffsetDecorationRecyclerView(spacing));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(String item, int plantImage, int plantId, View viewForAnimation);
    }
}
