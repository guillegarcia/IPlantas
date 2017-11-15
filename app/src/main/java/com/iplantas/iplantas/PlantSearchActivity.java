package com.iplantas.iplantas;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlantSearchActivity extends AppCompatActivity implements PlantsSearchListFragment.OnListFragmentInteractionListener {
    private static final String PLANT_NAME = "plant_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_search_activity);
        setFragment();
    }

    private void setFragment() {
        PlantsSearchListFragment fragment = new PlantsSearchListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(String plantName) {
        Intent intent = new Intent(PlantSearchActivity.this, PlantInfoActivity.class);
        intent.putExtra(PLANT_NAME, plantName);
        startActivity(intent);
    }
}
