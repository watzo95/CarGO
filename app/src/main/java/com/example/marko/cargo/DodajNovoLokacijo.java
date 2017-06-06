package com.example.marko.cargo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Izdelek;
import com.example.Lokacija;
import com.example.marko.cargo.ApplicationMy;
import com.example.DataAll;

import java.util.Date;

/**
 * Created by marko on 5. 06. 2017.
 *
 * app = (ApplicationMy) getApplication();
 izdelek_ime = (EditText) fin
 */

public class DodajNovoLokacijo extends AppCompatActivity{
    EditText izdelek_ime;
    EditText izdelek_kolicina;
    EditText izdelek_cena;
    EditText lokacija_ime;
    Button addLoc;
    ApplicationMy app;
    DataAll da;
    Lokacija l;
    Context context;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_novo_lokacijo);
        app = (ApplicationMy) getApplication();
        da = app.all;
        context = this;
        addLoc = (Button) findViewById(R.id.button1);
        izdelek_ime = (EditText) findViewById(R.id.editText2);
        izdelek_cena = (EditText) findViewById(R.id.editText5);
        izdelek_kolicina = (EditText) findViewById(R.id.editText6);
        lokacija_ime = (EditText) findViewById(R.id.editText);

        //Toast.makeText(context, getIntent().getExtras().getString("Nekaj"),Toast.LENGTH_SHORT).show();
        addLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long d = System.currentTimeMillis();
                Izdelek joska = new Izdelek(izdelek_ime.getText().toString(), Double.parseDouble(izdelek_kolicina.getText().toString()), Integer.parseInt(izdelek_cena.getText().toString()));
                Lokacija loc = new Lokacija(lokacija_ime.getText().toString(), 2212212,113121, "", "", d, joska);
                //da.dodajLokacijo(new Lokacija(lokacija_ime.getText().toString(), 2212212,113121, "", "",System.currentTimeMillis(), new Izdelek(izdelek_ime.getText().toString(), Double.parseDouble(izdelek_kolicina.getText().toString()), Integer.parseInt(izdelek_cena.getText().toString())));
                da.dodajLokacijo(lokacija_ime.getText().toString(), 2212212,113121, "", joska);
                app.save();
                //Lokacija l = app.getLocationByID(lol);
                //l.vstavi(new Izdelek(name.getText().toString(), Double.parseDouble(quantity.getText().toString()), Integer.parseInt(price.getText().toString())));
                Toast.makeText(context,"Lokacija shranjena.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
