package com.example.marko.cargo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.DataAll;
import com.example.Lokacija;

public class SeznamLokacij extends AppCompatActivity {

    ApplicationMy app;
    Bundle extras;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ApplicationMy) getApplication();
        setContentView(R.layout.activity_seznam_lokacij);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_rv);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        app = (ApplicationMy) getApplication();
        mAdapter = new DataAdapter(app.getAll(), this);
        mRecyclerView.setAdapter(mAdapter);
        // setSpinner();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lokacija l = app.getTestLocation();
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();
                Intent i = new Intent(getBaseContext(), DodajNovoLokacijo.class);
                //i.putExtra("Nekaj", extras.getString(DataAll.LOKACIJA_ID));
                startActivity(i);
            }
        });
    }


    /*listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder al= new AlertDialog.Builder(Podrobno.this);
            al.setCancelable(true);
            al.setTitle("Želite izbrisati ta izdelek?");
            al.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    adapter.remove(adapter.getItem(position));
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
    });*/

    public void onClickOpen(View view){
        // Snackbar.make(view, "Izbral si xxx:" + mySpin.getSelectedItem().toString(), Snackbar.LENGTH_LONG)
        //         .setAction("Action", null).show();
        Intent i = new Intent(getBaseContext(), Podrobno.class);
        //i.putExtra(DataAll.LOKACIJA_ID,  mySpin.getSelectedItem().toString());
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
