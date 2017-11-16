package com.iplantas.iplantas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MySiteStorage;
import com.iplantas.iplantas.persistence.MySiteStorageSQLite;

public class SitesFormActivity extends AppCompatActivity {

    private static final int DIALOG_CLOSE_TYPE=1;
    private static final int DIALOG_DIMISS_TYPE=2;

    private Site site;
    private EditText editName;
    private EditText editLat;
    private EditText editLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        long id = getIntent().getLongExtra("id",0);

        editName=(EditText) findViewById(R.id.form_name);
        editLat=(EditText) findViewById(R.id.form_lat);
        editLng=(EditText) findViewById(R.id.form_lng);

        this.loadSite(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadSite(long id){
        if(id>0) {
            MySiteStorage db = new MySiteStorageSQLite(this);
            this.site = db.getSiteById(id);
        }
        else{
            this.site=Site.SiteBuilder.site().build();
        }

        editName.setText(this.site.getName());
        editLat.setText(this.site.getLat()+"");
        editLng.setText(this.site.getLng()+"");
    }

    public void save(View view){
        this.site.setName(editName.getText().toString());
        try{
            double lat=Double.parseDouble(editLat.getText().toString());
            double lng=Double.parseDouble(editLng.getText().toString());
            this.site.setLat(lat);
            this.site.setLng(lng);
        }
        catch (NumberFormatException nfe){}

        MySiteStorageSQLite db=new MySiteStorageSQLite(this);
        if(db.insertSite(this.site.getName(), this.site.getLat(), this.site.getLng())>0){
            alert("Guardado","Sitio guardado correctamente",DIALOG_CLOSE_TYPE);
        }
        else{
            alert("Error","Error al guardar",DIALOG_DIMISS_TYPE);
        }

    }

    public void erase(View view){
        editName.setText("");
        editLat.setText("");
        editLng.setText("");
    }

    private void alert(String title, String message, final int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(type==DIALOG_CLOSE_TYPE) {
                    finishActivity();
                }
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private void finishActivity(){
        finish();
    }
}
