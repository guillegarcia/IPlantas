package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.adapter.ListUserPlantAdapter;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Fernando on 28/11/2017.
 */

public class ListUserPlantActivity extends AppCompatActivity {

    private List<Plant> listPlant;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListUserPlantAdapter adapterListUserPlant;
    private String nameSite;
    private long idSite;
    private MyStorage myStorage;
    private LinearLayout linearLayoutEmptyList;
    private Button addPlantButton;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_user_plants_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        idSite = extras.getLong("idSite");
        nameSite = extras.getString("nameSite");
        myStorage = new MyStorageSQLite(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list_user_plant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchPlant(idSite,nameSite);
            }
        });
        if (nameSite.equals("")) {
            listPlant = myStorage.listOfPlants();
        }else{
            listPlant = myStorage.listOfPlants(idSite);
        }

        showTextAndButtonWhenPlantsListIsEmpty(fab);

        Iterator itrNot = listPlant.iterator();
        recyclerView = (RecyclerView) findViewById(R.id.plants_of_user_recycler_view);
        adapterListUserPlant = new ListUserPlantAdapter(this, listPlant);
        recyclerView.setAdapter(adapterListUserPlant);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterListUserPlant.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);
                String processUrlData = listPlant.get(pos).getPlantDataUrl();
                //startWebNavigatorActivity(processUrlData);
            }
        });
        adapterListUserPlant.notifyDataSetChanged();
    }

    private void showTextAndButtonWhenPlantsListIsEmpty(FloatingActionButton fab) {
        textview = (TextView)findViewById(R.id.empty_list_user_plant_text);
        addPlantButton = (Button)findViewById(R.id.empty_list_user_plant_button);
        if (listPlant.size() < 1 || listPlant == null) {
            fab.setVisibility(View.GONE);
            textview.setVisibility(View.VISIBLE);
            addPlantButton.setVisibility(View.VISIBLE);
            addPlantButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSearchPlant(idSite,nameSite);
                }
            });
        }else{
            fab.setVisibility(View.VISIBLE);
            textview.setVisibility(View.GONE);
            addPlantButton.setVisibility(View.GONE);
        }
    }

    public void openSearchPlant(long idSite,String nameSite) {
        Intent intent = new Intent(this, PlantSearchActivity.class);
        intent.putExtra("idSite", idSite);
        intent.putExtra("nameSite", nameSite);
        startActivity(intent);
    }
}
