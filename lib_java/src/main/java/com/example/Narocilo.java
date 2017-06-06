package com.example;

import java.util.ArrayList;
import java.util.UUID;
/**
 * Created by marko on 29. 05. 2017.
 */

public class Narocilo {
    String id;
    Izdelek izdelki;
    Lokacija lokacija;

    public Narocilo(String id, Izdelek i, Lokacija l) {
        this.id = UUID.randomUUID().toString().replaceAll("-","");
        this.izdelki = i;
        this.lokacija = l;
    }
}
