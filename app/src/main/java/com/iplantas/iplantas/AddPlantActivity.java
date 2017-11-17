package com.iplantas.iplantas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Fernando on 17/11/2017.
 */

public class AddPlantActivity  extends AppCompatActivity {
    private TextView addPlantTitle;
    private TextView addPlantLabelPlace;
    private TextView addPlantNamePlace;
    private TextView addPlantLabelSpecies;
    private TextView addPlantNameSpecies;
    private TextView addPlantLabelName;
    private EditText addPlantNameName;
    private TextView addPlantLabelWater;
    private DatePicker addPlantLastWatered;
    private static final String PLANT_NAME = "plant_name";
    private static final String initial_date = "01-January-2015";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addPlantTitle = (TextView) findViewById(R.id.add_plant_title);
        addPlantLabelPlace = (TextView) findViewById(R.id.add_plant_label_place);
        addPlantNamePlace = (TextView) findViewById(R.id.add_plant_name_place);
        addPlantLabelSpecies = (TextView) findViewById(R.id.add_plant_label_species);
        addPlantNameSpecies = (TextView) findViewById(R.id.add_plant_name_species);
        addPlantLabelName = (TextView) findViewById(R.id.add_plant_label_name);
        addPlantNameName = (EditText) findViewById(R.id.add_plant_name_name);
        addPlantLabelWater = (TextView) findViewById(R.id.add_plant_label_water);
        addPlantLastWatered = (DatePicker) findViewById(R.id.add_plant_last_watered);
        Bundle extras = getIntent().getExtras();
        String species = extras.getString(PLANT_NAME);
        addPlantNameSpecies.setText(species);
        addPlantNameName.setText(species);
        addPlantLastWatered.setMinDate(System.currentTimeMillis());
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

}
