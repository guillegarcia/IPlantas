package com.iplantas.iplantas;

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

import java.util.ArrayList;
import java.util.List;

public class PlantsSearchListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<String> plantsNamesArrayList = new ArrayList<>();
    private PlantSearchListAdapter adapter;

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
        adapter = new PlantSearchListAdapter(getPlantsNamesExampleList(), mListener);
    }

    private List<String> getPlantsNamesExampleList() {
        plantsNamesArrayList.add("Ficus");
        plantsNamesArrayList.add("Aspidistra");
        plantsNamesArrayList.add("Esparraguera");
        plantsNamesArrayList.add("Difenbaquia");
        plantsNamesArrayList.add("Tronco de Brasil");
        plantsNamesArrayList.add("Costilla de Adán");
        plantsNamesArrayList.add("Kentia");
        plantsNamesArrayList.add("Drácena");
        plantsNamesArrayList.add("Clemátide");
        plantsNamesArrayList.add("Buganvillas");
        plantsNamesArrayList.add("Madreselva");
        plantsNamesArrayList.add("Jazmin");
        return plantsNamesArrayList;
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
        void onListFragmentInteraction(String item);
    }
}
