package com.example.marko.cargo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.Izdelek;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

/**
 * Created by marko on 30. 05. 2017.
 */

public class ListAdapter extends ArrayAdapter<Izdelek> {
    public ListAdapter(Context context, List<Izdelek> izdelki) {
        super(context, 0, izdelki);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Izdelek izd = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.podrobno_vrstica, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.nazivIzdelka);
        TextView tvHome = (TextView) convertView.findViewById(R.id.cenaIzdelka);
        TextView tvKolicina = (TextView) convertView.findViewById(R.id.kolicinaIzdelka);
        // Populate the data into the template view using the data object
        tvName.setText(izd.getNaziv());
        tvHome.setText(izd.getCena().toString() + "€");
        tvKolicina.setText(izd.getKolicina().toString());
        // Return the completed view to render on screen
        return convertView;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
