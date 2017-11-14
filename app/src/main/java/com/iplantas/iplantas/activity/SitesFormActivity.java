package com.iplantas.iplantas.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.persistence.MySiteStorageSQLite;

public class SitesFormActivity extends AppCompatActivity {

    private static final int DIALOG_CLOSE_TYPE=1;
    private static final int DIALOG_DIMISS_TYPE=2;

    EditText editName;
    EditText editLat;
    EditText editLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_form);

        editName=(EditText) findViewById(R.id.form_name);
        editLat=(EditText) findViewById(R.id.form_lat);
        editLng=(EditText) findViewById(R.id.form_lng);
    }

    public void save(View view){
        String name=editName.getText().toString();
        double lat=0;
        double lng=0;
        try{
            lat=Double.parseDouble(editLat.getText().toString());
            lng=Double.parseDouble(editLng.getText().toString());
        }
        catch (NumberFormatException nfe){}

        MySiteStorageSQLite db=new MySiteStorageSQLite(this);
        if(db.insertSite(name, lat, lng)>0){
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
