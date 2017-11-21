package com.iplantas.iplantas.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

public class SitesFormActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int DIALOG_CLOSE_TYPE=1;//if push button Aceptar the activity close
    private static final int DIALOG_DIMISS_TYPE=2;//if push button Aceptar the dialog hide

    private Site site;
    private EditText editName;
    private EditText editLat;
    private EditText editLng;
    private Spinner spinnerType;

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
        spinnerType=(Spinner) findViewById(R.id.form_type);
        String[] types={"Interior","Exterior","Trabajo"};
        spinnerType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types));

        this.load(id);

        setupLocationMap();
    }

    private void setupLocationMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.siteLocationMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_site, menu);
        if(this.site.getId()==0){
            menu.findItem(R.id.action_site_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                break;
            }
            case R.id.action_site_save:{
                save();
                break;
            }
            case R.id.action_site_delete:{
                confirm("Confirmación","¿Seguro que deseas borrar el sitio?");
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void load(long id){
        if(id>0) {
            MyStorage db = new MyStorageSQLite(this);
            this.site = db.getSiteById(id);
        }
        else{
            this.site=Site.SiteBuilder.site().build();
        }

        editName.setText(this.site.getName());
        editLat.setText(this.site.getLat()+"");
        editLng.setText(this.site.getLng()+"");
        spinnerType.setSelection(this.site.getType());
    }

    public void save(){
        this.site.setName(editName.getText().toString());
        if(this.site.getName().length()==0){
            alert("Advertencia","Debes indicar un nombre del sitio",DIALOG_DIMISS_TYPE);
            return;
        }
        try{
            double lat=Double.parseDouble(editLat.getText().toString());
            double lng=Double.parseDouble(editLng.getText().toString());
            this.site.setLat(lat);
            this.site.setLng(lng);
            this.site.setType(spinnerType.getSelectedItemPosition());
        }
        catch (NumberFormatException nfe){}

        MyStorageSQLite db=new MyStorageSQLite(this);
        long res=0;
        if(this.site.getId()==0){
            res=db.insertSite(site);
        }
        else{
            res=db.updateSite(site);
        }
        if(res>0){
            alert("Guardado","Sitio guardado correctamente",DIALOG_CLOSE_TYPE);
        }
        else{
            alert("Error","Error al guardar",DIALOG_DIMISS_TYPE);
        }
    }

    public void delete(){
        MyStorageSQLite db=new MyStorageSQLite(this);
        if(db.deleteSite(this.site.getId())>0){
            finish();
        }
        else{
            alert("Error","No se ha podido eliminar el sitio",DIALOG_DIMISS_TYPE);
        }
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

    private void confirm(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void finishActivity(){
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO: Funcionalidad de mapa
    }
}
