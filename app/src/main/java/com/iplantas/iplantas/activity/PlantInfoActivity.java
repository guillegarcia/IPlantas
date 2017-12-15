package com.iplantas.iplantas.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iplantas.iplantas.R;

public class PlantInfoActivity extends AppCompatActivity {
    TextView moreInfoText, plantName, addPlantToUserListText;
    ImageView plantImage;
    private static final String PLANT_NAME = "plant_name";
    private static final String ID_SITE = "idSite";
    private static final String NAME_SITE = "nameSite";
    private static final String PLANT_IMAGE = "plant_image";
    private static final String PLANT_ID = "plant_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_info_activity);

        setupMoreInfoText();
        setPlantName();
        setPalntImage();
        setupAddToUserListText();

    }

    private void setupAddToUserListText() {
        Bundle extras = getIntent().getExtras();
        final long idSite = extras.getLong(ID_SITE);
        final String nameSite = extras.getString(NAME_SITE);
        final int idSpecie = extras.getInt(PLANT_ID);
        addPlantToUserListText = (TextView)findViewById(R.id.info_plant_add_to_user_list);
        addPlantToUserListText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idSite != 0) {
                    Intent intent = new Intent(PlantInfoActivity.this, AddPlantActivity.class);
                    intent.putExtra(PLANT_NAME, plantName.getText());
                    intent.putExtra(ID_SITE, idSite);
                    intent.putExtra(NAME_SITE, nameSite);
                    intent.putExtra(PLANT_IMAGE, getIntent().getIntExtra(PLANT_IMAGE,R.mipmap.ic_launcher));
                    intent.putExtra(PLANT_ID, idSpecie);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(PlantInfoActivity.this, SitesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setPlantName() {
        plantName = (TextView)findViewById(R.id.info_plant_name);
        plantName.setText(getIntent().getStringExtra(PLANT_NAME));
    }

    private void setPalntImage(){
        plantImage = (ImageView)findViewById(R.id.info_plant_img);
        plantImage.setImageResource(getIntent().getIntExtra(PLANT_IMAGE,R.mipmap.ic_launcher));
    }

    private void setupMoreInfoText() {
        moreInfoText = (TextView)findViewById(R.id.info_plant_more_info_text);
        moreInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(intent);
            }
        });
    }

}
