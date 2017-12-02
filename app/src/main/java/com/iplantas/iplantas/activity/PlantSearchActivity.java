package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.iplantas.iplantas.fragments.PlantsSearchListFragment;
import com.iplantas.iplantas.R;

public class PlantSearchActivity extends AppCompatActivity implements PlantsSearchListFragment.OnListFragmentInteractionListener {
    private static final String ID_SITE = "idSite";
    private static final String NAME_SITE = "nameSite";
    private static final String PLANT_NAME = "plant_name";
    private static final String PLANT_IMAGE = "plant_image";
    private long idSite;
    private String nameSite;
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
        Bundle extras = getIntent().getExtras();
        idSite = extras.getLong(ID_SITE);
        nameSite = extras.getString(NAME_SITE);
    }

    @Override
    public void onListFragmentInteraction(String plantName, int plantImage, View viewForAnimation) {
        Intent intent = new Intent(PlantSearchActivity.this, PlantInfoActivity.class);
        intent.putExtra(PLANT_NAME, plantName);
        intent.putExtra(ID_SITE, idSite);
        intent.putExtra(NAME_SITE, nameSite);
        intent.putExtra(PLANT_IMAGE, plantImage);


        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(PlantSearchActivity.this, new Pair<View, String>(viewForAnimation, getString(R.string.transition_name_plant_img)));
        ActivityCompat.startActivity(PlantSearchActivity.this, intent, options.toBundle());

        //startActivity(intent);
        //finish();
    }
}
