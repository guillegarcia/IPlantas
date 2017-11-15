package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.adapter.RecyclerAdapterSite;
import com.iplantas.iplantas.listener.RecyclerItemClickListener;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MySiteStorage;
import com.iplantas.iplantas.persistence.MySiteStorageSQLite;

import java.util.List;

public class SitesActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

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
        final List<Site> sites=db.getSites();

        recycler = (RecyclerView) findViewById(R.id.recycler_sites);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new RecyclerAdapterSite(this,sites);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(SitesActivity.this,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Site site=sites.get(position);
                        openSite(site.getId());
                    }
                })
        );

    }

    private void openSite(long id){
        Intent intent=new Intent(this,SitesFormActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
