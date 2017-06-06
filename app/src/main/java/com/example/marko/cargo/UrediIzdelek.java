package com.example.marko.cargo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DataAll;
import com.example.Izdelek;
import com.example.Lokacija;

/**
 * Created by marko on 5. 06. 2017.
 */

public class UrediIzdelek extends AppCompatActivity {
    public static String NEW_LOCATION_ID="NEW_LOCATION";
    String ID;
    ApplicationMy app;
    TextView loc;
    Lokacija l;
    boolean stateNew;
    Izdelek izd;
    DataAll da;
    EditText name;
    EditText quantity;
    EditText price;
    Button dodaj;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uredi_izdelek);
        app = (ApplicationMy) getApplication();
        name = (EditText) findViewById(R.id.editText2);
        quantity = (EditText) findViewById(R.id.editText5);
        price = (EditText) findViewById(R.id.editText6);
        dodaj = (Button) findViewById(R.id.button);
        stateNew = false;
        context = this;

        Intent i = getIntent();

        Toast.makeText(context, getIntent().getExtras().getString("Nekaj"),Toast.LENGTH_SHORT).show();
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                String lol = extras.getString("Nekaj");
                Lokacija l = app.getLocationByID(lol);
                l.vstavi(new Izdelek(name.getText().toString(), Double.parseDouble(quantity.getText().toString()), Integer.parseInt(price.getText().toString())));
                Toast.makeText(context,"Izdelek je bil uspešno shranjen.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
