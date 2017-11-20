package com.iplantas.iplantas.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.persistence.MyStorage;
import com.iplantas.iplantas.persistence.MyStorageSQLite;

import java.sql.Date;

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
    private Button addPlantAccept;
    private Button addPlantCancel;
    private Date pickedDate;
    private DatePicker.OnDateChangedListener mOnDateChangedListener;
    private static final String PLANT_NAME = "plant_name";
    private static final String initial_date = "01-January-2015";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        addPlantAccept = (Button) findViewById(R.id.add_plant_accept);
        addPlantCancel = (Button) findViewById(R.id.add_plant_cancel);
        addPlantAccept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                MyStorage myPlantsStorage = new MyStorageSQLite(AddPlantActivity.this);
                Plant myPlant = new Plant.PlantBuilder().
                    withPlantName(String.valueOf(addPlantNameName.getText())).
                    withPlantLastWatered(new Date(0)).
                    buildPlant();
                Toast.makeText(AddPlantActivity.this,myPlant.toString(),Toast.LENGTH_LONG).show();
                myPlantsStorage.addPlant(myPlant);
            }
        });
        addPlantCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle extras = getIntent().getExtras();
        String species = extras.getString(PLANT_NAME);
        addPlantNameSpecies.setText(species);
        addPlantNameName.setText(species);
        addPlantLastWatered.setMinDate(System.currentTimeMillis());
/*        mOnDateChangedListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                String strFecha = "2007-12-25";
                Date fecha = null;
                try {

                    fecha = (Date) formatoDelTexto.parse(strFecha);

                } catch (ParseException ex) {

                    ex.printStackTrace();

                }

                System.out.println(fecha.toString());
                Toast.makeText(AddPlantActivity.this,fecha.toString(),Toast.LENGTH_LONG).show();
            }

            public void setOnDateChangedListener(DatePicker.OnDateChangedListener listener){
                mOnDateChangedListener = listener;
            }
        };
        addPlantLastWatered.setOnDateChangedListener(mOnDateChangedListener);*/
    }

    public void setOnDateChangedListener(DatePicker.OnDateChangedListener listener){
        mOnDateChangedListener = listener;
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
