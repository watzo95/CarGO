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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DataAll;
import com.example.Lokacija;

public class SeznamLokacij extends AppCompatActivity {

    ApplicationMy app;
    DataAll da;
    Bundle extras;
    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;
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

        FloatingActionButton preglej = (FloatingActionButton) findViewById(R.id.preglej);
        preglej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), PregledLokacij.class);
                startActivity(i);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                    AlertDialog.Builder builder = new AlertDialog.Builder(SeznamLokacij.this); //alert for confirm to delete
                    builder.setMessage("Ste prepričani, da želite izbrisati lokacijo?");    //set message
                    builder.setCancelable(true);
                    builder.setPositiveButton("Odstrani", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //mAdapter.notifyItemRemoved(position);    //item removed from recylcerview
                            //sqldatabase.execSQL("delete from " + TABLE_NAME + " where _id='" + (position + 1) + "'"); //query for delete
                            //list.remove(position);  //then remove item
                            mAdapter.remove(position);
                            app.save();
                            return;
                        }
                    }).show();  //show alert dialog
                }
                /*else if(direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SeznamLokacij.this); //alert for confirm to delete
                    builder.setMessage("Preimenujte izbrano lokacijo:");    //set message
                    builder.setCancelable(true);
                    builder.setPositiveButton("Shrani", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //mAdapter.notifyItemRemoved(position);    //item removed from recylcerview
                            //sqldatabase.execSQL("delete from " + TABLE_NAME + " where _id='" + (position + 1) + "'"); //query for delete
                            //list.remove(position);  //then remove item
                            //mAdapter.remove(position);
                            return;
                        }
                    }).show();  //show alert dialog
                }*/
            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(simpleCallback);
        ith.attachToRecyclerView(mRecyclerView);
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
