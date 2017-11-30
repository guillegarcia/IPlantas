package com.iplantas.iplantas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import com.iplantas.iplantas.activity.SitesActivity;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.PlantInfo;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStoragePlants;
import com.iplantas.iplantas.persistence.MyStoragePlantsPlain;
import com.iplantas.iplantas.persistence.MyStorageSQLite;


public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        pruebaBusqueda();

        pruebaPlantasInfo();

    }

    private void pruebaBusqueda() {
        MyStorage myPlantsStorage = new MyStorageSQLite(this);
        List<Plant> plantList = myPlantsStorage.searchPlants("");
        for (Plant plant : plantList) {
            Log.d(LOG_TAG,"Search result: "+plant.getPlantName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sitios(View view){
        Intent intent = new Intent(this, SitesActivity.class);
        startActivity(intent);
    }

    private void pruebaPlantasInfo(){
        MyStoragePlants msp=new MyStoragePlantsPlain(this);
        PlantInfo p=msp.getPlantInfoByName("Petunias");
        Log.e("Nombre",p.getName());
        Log.e("Tipo",p.getType());
        Log.e("Riego",p.getRecomendedWatering(20)+"");
        Log.e("Riego",p.getRecomendedWatering(24)+"");
        Log.e("Sol",p.getRecomendedSun(20)+"");
        Log.e("Sol",p.getRecomendedSun(24)+"");
        Log.e("Soil",p.getRecomendedSoil()+"");
        Log.e("Prune",p.getRecomendedPrune()+"");
        Log.e("Flowering",p.getRecomendedFlowering()+"");
    }
}
