package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.adapter.ListUserPlantAdapter;
import com.iplantas.iplantas.adapter.RecyclerAdapterSite;
import com.iplantas.iplantas.model.RateMyApp;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

import java.util.ArrayList;
import java.util.List;

public class SitesActivity extends AppCompatActivity {

    public static final int SITE_ACTIVITY=1;

    private RecyclerView recycler;
    private RecyclerAdapterSite adapter;
    private RecyclerView.LayoutManager lManager;
    private TextView textView;
    private List<Site> sites;
    private CoordinatorLayout.LayoutParams lp;
    private Button share_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);

        textView = (TextView)findViewById(R.id.text_add_place);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSite(0);           }
        });

        MyStorage db=new MyStorageSQLite(this);
        sites=db.getSites();
        lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        if(sites.size()==0){
            textView.setVisibility(View.VISIBLE);
            lp.anchorGravity = Gravity.CENTER;
            /*Site example= Site.SiteBuilder.site()
                    .withName("Sitio de ejemplo")
                    .withType(3)
                    .build();
            this.sites.add(example);*/
        }else{
            textView.setVisibility(View.GONE);
            lp.anchorGravity = Gravity.BOTTOM | GravityCompat.END;
        }

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


        recycler = (RecyclerView) findViewById(R.id.recycler_sites);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new RecyclerAdapterSite(this, sites, new RecyclerAdapterSite.RecyclerAdapterSiteListener() {
            @Override
            public void editTextViewOnClick(View v, int position) {
                Site site=sites.get(position);
                if(site.getType()!=Site.TYPE_EXAMPLE) {
                    openSite(site.getId());
                }
            }

            @Override
            public void cardViewOnClick(View v, int position) {
                Site site=sites.get(position);
                if(site.getType()!=Site.TYPE_EXAMPLE) {
                    openPlant(site);
                }
            }

        });
        recycler.setAdapter(adapter);

        /*recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(SitesActivity.this,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Toast.makeText(SitesActivity.this,"Hola",Toast.LENGTH_LONG).show();
                    }
                })
        );*/

    }

    private void openSite(long id){
        //Intent intent=new Intent(this,PlantSearchActivity.class);
        Intent intent=new Intent(this,SitesFormActivity.class);
        intent.putExtra("id",id);
        startActivityForResult(intent,SITE_ACTIVITY);
    }

    //Cambio este método para que me lleguen los datos a la lista de plantas del usuario
    /*private void openPlant(long id){
        Intent intent=new Intent(this,PlantSearchActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }*/
    private void openPlant(Site site){
        Intent intent=new Intent(this,ListUserPlantActivity.class);
        intent.putExtra("idSite",site.getId());
        intent.putExtra("nameSite",site.getName());
        startActivity(intent);
    }

    private void loadSites(){
        this.sites=new ArrayList<>();
        MyStorage db=new MyStorageSQLite(this);
        this.sites=db.getSites();
        adapter.swap(this.sites);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SITE_ACTIVITY){
            textView.setVisibility(View.GONE);
            lp.anchorGravity = Gravity.BOTTOM | GravityCompat.END;
            loadSites();
        }
    }


    public void compartirApp(String texto) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(Intent.createChooser(i, "Selecciona aplicación"));
    }

}
