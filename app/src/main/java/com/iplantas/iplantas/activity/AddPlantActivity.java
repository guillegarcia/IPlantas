package com.iplantas.iplantas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Created by Fernando on 17/11/2017.
 */

public class AddPlantActivity  extends AppCompatActivity {
    private TextView addPlantTitle;
    private TextView addPlantLabelPlace;
    private TextView addPlantNamePlace;
    private TextView addPlantLabelSpecies;
    private TextView addPlantNameSpecies;
    //private TextView addPlantLabelName;
    private EditText addPlantNameName;
    private TextView addPlantLabelWater;
    private SeekBar addPlantLastWatered;
    private TextView addPlantLastWateredNumber;
    private Button addPlantAccept;
    private Button addPlantCancel;
    private Date pickedDate;
    private LocalDate localDate;
    private DateTimeFormatter dtf;
    private DatePicker.OnDateChangedListener mOnDateChangedListener;
    private static final String PLANT_NAME = "plant_name";
    private static final String ID_SITE = "idSite";
    private static final String NAME_SITE = "nameSite";
    private static final String initial_date = "01-January-2015";
    private int positionDays;
    private long idSite;
    private String nameSite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        String species = extras.getString(PLANT_NAME);
        idSite = extras.getLong(ID_SITE);
        nameSite = extras.getString(NAME_SITE);
        addPlantTitle = (TextView) findViewById(R.id.add_plant_title);
        addPlantLabelPlace = (TextView) findViewById(R.id.add_plant_label_place);
        addPlantNamePlace = (TextView) findViewById(R.id.add_plant_name_place);
        addPlantLabelSpecies = (TextView) findViewById(R.id.add_plant_label_species);
        addPlantNameSpecies = (TextView) findViewById(R.id.add_plant_name_species);
        //addPlantLabelName = (TextView) findViewById(R.id.add_plant_label_name);
        addPlantNameName = (EditText) findViewById(R.id.add_plant_name_name);
        addPlantLabelWater = (TextView) findViewById(R.id.add_plant_label_water);
        addPlantLastWatered = (SeekBar) findViewById(R.id.add_plant_last_watered);
        addPlantLastWateredNumber = (TextView) findViewById(R.id.add_plant_label_water_number);
        addPlantAccept = (Button) findViewById(R.id.add_plant_accept);
        addPlantCancel = (Button) findViewById(R.id.add_plant_cancel);
        addPlantAccept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!String.valueOf(addPlantNameName.getText()).trim().equals("")){
                    // Code here executes on main thread after user presses button
                    MyStorage myPlantsStorage = new MyStorageSQLite(AddPlantActivity.this);
                    Plant myPlant = new Plant.PlantBuilder()
                            .withIdPlace(idSite)
                            .withPlantName(String.valueOf(addPlantNameName.getText()))
                            .withPlantLastWatered(pickedDate)
                            .buildPlant();
                    String toastText = "Planta - " +  myPlant.getPlantName() + " - insertada.";
                    Toast.makeText(AddPlantActivity.this,toastText, Toast.LENGTH_LONG).show();
                    myPlantsStorage.addPlant(myPlant);
                    onBackPressed();
                }else{
                    new AlertDialog.Builder(AddPlantActivity.this)
                            .setTitle(getResources().getString(R.string.title_dialog_no_name_plant))
                            .setMessage(getResources().getString(R.string.content_dialog_no_name_plant))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });
        addPlantCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddPlantActivity.this, PlantSearchActivity.class);
                intent.putExtra(ID_SITE, idSite);
                intent.putExtra(NAME_SITE, nameSite);
                startActivity(intent);
                finish();
            }
        });
        addPlantNamePlace.setText(nameSite);
        addPlantNameSpecies.setText(species);
        addPlantNameName.setText(species);
        addPlantLastWateredNumber.setText("0");

        pickedDate = new Date(System.currentTimeMillis());
        //dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //localDate = LocalDate.now();
        positionDays = 0;

        addPlantLastWatered.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                positionDays = progress;
                String myProgress = String.valueOf(progress);
                addPlantLastWateredNumber.setText(myProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0)
            {
                //localDate = LocalDate.now();
                //localDate = localDate.minusDays(positionDays);
                pickedDate = new Date(System.currentTimeMillis());
                pickedDate = addSubstractHoursToDate(new Date(System.currentTimeMillis()),positionDays*-1);
                //String stringForToast = "Mi fecha es - " + localDate.toString() + "-.";
                String stringForToast2 = "Mi date es - " + pickedDate.toString() + "-.";
                //Log.d("Probando la fecha: ",stringForToast);
                Log.d("Probando el Date: ",stringForToast2);
                //Toast.makeText(AddPlantActivity.this, stringForToast,Toast.LENGTH_LONG).show();
                //Toast.makeText(AddPlantActivity.this, stringForToast2,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Date addSubstractHoursToDate(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_MONTH, days);  // numero de horas a añadir, o restar en caso de horas<0
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }
}
