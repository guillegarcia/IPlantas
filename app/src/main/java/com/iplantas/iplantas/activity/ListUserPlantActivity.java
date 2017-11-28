package com.iplantas.iplantas.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.iplantas.iplantas.R;
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
    private String place;
    private MyStorage myStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_user_plants_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        place = extras.getString("place");
        myStorage = new MyStorageSQLite(getApplicationContext());
        if (place.equals("")) {
            listPlant = myStorage.listOfPlants();
        }else{
            listPlant = myStorage.listOfPlants(place);
        }
        Iterator itrNot = listPlant.iterator();

    }
}
