package com.iplantas.iplantas.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.persistence.SitiosDB;

public class SitiosFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitios_form);
    }

    public void guardar(View view){
        EditText editNombre=(EditText) findViewById(R.id.form_nombre);
        EditText editLat=(EditText) findViewById(R.id.form_lat);
        EditText editLng=(EditText) findViewById(R.id.form_lng);

        String nombre=editNombre.getText().toString();
        double lat=0;
        double lng=0;
        try{
            lat=Double.parseDouble(editLat.getText().toString());
            lng=Double.parseDouble(editLng.getText().toString());
        }
        catch (NumberFormatException nfe){}

        SitiosDB db=new SitiosDB(this);
        if(db.insertar(nombre, lat, lng)>0){
            alert("Guardado","Sitio guardado correctamente");
        }
        else{
            error("Error","Error al guardar");
        }

    }

    private void alert(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishActivity();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private void error(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private void finishActivity(){
        finish();
    }
}
