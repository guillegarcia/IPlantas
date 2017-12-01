package com.iplantas.iplantas.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Site;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

public class SitesFormActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int DIALOG_CLOSE_TYPE = 1;//if push button Aceptar the activity close
    private static final int DIALOG_DIMISS_TYPE = 2;//if push button Aceptar the dialog hide

    private static final String LOG_TAG = "SitesFormActivity";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 111;

    private Site site;
    private EditText editName;
    //private EditText editLat;
    //private EditText editLng;
    private Spinner spinnerType;
    private GoogleMap locationMap;
    private boolean locationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_form);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        */

        long id = getIntent().getLongExtra("id", 0);

        editName = (EditText) findViewById(R.id.form_name);
        //editLat=(EditText) findViewById(R.id.form_lat);
        //editLng=(EditText) findViewById(R.id.form_lng);
        spinnerType = (Spinner) findViewById(R.id.form_type);
        String[] types = {"Vivienda", "Otra Vivienda", "Trabajo"};
        spinnerType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types));

        this.load(id);

        setupLocationMap();
    }

    private void setupLocationMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.siteLocationMap);
        mapFragment.getMapAsync(this);
    }

    /*
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
    */

    private void load(long id) {
        if (id > 0) {
            MyStorage db = new MyStorageSQLite(this);
            this.site = db.getSiteById(id);
        } else {
            this.site = Site.SiteBuilder.site().build();
        }

        editName.setText(this.site.getName());
        //editLat.setText(this.site.getLat()+"");
        //editLng.setText(this.site.getLng()+"");
        spinnerType.setSelection(this.site.getType());
    }

    public void save(View v) {
        this.site.setName(editName.getText().toString());
        if (this.site.getName().length() == 0) {
            alert(getString(R.string.advertencia), getString(R.string.faltaNombreSitio), DIALOG_DIMISS_TYPE);
            return;
        }
        Log.d(LOG_TAG, "LAT=" + site.getLat());
        Log.d(LOG_TAG, "LNG=" + site.getLng());
        if (site.getLat() == Site.EMPTY_LAT || site.getLng() == Site.EMPTY_LNG) {
            alert(getString(R.string.advertencia), getString(R.string.faltaLocalizacion), DIALOG_DIMISS_TYPE);
            return;
        }
        try {
            //double lat=Double.parseDouble(editLat.getText().toString());
            //double lng=Double.parseDouble(editLng.getText().toString());
//            this.site.setLat(0);
//            this.site.setLng(0);

            //TODO: Check lat/long

            this.site.setType(spinnerType.getSelectedItemPosition());
        } catch (NumberFormatException nfe) {
        }

        MyStorageSQLite db = new MyStorageSQLite(this);
        long res = 0;
        if (this.site.getId() == 0) {
            res = db.insertSite(site);
        } else {
            res = db.updateSite(site);
        }
        if (res > 0) {
            //alert("Guardado","Sitio guardado correctamente",DIALOG_CLOSE_TYPE);
            String toastText = "Sitio " + this.site.getName() + " guardado.";
            Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
            finishActivity();
        } else {
            alert("Error", "Error al guardar", DIALOG_DIMISS_TYPE);
        }
    }

    public void delete() {
        MyStorageSQLite db = new MyStorageSQLite(this);
        if (db.deleteSite(this.site.getId()) > 0) {
            finishActivity();
        } else {
            alert("Error", "No se ha podido eliminar el sitio", DIALOG_DIMISS_TYPE);
        }
    }

    private void alert(String title, String message, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (type == DIALOG_CLOSE_TYPE) {
                    finishActivity();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void confirm(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación")
                .setMessage("¿Seguro que deseas borrar el sitio?");
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

    private void finishActivity() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.locationMap = googleMap;

        //Initial position
        LatLng startPositionLatLng = new LatLng(40.4167278, -3.7033387000000175);
        if(site.getLng() != Site.EMPTY_LNG && site.getLat() != Site.EMPTY_LAT){
            startPositionLatLng = new LatLng(site.getLat(),site.getLng());
            locationMap.addMarker(new MarkerOptions().position(startPositionLatLng));
        }

        locationMap.moveCamera(CameraUpdateFactory.newLatLng(startPositionLatLng));
        locationMap.moveCamera(CameraUpdateFactory.zoomTo(15));

//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationMap.setMyLocationEnabled(true);

        //Click event
        locationMap.setOnMapClickListener(this);


    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(LOG_TAG,"LAT: "+latLng.latitude);
        Log.d(LOG_TAG,"LNG: "+latLng.longitude);

        //Save selected position
        this.site.setLat(latLng.latitude);
        this.site.setLng(latLng.longitude);

        locationMap.clear();
        locationMap.addMarker(new MarkerOptions().position(latLng));
    }


}
