package com.example;
import com.example.Izdelek;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.UUID;

public class Lokacija {
    String id;
    String name;
    double x, y; //GPS
    String idUser; //idUser
    String fileName;
    long date;
    List<Izdelek> izdelki = new ArrayList<Izdelek>();
    Izdelek izdelek;

    public Lokacija(String name, long x, long y, String idUser, String fileName, long date, Izdelek izd) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.name = name;
        this.x = x;
        this.y = y;
        this.idUser = idUser;
        this.fileName = fileName;
        this.date = date;
        izdelki.add(izd);
    }

    @Override
    public String toString() {
        return "Lokacija{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", vzdevek='" + idUser + '\'' +
                ", fileName='" + fileName + '\'' +
                ", date=" + date +
                '}';
    }

    public void vstavi(Izdelek i) {
        izdelki.add(i);
    }

    public String getId() {
        return id;
    }

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izde) {
        this.izdelki = izde;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
