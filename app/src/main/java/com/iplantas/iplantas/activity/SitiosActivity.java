package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.adapter.RecyclerAdapterSitio;
import com.iplantas.iplantas.model.Sitio;
import com.iplantas.iplantas.persistence.SitiosDB;

import java.util.ArrayList;
import java.util.List;

public class SitiosActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                abrirSitio();
            }
        });

        /*List<Sitio> sitios=new ArrayList<>();
        sitios.add(new Sitio(1,"Casa",-1,-1));
        sitios.add(new Sitio(2,"Trabajo",1,25));
        sitios.add(new Sitio(3,"Apartamento Playa",125,25));*/

        SitiosDB db=new SitiosDB(this);
        //db.insertar("Casa");
        //db.insertar("Trabajo");
        //db.insertar("Apartamento playa");

        List<Sitio> sitios=db.obtenerSitios();

        recycler = (RecyclerView) findViewById(R.id.reciclador_sitios);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new RecyclerAdapterSitio(this,sitios);
        recycler.setAdapter(adapter);
    }

    private void abrirSitio(){
        Intent intent=new Intent(this,SitiosFormActivity.class);
        startActivity(intent);
    }

}
