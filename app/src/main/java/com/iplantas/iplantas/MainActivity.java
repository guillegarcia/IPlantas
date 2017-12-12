package com.iplantas.iplantas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import com.iplantas.iplantas.activity.ListUserPlantActivity;
import com.iplantas.iplantas.activity.PlantSearchActivity;
import com.iplantas.iplantas.activity.SitesActivity;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.PlantInfo;
import com.iplantas.iplantas.model.RateMyApp;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStoragePlants;
import com.iplantas.iplantas.persistence.MyStoragePlantsPlain;
import com.iplantas.iplantas.persistence.MyStorageSQLite;


public class MainActivity extends AppCompatActivity {
    CardView cardViewSitios, cardViewSearch;
    Button share_button;

    private final static String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardViewSearch = (CardView)findViewById(R.id.cardview_search);
        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlantSearchActivity.class);
                //Intent intent = new Intent(MainActivity.this, ListUserPlantActivity.class);
                intent.putExtra("idSite",0);
                intent.putExtra("nameSite","");
                startActivity(intent);


            }
        });
        cardViewSitios = (CardView)findViewById(R.id.cardview_sitios);
        cardViewSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sitios(null);
            }
        });

        share_button = (Button) findViewById(R.id.share_button);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartirApp("Prueba esta interesante aplicación para cuidar tus plantas: " +
                        "http://play.google.com/store/apps/details?id=" +
                                getPackageName());
            }
        });
        new RateMyApp(this).app_launched();
        //pruebaBusqueda();
        pruebaPlantasInfo();

    }

    private void pruebaBusqueda() {
        MyStorage myPlantsStorage = new MyStorageSQLite(this);
        List<Plant> plantList = myPlantsStorage.searchPlants("");
        for (Plant plant : plantList) {
            Log.d(LOG_TAG,"Search result: "+plant.getPlantName());
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */

    /*
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
    */

    public void sitios(View view){
        Intent intent = new Intent(this, SitesActivity.class);
        startActivity(intent);
    }

    private void pruebaPlantasInfo(){

        MyStoragePlants msp=new MyStoragePlantsPlain(this);
        //PlantInfo p=msp.getPlantInfoByName("Lavanda");
        PlantInfo p=msp.getPlantInfoById(4);
        Log.e("ID",p.getId()+"");
        Log.e("Nombre",p.getName());
        Log.e("Tipo",p.getType());
        Log.e("Familia",p.getFamily());
        Log.e("Riego",p.getRecomendedWatering(40)+"");
        Log.e("Sol",p.getRecomendedSun(40)+"");
        Log.e("Abono",p.getRecomendedSoil()+"");
        Log.e("Tipo abono",p.getSoilType());
        Log.e("Poda",p.getRecomendedPrune()+"");
        Log.e("Florece",p.getRecomendedFlowering()+"");
        Log.e("Rango Temp", p.getOptimalTemp(PlantInfo.MIN)+" - "+p.getOptimalTemp(PlantInfo.MAX));
        Log.e("Localizacion",p.getLocation());
        Log.e("Url",p.getUrl());
        Log.e("Image",p.getImg());

        Log.e("Proximo riego", p.getNextWateringDate(40).toString());//Proximo riego si la regamos ahora
        Log.e("Proximo riego", p.getNextWateringDateFormat(40).toString());//Proximo riego si la regamos ahora
        Log.e("Proximo abonoado", p.getNextSoilDate(40).toString());//Proximo abono si la abonamos ahora
        Log.e("Proximo abonoado", p.getNextSoilDateFormat(40).toString());//Proximo abono si la abonamos ahora

        /*
        Log.e("Ecuador",p.getNextWateringDate(30.0f)+"");
        Log.e("Casablanca",p.getNextWateringDate(33.0f)+"");
        Log.e("Madrid",p.getNextWateringDate(40.0f)+"");
        Log.e("Paris",p.getNextWateringDate(48.0f)+"");
        Log.e("Berlin",p.getNextWateringDate(48.0f)+"");
        Log.e("Glasgow",p.getNextWateringDate(55.0f)+"");
        Log.e("Islandia",p.getNextWateringDate(64.0f)+"");

        Log.e("Bogota",p.getNextWateringDate(4.0f)+"");
        Log.e("Sao Paulo",p.getNextWateringDate(-23.0f)+"");
        Log.e("Buenos Aires",p.getNextWateringDate(-34.0f)+"");
        Log.e("Patagonia",p.getNextWateringDate(-54.0f)+"");
        */
    }

    public void compartirApp(String texto) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(Intent.createChooser(i, "Selecciona aplicación"));
    }
}
