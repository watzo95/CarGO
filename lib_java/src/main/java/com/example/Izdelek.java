package com.example;

import java.util.UUID;

/**
 * Created by marko on 29. 05. 2017.
 */

public class Izdelek {
    String id;
    String naziv;
    Double cena;
    int kolicina;

    public Izdelek(String n, Double c, int k) {
        this.id = UUID.randomUUID().toString().replaceAll("-","");
        this.naziv = n;
        this.cena = c;
        this.kolicina = k;
    }

    public String getNaziv() {
        return naziv;
    }

    public Double getCena() {
        return cena;
    }

    public Integer getKolicina() { return kolicina;}

    @Override
    public String toString() {
        return "Izdelek{" +
                "id='" + id + '\'' +
                ", name='" + naziv + '\'' +
                ", cena=" + cena +
                ", kolicina=" + kolicina +
                '}';
    }
}
