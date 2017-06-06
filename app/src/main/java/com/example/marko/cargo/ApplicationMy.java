package com.example.marko.cargo;

import android.app.Application;
import com.example.DataAll;
import com.example.Lokacija;

import java.io.File;
import java.util.List;

/**
 * Created by marko on 29. 05. 2017.
 */

public class ApplicationMy extends Application {
    int x;
    DataAll all;
    private static final String DATA_MAP = "cargodatamap";
    private static final String FILE_NAME = "dostave.json";

    @Override
    public void onCreate() {
        super.onCreate();
        x= 5;
        if (!load()) {
            all = DataAll.scenarijA();
        }
    }

    public boolean save() {
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);

        return ApplicationJson.save(all,file);
    }
    public boolean load(){
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);
        DataAll tmp = ApplicationJson.load(file);
        if (tmp!=null) all = tmp;
        else return false;
        return true;
    }


    public int getX() {
        return x;
    }

    public DataAll getAll() {
        return  all;
    }
    public void setX(int x) {
        this.x = x;
    }

    public Lokacija getTestLocation() {
        return all.getLocation(0);
    }

    public Lokacija getLocationByID(String id) {
        return all.getLocationByID(id);
    }


    public List<Lokacija> getLokacijaAll() {
        return all.getLokacijaAll();
    }

    //public Lokacija getNewLocation(double x, double y, String filename) {
    //   return all.getNewLocation(x,y, filename);
    //}

    /*public boolean save() {
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);

        return ApplicationJson.save(all,file);
    }
    public boolean load(){
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);
        DataAll tmp = ApplicationJson.load(file);
        if (tmp!=null) all = tmp;
        else return false;
        return true;
    }*/
}
