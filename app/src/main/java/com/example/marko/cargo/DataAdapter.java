package com.example.marko.cargo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.DataAll;
import java.util.List;

import com.example.Izdelek;
import com.example.Lokacija;
import android.widget.ImageView;

/**
 * Created by marko on 29. 05. 2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    DataAll all;
    Activity ac;
    List<Izdelek> izdelki;
    private List<Lokacija> myDataRV = new ArrayList<>();

    public DataAdapter(DataAll all, Activity ac) {
        super();
        this.all = all;
        this.ac = ac;
    }
    public DataAdapter(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView im;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            im = (ImageView) v.findViewById(R.id.icon);
        }

        /*public void add(int position, Lokacija item) {
            myDataRV.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(Lokacija item) {
            int position = myDataRV.indexOf(item);
            myDataRV.remove(position);
            notifyItemRemoved(position);
        }*/
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vrstica, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    private static void startDView(String lokacijaID, Activity ac) {
        //  System.out.println(name+":"+position);
        Intent i = new Intent(ac.getBaseContext(), Podrobno.class); //CARE
        i.putExtra(DataAll.LOKACIJA_ID,  lokacijaID);
        ac.startActivity(i);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Lokacija nov = all.getLocation(position);
        final String name = nov.getName();
        holder.txtHeader.setText(name+" "+nov.getIdUser());
        holder.txtFooter.setText(nov.getX()+","+nov.getY());

        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataAdapter.startDView(nov.getId(),ac);
            }
        });
        holder.txtFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataAdapter.startDView(nov.getId(),ac);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return all.getLocationSize();
    }
}
