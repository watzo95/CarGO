package com.example.marko.cargo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.DataAll;
import com.example.Izdelek;
import com.example.Lokacija;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

import static com.example.marko.cargo.DCT.filesize;
import static com.example.marko.cargo.DCT.inverseDCT;
import static com.example.marko.cargo.DCT.reverseZigZag;

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
    WebView streetView;
    DataAll da;
    boolean stateNew;
    String ID;
    Bundle extras;
    Context context;
    ListAdapter adapter;
    List<Izdelek> arrayList;
    Button zakljuci;
    public static String NEW_LOCATION_ID="NEW_LOCATION";
    private Bitmap bitmap;
    static final int CAM_REQUEST = 1;
    static final int CROP_REQUEST =2;

    private File getFile() {
        File folder = new File("sdcard/narocila");
        if(!folder.exists()) {
            folder.mkdir();
        }
        File image = new File(folder,"narocilo.jpg");
        return image;
    }

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

        streetView = (WebView) findViewById(R.id.streetView);

        setLokacija(extras.getString(DataAll.LOKACIJA_ID));

        /*DCT alg = new DCT();
        byte[] buffer = null;
        ArrayList<ArrayList<double[]>> fromFile;
        ArrayList<ArrayList<double[][]>> barve = new ArrayList<>();
        int vrstica=0;
        int stolpec=0;
        int rdeca = 0;
        int zelena = 0;
        int modra = 0;
        double[][] rdeca_param;
        double[][] zelena_param;
        double[][] modra_param;
        Color nova = new Color();
        try {
            File f = new File("sdcard/narocila/narocilo.bin");
            buffer = new byte[(int)f.length()];
            new FileInputStream(f).read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fromFile = alg.DecodeDCT(buffer);
        int ii = 0;
        while(ii<fromFile.size()){
            ArrayList<double[][]> zacasna = new ArrayList<>();
            int jj = 0;
            while(jj<fromFile.get(ii).size()){
                zacasna.add(inverseDCT(reverseZigZag(fromFile.get(ii).get(jj))));
                jj++;
            }
            barve.add(zacasna);
            ii++;
        }
        int dolzina = (int)Math.sqrt(barve.get(0).size()) * 8;
        Bitmap picture = Bitmap.createBitmap(dolzina, dolzina, Bitmap.Config.RGB_565);

        for(int jj=0; jj<barve.get(0).size(); jj++){
            rdeca_param = barve.get(0).get(jj);
            zelena_param = barve.get(1).get(jj);
            modra_param = barve.get(2).get(jj);
            for(int sirina=0; sirina<8; sirina++){
                for(int visina=0; visina<8; visina++){
                    rdeca = (int)rdeca_param[sirina][visina];
                    zelena = (int)zelena_param[sirina][visina];
                    modra = (int)modra_param[sirina][visina];
                    if(rdeca < 0){
                        rdeca = 0;
                    }
                    else if(rdeca > 255){
                        rdeca = 255;
                    }
                    if(zelena < 0){
                        zelena = 0;
                    }
                    else if(zelena > 255){
                        zelena = 255;
                    }
                    if(modra < 0){
                        modra = 0;
                    }
                    else if(modra > 255) {
                        modra = 255;
                    }
                    picture.setPixel(vrstica+sirina, stolpec+visina,Color.rgb(rdeca,zelena,modra));
                }
            }
            vrstica = vrstica + 8;
            if(vrstica == dolzina){
                vrstica = 0;
                stolpec = stolpec + 8;
            }
        }
        File f = new File("sdcard/narocila/compressed.jpg");
        //showMessageDialog(null,"Odzipano");
        try {
            OutputStream os = new FileOutputStream(f);
            picture.compress(Bitmap.CompressFormat.JPEG, 100,os);
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/

        //SLIKA GOOGLE STREET VIEW


        //LatLng koordinate = getLocationFromAddress(this,l.getName());
        //String url = "https://maps.googleapis.com/maps/api/streetview?size=300x200&location="+ koordinate.latitude + "," + koordinate.longitude + "&heading=151.78&pitch=-0.76&key=AIzaSyBI9itNcBRoKzNAli1ltHf1qvF7m2mrYKU";
        //bitmap = loadImageFromURL(url);
        //streetView.setImageBitmap(bitmap);


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

        FloatingActionButton camera = (FloatingActionButton) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(i,CAM_REQUEST);
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
        LatLng koordinate = getLocationFromAddress(this,l.getName());
        String url = "http://maps.googleapis.com/maps/api/streetview?size=325x300&location="+koordinate.latitude+","+koordinate.longitude+"&heading=151.78&pitch=-0.76&key=AIzaSyBI9itNcBRoKzNAli1ltHf1qvF7m2mrYKU";
        //Picasso.with(this).load("http://maps.googleapis.com/maps/api/streetview?size=200x300&location="+koordinate.latitude+","+koordinate.longitude+"&heading=151.78&pitch=-0.76&key=AIzaSyBI9itNcBRoKzNAli1ltHf1qvF7m2mrYKU").into(streetView);
        streetView.loadUrl(url);
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

    public Bitmap loadImageFromURL(String url) {
        //String url1 = "https://maps.googleapis.com/maps/api/streetview?size=600x300&location=46.414382,10.013988&heading=151.78&pitch=-0.76&key=AIzaSyBI9itNcBRoKzNAli1ltHf1qvF7m2mrYKU";
        try {
            URL urladd = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)urladd.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap mybit = BitmapFactory.decodeStream(input);
            return mybit;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    private void cropPic() {
        Intent crop = new Intent("com.android.camera.action.CROP");
        crop.putExtra("crop",true);
        crop.putExtra("aspectX",1);
        crop.putExtra("aspectY",1);
        crop.putExtra("outputX",1);
        crop.putExtra("outputY",1);
        crop.putExtra("return-data",true);
        startActivityForResult(crop,CROP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<double[][]> R = new ArrayList<>();
        ArrayList<double[][]> G = new ArrayList<>();
        ArrayList<double[][]> B = new ArrayList<>();
        ArrayList<ArrayList<double[][]>> all = new ArrayList<>();
        ArrayList<double[]> zaZapis = new ArrayList<>();
        DCT alg = new DCT();

        Bitmap bmp = BitmapFactory.decodeFile("sdcard/narocila/narocilo.jpg");
        Bitmap bmp1 = Bitmap.createScaledBitmap(bmp, 1024, 1024, false);

        R = alg.split(bmp1, "r");
        G = alg.split(bmp1, "g");
        B = alg.split(bmp1, "b");

        all.add(R);
        all.add(G);
        all.add(B);

        int i = 0;
        while (i < all.size()) {
            int s = all.get(i).size();
            for (int j = 0; j < s; j++) {
                all.get(i).set(j, alg.forwardDCT(all.get(i).get(j)));
            }
            i++;
        }

        for (int m = 0; m < all.size(); m++) {
            int count = 0;

            double[] ins;
            int s = all.get(m).size() * 8 * 8;
            ins = new double[s];
            int s1 = all.get(m).size();
            for (int n = 0; n < s1; n++) {
                double[][] xx = all.get(m).get(n);
                double[] bar = alg.ZigZag(xx);
                int o = 0;
                while (o < bar.length) {
                    ins[count] = bar[o];
                    count++;
                    o++;
                }
            }
            zaZapis.add(ins);
        }
        Toast.makeText(getApplicationContext(), "Size before: " + filesize + " bits", Toast.LENGTH_LONG);
        alg.EncodeDCT(zaZapis);
        Toast.makeText(getApplicationContext(), "Encoded.", Toast.LENGTH_SHORT);

    }
}
