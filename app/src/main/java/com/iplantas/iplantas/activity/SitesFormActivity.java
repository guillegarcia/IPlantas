package com.iplantas.iplantas.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
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
    private Spinner spinnerType;
    private GoogleMap locationMap;
    private boolean locationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_form);

        long id = getIntent().getLongExtra("id", 0);

        editName = (EditText) findViewById(R.id.form_name);
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

    private void load(long id) {
        if (id > 0) {
            MyStorage db = new MyStorageSQLite(this);
            this.site = db.getSiteById(id);
        } else {
            Button b=(Button) findViewById(R.id.add_plant_cancel);
            b.setEnabled(false);
            this.site = Site.SiteBuilder.site().build();
        }

        editName.setText(this.site.getName());
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
            this.site.setType(spinnerType.getSelectedItemPosition());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        MyStorageSQLite db = new MyStorageSQLite(this);
        long res = 0;
        if (this.site.getId() == 0) {
            res = db.insertSite(site);
        } else {
            res = db.updateSite(site);
        }
        if (res > 0) {
            String toastText = getString(R.string.sitio) +" "+ this.site.getName() + " " + getString(R.string.guardado);
            Toast.makeText(SitesFormActivity.this,toastText, Toast.LENGTH_LONG).show();
            finishActivity();
        } else {
            alert(getString(R.string.error), getString(R.string.error_guardar), DIALOG_DIMISS_TYPE);
        }
    }

    public void delete() {
        MyStorageSQLite db = new MyStorageSQLite(this);
        if (db.deleteSite(this.site.getId()) > 0) {
            finishActivity();
        } else {
            alert(getString(R.string.error), getString(R.string.no_posible_eliminar_sitio), DIALOG_DIMISS_TYPE);
        }
    }

    private void alert(String title, String message, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message);
        builder.setNeutralButton(R.string.aceptar, new DialogInterface.OnClickListener() {
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
        builder.setTitle(R.string.confirmacion)
                .setMessage(R.string.confirmacionBorrarSitio);
        builder.setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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

        int zoom=2;
        LatLng startPositionLatLng = new LatLng(40.4167278, -3.7033387000000175);
        if(site.getLng() != Site.EMPTY_LNG && site.getLat() != Site.EMPTY_LAT){
            startPositionLatLng = new LatLng(site.getLat(),site.getLng());
            locationMap.addMarker(new MarkerOptions().position(startPositionLatLng));
            zoom=15;
        }

        locationMap.moveCamera(CameraUpdateFactory.newLatLng(startPositionLatLng));
        locationMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));

        //Click event
        locationMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        //Save selected position
        this.site.setLat(latLng.latitude);
        this.site.setLng(latLng.longitude);

        locationMap.clear();
        locationMap.addMarker(new MarkerOptions().position(latLng));
    }


}
