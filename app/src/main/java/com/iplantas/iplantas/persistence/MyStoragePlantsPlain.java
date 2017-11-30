package com.iplantas.iplantas.persistence;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.PlantInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vicch on 30/11/2017.
 */

public class MyStoragePlantsPlain implements MyStoragePlants{

    private Context context;
    private List<PlantInfo> list;

    public MyStoragePlantsPlain(Context context){
        this.context=context;
        this.list=new ArrayList<>();
        this.load();
        Collections.sort(list);
    }

    public List<PlantInfo> getPlantsInfo(){
        return this.list;
    }

    public PlantInfo getPlantInfoByName(String name){
        for (PlantInfo pf : this.list){
            if(pf.getName().equals(name)){
                return pf;
            }
        }
        return null;
    }

    public PlantInfo getPlantInfoByType(String type){
        for (PlantInfo pf : this.list){
            if(pf.getType().equals(type)){
                return pf;
            }
        }
        return null;
    }

    private void load(){
        InputStream inputStream = context.getResources().openRawResource(R.raw.plantas);
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        try {
            //Descartar cabecera
            br.readLine();
            while ((line = br.readLine()) != null) {
                PlantInfo p=new PlantInfo(line);
                this.list.add(p);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
