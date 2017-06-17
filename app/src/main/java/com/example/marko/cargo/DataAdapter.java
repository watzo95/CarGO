package com.example.marko.cargo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
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
    public List<Izdelek> izdelki;
    public static SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    Date datum = new Date(System.currentTimeMillis());
    private List<Lokacija> myDataRV = new ArrayList<>();

    public DataAdapter(DataAll all, Activity ac) {
        super();
        this.all = all;
        this.ac = ac;
    }
    public DataAdapter(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }

    public Double sestejSkupnoCenoNarocila(List<Izdelek> izdelki) {
        double total = 0.0;
        String end;
        for(int i = 0; i < izdelki.size(); i++) {
            total += Double.parseDouble(izdelki.get(i).getCena().toString()) * Double.parseDouble(izdelki.get(i).getKolicina().toString());
        }
        return total;
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

    public void remove(int position) {
        all.zbrisiLokacijo(position);
        this.notifyDataSetChanged();
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

        Double sest = sestejSkupnoCenoNarocila(all.vrniIzdelkeNaLokaciji(position));

        if(sest < 100) {
            holder.im.setImageResource(R.drawable.small);
        }
        else if(sest > 100) {
            holder.im.setImageResource(R.drawable.big);
        }

        final Lokacija nov = all.getLocation(position);
        final String name = nov.getName();
        holder.txtHeader.setText(name);
        holder.txtFooter.setText(DateFormat.format("dd.MM.yyyy HH:mm:ss", new Date(nov.getDate())).toString());

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
