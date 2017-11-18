package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iplantas.iplantas.PlantSearchActivity;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.adapter.RecyclerAdapterSite;
import com.iplantas.iplantas.listener.RecyclerItemClickListener;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MySiteStorage;
import com.iplantas.iplantas.persistence.MySiteStorageSQLite;

import java.util.List;

public class SitesActivity extends AppCompatActivity {

    public static final int SITE_ACTIVITY=1;

    private RecyclerView recycler;
    private RecyclerAdapterSite adapter;
    private RecyclerView.LayoutManager lManager;

    private List<Site> sites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSite(0);           }
        });

        MySiteStorage db=new MySiteStorageSQLite(this);
        sites=db.getSites();

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
                openSite(site.getId());
            }

            @Override
            public void cardViewOnClick(View v, int position) {
                Site site=sites.get(position);
                openPlant(site.getId());
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

    private void openPlant(long id){
        Intent intent=new Intent(this,PlantSearchActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    private void loadSites(){
        MySiteStorage db=new MySiteStorageSQLite(this);
        this.sites=db.getSites();
        adapter.swap(this.sites);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SITE_ACTIVITY){
            loadSites();
        }
    }

}
