package com.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by marko on 29. 05. 2017.
 */

public class DataAll {
    public static final String LOKACIJA_ID = "lokacija_idXX";
    public static SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private ArrayList<Lokacija> lokacijaList;
    private ArrayList<LokacijaTag> lokacijaTagList;
    private ArrayList<Narocilo> narocilaList;
    private ArrayList<Izdelek> izdelkiList;
    private User me;

    public DataAll() {
        me = new User("john.doe@unknown.com","undefined");
        lokacijaList = new ArrayList<>();
        lokacijaTagList = new ArrayList<>();
        narocilaList = new ArrayList<>();
        izdelkiList = new ArrayList<>();
    }

    public Lokacija getLocationByID(String ID) {
        for (Lokacija l: lokacijaList) { //TODO this solution is relatively slow! If possible don't use it!
            // if (l.getId() == ID) return l; //NAPAKA primerja reference
            if (l.getId().equals(ID)) return l;
        }
        return null;
    }

    public void zakljuciDostavo(String ID) {
        for(int i = 0; i < lokacijaList.size(); i++) {
            if(lokacijaList.get(i).getId().equals(ID)) {
                lokacijaList.remove(i);
                break;
            }
        }
    }

    public List<Izdelek> vrniIzdelkeNaLokaciji(int position) {
        Lokacija l = this.getLocation(position);
        return l.getIzdelki();
    }

    public Lokacija dodajLokacijo(String name, long x, long y, String im, Izdelek izd) {
        Lokacija tmp = new Lokacija(name, x,y, me.getIdUser(),"", System.currentTimeMillis(),izd);
        lokacijaList.add(tmp);
        return tmp;
    }

    public void odstraniLokacijo(int position) {
        lokacijaList.remove(position);
    }

    public Izdelek dodajIzdelek(String naziv, double cena, int kolicina ) {
        Izdelek tmp = new Izdelek(naziv,cena,kolicina);
        izdelkiList.add(tmp);
        return tmp;
    }

    public void addLocationTag(Lokacija l, String t) {
        lokacijaTagList.add(new LokacijaTag(l.id, t, System.currentTimeMillis(), me.getIdUser()));
    }

    public static DataAll scenarijA() {
        DataAll da = new DataAll();
        Date danes = new Date();
        da.me = new User("marko.watzak@hotmail.com","watzo");
        Lokacija tmp;
        Izdelek zac,zac1,zac2;
        zac1 = da.dodajIzdelek("Fidget Spinner",25.0,1);
        zac2 = da.dodajIzdelek("Eleaf Istick Pico", 50.0, 1);
        zac = da.dodajIzdelek("Baterija 3000mAh",11.0,11);
        tmp = da.dodajLokacijo("Kardeljev trg 3, 3320 Velenje", 1212212,23123121,"",zac);
        tmp.izdelki.add(zac1);
        tmp.izdelki.add(zac2);
        da.addLocationTag(tmp, "Nujno");
        tmp = da.dodajLokacijo("Slomskov trg 12, 2000 Maribor", 2212212,113121, "",zac1);
        da.addLocationTag(tmp, "Reklamacija");
        tmp = da.dodajLokacijo("Koroska cesta 60, 2000 Maribor", 3212212,23123121, "",zac2);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Zelezniska 2, 1230 Domzale", 3212212,23123121, "",zac1);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Toledova ulica 8, 3320 Velenje", 3212212,23123121, "",zac);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Cesta talcev 11, 3320 Velenje", 3212212,23123121, "",zac);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Cesta I/1, 3320 Velenje", 3212212,23123121, "",zac1);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Kardeljev trg 3, 3320 Velenje", 1212212,23123121,"",zac1);
        da.addLocationTag(tmp, "Nujno");
        tmp = da.dodajLokacijo("Slomskov trg 12, 2000 Maribor", 2212212,113121, "",zac);
        da.addLocationTag(tmp, "Reklamacija");
        tmp = da.dodajLokacijo("Koroska cesta 60, 2000 Maribor", 3212212,23123121, "",zac2);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Zelezniska 2, 1230 Domzale", 3212212,23123121, "",zac);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Toledova ulica 8, 3320 Velenje", 3212212,23123121, "",zac1);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Cesta talcev 11, 3320 Velenje", 3212212,23123121, "",zac2);
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Cesta I/1, 3320 Velenje", 3212212,23123121, "",zac);
        da.addLocationTag(tmp, "Pozvoni");
        /*tmp = da.dodajLokacijo("Stantetova ulica 20, 3000 Celje", 3212212,23123121, "");
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Presernova 2, 3320 Velenje", 3212212,23123121, "");
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Koroska cesta 60, 2000 Maribor", 3212212,23123121, "");
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Smetanova ulica 17, 2000 Maribor", 3212212,23123121, "");
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Trg Mladosti 3, 3320 Velenje", 3212212,23123121, "");
        da.addLocationTag(tmp, "Pozvoni");
        tmp = da.dodajLokacijo("Ulica Janka Ulriha 35, 3320 Velenje", 3212212,23123121, "");
        da.addLocationTag(tmp, "Pozvoni");*/

        return da;
    }

    public Lokacija getLocation(int i) {
        return lokacijaList.get(i);
    }

    public void zbrisiLokacijo(int i) {lokacijaList.remove(i);}

    public List<Lokacija> getLokacijaAll() {
        return lokacijaList;
    }

    //public Lokacija getNewLocation(double d1, double d2, String filename) {
    //    return addLocation(new Lokacija("N/A", d1, d2, filename));
    //}

    public int getLocationSize() {
        return lokacijaList.size();
    }

    public void addLocation(Lokacija l) {
        lokacijaList.add(l);

    }
}
