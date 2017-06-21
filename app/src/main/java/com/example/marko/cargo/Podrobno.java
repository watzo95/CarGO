package com.example.marko.cargo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.DataAll;
import com.example.Izdelek;
import com.example.Lokacija;
import com.example.marko.cargo.ApplicationMy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.example.marko.cargo.ListAdapter;
import com.example.marko.cargo.ApplicationMy;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static java.lang.System.in;

/**
 * Created by marko on 29. 05. 2017.
 */

public class Podrobno extends AppCompatActivity {
    ApplicationMy app;
    TextView loc;
    ListView listview;
    Lokacija l;
    TextView ime;
    TextView cena;
    TextView kolicina;
    EditText editIme;
    EditText editKolicina;
    EditText editCena;
    TextView skupna_cena;
    DataAll da;
    boolean stateNew;
    String ID;
    Bundle extras;
    Context context;
    ListAdapter adapter;
    List<Izdelek> arrayList;
    Button zakljuci;
    public static String NEW_LOCATION_ID="NEW_LOCATION";

    public double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public String sestejSkupnoCenoNarocila(List<Izdelek> izdelki) {
        double total = 0.0;
        String end;
        for(int i = 0; i < izdelki.size(); i++) {
            total += Double.parseDouble(izdelki.get(i).getCena().toString()) * Double.parseDouble(izdelki.get(i).getKolicina().toString());
        }

        //double nova = roundTwoDecimals(total);
        end = Double.toString(total);
        return end;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podrobno);
        app = (ApplicationMy) getApplication();
        listview = (ListView) findViewById(R.id.listview);
        skupna_cena = (TextView) findViewById(R.id.skupnaCena);
        loc = (TextView) findViewById(R.id.textView);
        stateNew = false;
        extras = getIntent().getExtras();
        context = this;
        da = app.getAll();
        setLokacija(extras.getString(DataAll.LOKACIJA_ID));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lokacija l = app.getTestLocation();
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                Intent i = new Intent(getBaseContext(), DodajNovo.class);
                i.putExtra("Nekaj", extras.getString(DataAll.LOKACIJA_ID));
                startActivity(i);
            }
        });

        zakljuci = (Button) findViewById(R.id.button2);
        zakljuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                da.zakljuciDostavo(l.getId());
                Toast.makeText(context,"Zaključeno.",Toast.LENGTH_SHORT).show();
                app.save();
                finish();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder al= new AlertDialog.Builder(Podrobno.this);
                al.setCancelable(true);
                al.setTitle("Želite izbrisati ta izdelek?");
                al.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(adapter.getItem(position));
                        app.save();
                        skupna_cena.setText("Skupna cena narocila: " + sestejSkupnoCenoNarocila(arrayList) +"€");
                        Toast.makeText(context,"Izdelek izbrisan.",Toast.LENGTH_SHORT).show();
                    }
                });
                al.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                al.show();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview.getItemAtPosition(position);
                //Intent i = new Intent(getBaseContext(), UrediIzdelek.class);
                //i.putExtra("Nekaj",position);
                showDialogBox(arrayList.get(position),position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        skupna_cena.setText("Skupna cena narocila: " + sestejSkupnoCenoNarocila(arrayList) +"€");
        adapter.refresh();
    }

    public void showDialogBox(Izdelek izd, final int position) {
        final Dialog dia = new Dialog(Podrobno.this);
        dia.setTitle("Uredi podatke o izdelku:");
        dia.setContentView(R.layout.uredi_izdelek);
        ime = (EditText)dia.findViewById(R.id.editText2);
        kolicina = (EditText)dia.findViewById(R.id.editText6);
        cena = (EditText)dia.findViewById(R.id.editText5);
        Button butt = (Button)dia.findViewById(R.id.button);
        ime.setText(izd.getNaziv());
        kolicina.setText(izd.getKolicina().toString());
        cena.setText(izd.getCena().toString());
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.set(position, new Izdelek(ime.getText().toString(), Double.parseDouble(cena.getText().toString()), Integer.parseInt(kolicina.getText().toString())));
                Toast.makeText(context,"Podatki o izdelku so bili spremenjeni.",Toast.LENGTH_SHORT).show();
                app.save();
                adapter.refresh();
                dia.dismiss();
            }
        });
        dia.show();
        //EditText editIme = (E)
    }
    /*@Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if( (extras !=null) && (!ID.equals(NEW_LOCATION_ID)))
        {
            ID = extras.getString(DataAll.LOKACIJA_ID);
            if (ID.equals(NEW_LOCATION_ID)) {
                stateNew = true;
                //dodajLokacijo();
            }else {
                stateNew = false;
                setLokacija(extras.getString(DataAll.LOKACIJA_ID));
            }
        } else {
            System.out.println("Nič ni v extras!");
        }
        //permissionGranted.checkAllMagicalCameraPermission();
        // l = app.getTestLocation();
        // update(l);
    }*/

    void setLokacija(String ID) {
        l = app.getLocationByID(ID);
        update(l);
    }

    public void update(Lokacija l) {
        //ivSlika
        loc.setText(""+l.getName());
        arrayList = new ArrayList<Izdelek>();
        arrayList = l.getIzdelki();

        /*arrayList.add(new Izdelek("Fidget Spinner", 24.95, 1));
        arrayList.add(new Izdelek("Fidget Spinner", 24.95, 2));
        arrayList.add(new Izdelek("Fidget Spinner", 24.95, 3));
        arrayList.add(new Izdelek("Eleaf Istick Pico 75W", 49.99, 2));
        arrayList.add(new Izdelek("Smok VapePen D22", 22.39, 20));
        arrayList.add(new Izdelek("Kanger SSOCC coil", 3.49, 20));
        arrayList.add(new Izdelek("Fidget Spinner", 13.99, 10));*/

        adapter = new ListAdapter(this, arrayList);

        skupna_cena.setText("Skupna cena narocila :" + sestejSkupnoCenoNarocila(arrayList) +"€");

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }
}
