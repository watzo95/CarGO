package com.example.marko.cargo;

/**
 * Created by marko on 28. 12. 2017.
 */

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class TourManager {

    //List of Destinations
    private static ArrayList<Destinacija> destinations = new ArrayList<Destinacija>();

    public  static void addDestination(Destinacija destination) {
        destinations.add(destination);
    }

    //get the destination by index
    public static Destinacija getDestination(int index) {
        return (Destinacija) destinations.get(index);
    }

    public static void removeAll() {

        for (Destinacija D: destinations) {
            Marker marker = D.getMarker();
            marker.remove();
        }
        destinations = new ArrayList<Destinacija>();
    }

    public static ArrayList<Destinacija> allDestinations() {
        return destinations;
    }

    // get the number of Destinations
    public static int numberOfDestinations() {
        return destinations.size();
    }
}
