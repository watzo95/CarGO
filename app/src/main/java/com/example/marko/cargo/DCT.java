package com.example.marko.cargo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Created by marko on 23. 09. 2018.
 */

public class DCT {

    public Bitmap img;
    public static String filename = "";
    public static int factor = 8;
    static double[][] matrikafaktorjev = new double[8][8];
    static String pot="";
    public static int N = 100000;

    int start = 15;
    int trenutni;

    public ArrayList<double[][]> split(Bitmap pic, String c) {
        ArrayList<double[][]> customized =  new ArrayList<>();
        double[][] modded = new double[pic.getHeight()][pic.getWidth()];
        for(int i=0; i<pic.getHeight(); i++){
            for(int j=0; j<pic.getWidth(); j++) {
                switch (c) {
                    case "r":
                        //red
                        modded[j][i] = (pic.getPixel(j,i) >> 16) & 0xff;
                        break;
                    case "g":
                        //green
                        modded[j][i] = (pic.getPixel(j,i) >> 8) & 0xff;
                        break;
                    case "b":
                        //blue
                        modded[j][i] = (pic.getPixel(j,i)) & 0xff;
                        break;
                }
            }
        }
        for(int i=0; i<pic.getHeight(); i = i + 8) {
            for (int j = 0; j < pic.getWidth(); j = j + 8) {
                //customized.add(cut(modded, i, j));
                double[][] im = new double[8][8];
                for(int ii= i; ii<i+8; ii++){
                    for(int jj=j; jj<j+8; jj++){
                        im[jj-j][ii-i] = modded[jj][ii];
                    }
                }
                customized.add(im);
            }
        }
        return customized;
    }

    //Cik-cak, vzet iz navodil:

    double[][] c;
    public static int filesize;

    public static double[] ZigZag(double[][]input){
        boolean konec = false;
        double[][] a = input;
        int x=0;
        int y=0;
        int indeks = 0;
        int N = 7;
        double[] matrix = new double[(N+1)*(N+1)];
        int OVERFLOW = 2033;
        while(konec == false)
        {
            matrix[indeks] = input[y][x];
            a[y][x] = OVERFLOW;
            if ((x>0) && (y<N) && (a[y+1][x-1]<OVERFLOW)) // lahko gre levo dol
            {
                x--;
                y++;
            }
            else if ((x<N) && (y>0) && (a[y-1][x+1]<OVERFLOW)) // lahko gre desno gor
            {
                x++;
                y--;
            }
            else if ((x>0) && (x<N)) // lahko gre desno in ni v 1. stolpcu
            {
                x++;
            }
            else if ((y>0) && (y<N)) // lahko gre dol in ni v 1. vrstici
            {
                y++;
            }
            else if (x<N) // lahko gre desno (in je v 1. stolpcu)
            {
                x++;
            }
            else konec=true;
            indeks++;
        }
        return matrix;
    }



    protected static double[][] forwardDCT(double[][] input) {
        int start = 15;
        int trenutni;
        for(int ii = 0; ii < 8; ii++) {
            trenutni = start;
            for(int jj = 0; jj < 8; jj++) {
                matrikafaktorjev[ii][jj] = trenutni;
                trenutni--;
            }
            start--;
        }
        double[][] output = new double[8][8];
        double c1;
        double c2;
        double summary;
        for (int u=0;u<8;u++) {
            for (int v=0;v<8;v++) {
                if(matrikafaktorjev[u][v] > factor) {
                    if(u==0){
                        c1 = 1/Math.sqrt(2);
                    }
                    else {
                        c1 = 1;
                    }
                    if(v == 0){
                        c2 = 1/Math.sqrt(2);
                    }
                    else {
                        c2 = 1;
                    }
                    summary = 0.0;
                    for (int x=0;x<8;x++) {
                        for (int y=0;y<8;y++) {
                            summary = summary + input[x][y]*Math.cos(((2*x+1)*u*Math.PI/(16)))*Math.cos(((2*y+1)*v*Math.PI/(16)));
                        }
                    }
                    output[u][v] = Math.round(summary*((c1*c2)/4));
                }
                else {
                    output[u][v] = 0;
                }
            }
        }
        return output;
    }

    public static double[][] reverseZigZag(double[] input){

        boolean konec = false;
        int x=0;
        int y=0;
        int indeks = 0;
        int N = 7;
        double[][]f = new double[N+1][N+1];
        double[][]a = new double[N+1][N+1];
        int OVERFLOW = 2033;

        while(konec == false){
            f[y][x] = input[indeks];
            a[y][x] = OVERFLOW;
            if ((x>0) && (y<N) && (a[y+1][x-1]<OVERFLOW)) // lahko gre levo dol
            {
                x--;
                y++;
            }
            else if ((x<N) && (y>0) && (a[y-1][x+1]<OVERFLOW)) // lahko gre desno gor
            {
                x++;
                y--;
            }
            else if ((x>0) && (x<N)) // lahko gre desno in ni v 1. stolpcu
            {
                x++;
            }
            else if ((y>0) && (y<N)) // lahko gre dol in ni v 1. vrstici
            {
                y++;
            }
            else if (x<N) // lahko gre desno (in je v 1. stolpcu)
            {
                x++;
            }
            else{
                konec=true;
            }
            indeks++;
        }

        return f;
    }

    public static double[][] inverseDCT(double[][] input){
        double[][] blok = new double[8][8];
        double summary;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                summary = 0.0;
                for(int u=0; u<8; u++) {
                    for (int v = 0; v < 8; v++) {
                        double c1;
                        double c2;
                        if(u==0){
                            c1 = 1/Math.sqrt(2);
                        }
                        else {
                            c1 = 1;
                        }
                        if(v == 0){
                            c2 = 1/Math.sqrt(2);
                        }
                        else {
                            c2 = 1;
                        }
                        summary = summary + c1*c2*input[u][v]*Math.cos(((2 * i + 1) * u * Math.PI) / 16) * Math.cos(((2 * j + 1) * v * Math.PI) / 16);
                    }
                }
                blok[i][j] = Math.round(summary/4);
            }
        }
        return blok;
    }

    public static ArrayList<ArrayList<double[]>> DecodeDCT(byte[] fromFile){

        BitSet kodirano = new BitSet();

        // Returns a bitset containing the values in bytes.
        // The byte-ordering of bytes must be big-endian which means the most significant bit is in element 0.

        //BitSet bits = new BitSet();
        int ii=0;
        while(ii<fromFile.length*8) {
            if ((fromFile[fromFile.length-ii/8-1]&(1<<(ii%8))) > 0) {
                kodirano.set(ii);
            }
            ii++;
        }
        BitSet sortarray;
        String sortstring;
        ArrayList<double[]> output = new ArrayList<>();
        ArrayList<ArrayList<double[]>> retList = new ArrayList<>();
        double[] blok = new double[64];
        int stBlok = 0;

        for(int stej=0; stej<kodirano.size(); stej++) {
            boolean flag = kodirano.get(stej); //preveri ce je bit 1
            if(stBlok != 0){ //ni nov zacetek bloka
                if(flag){
                    stej += 1;
                    sortarray = kodirano.get(stej, stej+4); //dobi 4 bite iz pozicije
                    sortstring = "";
                    int i = 0;
                    while(i < 4){
                        int zast = (sortarray.get(i)) ? 1 : 0;
                        if(zast == 0){
                            sortstring = sortstring + "0";
                        }
                        else{
                            sortstring = sortstring + "1";
                        }
                        i++;
                    }
                    int stBitov = Integer.parseInt(sortstring, 2);
                    stej = stej + 4;
                    sortstring = "";
                    sortarray = kodirano.get(stej, stej+stBitov);
                    int j = 0;
                    while(j<stBitov){
                        int zast = (sortarray.get(j)) ? 1 : 0;
                        if(zast == 0){
                            sortstring += "0";
                        }
                        else{
                            sortstring += "1";
                        }
                        j++;
                    }
                    int minus = 1;
                    switch(sortstring.charAt(0)) {
                        case '1':
                            minus = 1;
                            break;
                        case '0':
                            minus = -1;
                    }
                    sortstring = sortstring.substring(1);
                    int plusMinus = 0;
                    plusMinus = Integer.parseInt(sortstring,2);
                    plusMinus *= minus;
                    blok[stBlok] = plusMinus;
                    stBlok += 1;
                    stej = stej + stBitov-1;
                    if(stBlok == 64){
                        stBlok = 0;
                        output.add(blok);
                        blok = new double[64];
                    }
                }
                else{
                    stej++;
                    BitSet zacasenArray = kodirano.get(stej, stej+6);
                    stej+=6;
                    String zacasenString = "";
                    int j = 0;
                    while(j<6){
                        int zast = (zacasenArray.get(j)) ? 1 : 0;
                        if(zast == 0){
                            zacasenString += "0";
                        }
                        else{
                            zacasenString += "1";
                        }
                        j++;
                    }
                    int stNicel = Integer.parseInt(zacasenString, 2);
                    int k = 0;
                    while(k<stNicel){
                        blok[stBlok] = 0;
                        stBlok++;
                        k++;
                    }
                    if(stBlok != 64){
                        zacasenArray = kodirano.get(stej, stej+4);
                        zacasenString = "";
                        int m = 0;
                        while(m<4){
                            int zast = (zacasenArray.get(m)) ? 1 : 0;
                            if(zast == 0){
                                zacasenString += "0";
                            }
                            else{
                                zacasenString += "1";
                            }
                            m++;
                        }
                        int stBitov = Integer.parseInt(zacasenString, 2);
                        stej += 4;
                        zacasenString = "";
                        zacasenArray = kodirano.get(stej, stej+stBitov);
                        int n = 0;
                        while(n<stBitov){
                            int zast = (zacasenArray.get(n)) ? 1 : 0;
                            if(zast == 0){
                                zacasenString += "0";
                            }
                            else{
                                zacasenString += "1";
                            }
                            n++;
                        }
                        if(zacasenString.isEmpty()){
                            break;
                        }
                        int minus = 1;
                        switch(zacasenString.charAt(0)) {
                            case '1':
                                minus = 1;
                                break;
                            case '0':
                                minus = -1;
                        }
                        zacasenString = zacasenString.substring(1);
                        int plusMinus = 0;
                        plusMinus = Integer.parseInt(zacasenString,2);
                        plusMinus *= minus;
                        blok[stBlok] = plusMinus;
                        stBlok += 1;
                        stej = stej + stBitov-1;
                        if(stBlok == 64){
                            stBlok = 0;
                            output.add(blok);
                            blok = new double[64];
                        }
                    }
                    else{
                        stBlok = 0;
                        output.add(blok);
                        blok = new double[64];
                        stej -= 1;
                    }
                }
            }
            else {
                BitSet DCkoeficient = kodirano.get(stej, stej + 11);
                String niz = "";
                int j=0;
                while(j<11){
                    if(!DCkoeficient.get(j)){
                        niz += "0";
                    }
                    else{
                        niz += "1";
                    }
                    j++;
                }
                blok[stBlok] = Integer.parseInt(niz, 2);
                stBlok += 1;
                stej = stej + 10;
            }
        }
        stBlok = output.size();
        retList.add(new ArrayList<>(output.subList(0,stBlok/3)));
        retList.add(new ArrayList<>(output.subList(stBlok/3,(stBlok/3)*2)));
        retList.add(new ArrayList<>(output.subList((stBlok/3)*2,stBlok)));
        System.out.println("Decode exit function.");
        return retList;
    }

    public static void EncodeDCT(ArrayList<double[]>output){
        BitSet kodirano = new BitSet(N);
        int st = 0;
        int i = 0;
        int j = 0;
        for(i = 0;i < output.size(); i++) {
            for(j = 0; j < output.get(i).length; j++) {
                double foo = output.get(i)[j];
                if(j % 64 == 0 || j == 0) {
                    String pretvorjeno = Integer.toString((int)foo,2);
                    while(pretvorjeno.length() < 11) {
                        pretvorjeno = "0" + pretvorjeno;
                    }
                    int x = 0;
                    while(x < pretvorjeno.length()) {
                        if(pretvorjeno.charAt(x) == '1') {
                            kodirano.set(st,Boolean.TRUE);
                            st++;
                        }
                        else {
                            kodirano.set(st,Boolean.FALSE);
                            st++;
                        }
                        x++;
                    }
                }
                else if(foo == 0) {
                    int stevec_nicel = 0;
                    int naslednji = 0;
                    int zastavica = 1;
                    int k = j;
                    while(k < j+64) {
                        if(output.get(i)[k] != 0) {
                            zastavica = 0;
                            break;
                        }
                        naslednji = (k + 1);
                        stevec_nicel = stevec_nicel + 1;
                        if(naslednji % 64 == 0) {
                            break;
                        }
                        k++;
                    }
                    kodirano.set(st, Boolean.FALSE);
                    st += 1;
                    String pretvorjeno = Integer.toString((int)stevec_nicel,2);
                    while(pretvorjeno.length() < 6){
                        pretvorjeno = "0" + pretvorjeno;
                    }

                    int x = 0;
                    while(x < pretvorjeno.length()) {
                        if(pretvorjeno.charAt(x) == '1') {
                            kodirano.set(st,Boolean.TRUE);
                            st++;
                        }
                        else {
                            kodirano.set(st,Boolean.FALSE);
                            st++;
                        }
                        x++;
                    }
                    j = j + stevec_nicel - 1;
                    if(zastavica == 1) {
                        continue;
                    }
                    else {
                        j++;
                        pretvorjeno = Integer.toString((int)output.get(i)[j],2);
                        switch(pretvorjeno.charAt(0)) {
                            case '-':
                                pretvorjeno = "0" + pretvorjeno.substring(1);
                                break;
                            default:
                                pretvorjeno = "1" + pretvorjeno;
                                break;
                        }
                        String stejBite = Integer.toString(pretvorjeno.length(),2);
                        while(stejBite.length()<4){
                            stejBite = "0" + stejBite;
                        }

                        int y = 0;
                        while(y < stejBite.length()) {
                            if(stejBite.charAt(y) == '1') {
                                kodirano.set(st,Boolean.TRUE);
                                st++;
                            }
                            else {
                                kodirano.set(st,Boolean.FALSE);
                                st++;
                            }
                            y++;
                        }
                        int z = 0;
                        while(z < pretvorjeno.length()) {
                            if(pretvorjeno.charAt(z) == '1') {
                                kodirano.set(st,Boolean.TRUE);
                                st++;
                            }
                            else {
                                kodirano.set(st,Boolean.FALSE);
                                st++;
                            }
                            z++;
                        }
                    }
                }
                else {
                    kodirano.set(st, Boolean.TRUE);
                    st += 1;
                    String pretvorjeno = Integer.toString((int)foo,2);
                    switch(pretvorjeno.charAt(0)) {
                        case '-':
                            pretvorjeno = "0" + pretvorjeno.substring(1);
                            break;
                        default:
                            pretvorjeno = "1" + pretvorjeno;
                            break;
                    }
                    String stejBite = Integer.toString((int)pretvorjeno.length(),2);
                    while(stejBite.length() < 4){
                        stejBite = "0" + stejBite;
                    }
                    int y = 0;
                    while(y < stejBite.length()) {
                        if(stejBite.charAt(y) == '1') {
                            kodirano.set(st,Boolean.TRUE);
                            st++;
                        }
                        else {
                            kodirano.set(st,Boolean.FALSE);
                            st++;
                        }
                        y++;
                    }
                    int z = 0;
                    while(z < pretvorjeno.length()) {
                        if(pretvorjeno.charAt(z) == '1') {
                            kodirano.set(st,Boolean.TRUE);
                            st++;
                        }
                        else {
                            kodirano.set(st,Boolean.FALSE);
                            st++;
                        }
                        z++;
                    }
                }
                //j++;
            }
            //i++;
        }

        // Returns a byte array of at least length 1.
        // The most significant bit in the result is guaranteed not to be a 1
        // (since BitSet does not support sign extension).
        // The byte-ordering of the result is big-endian which means the most significant bit is in element 0.
        // The bit at index 0 of the bit set is assumed to be the least significant bit.

        byte[] byteOutput = new byte[kodirano.length()/8+1];
        for (int ii=0; ii<kodirano.length(); ii++) {
            if (kodirano.get(ii)) {
                byteOutput[byteOutput.length-ii/8-1] |= 1<<(ii%8);
            }
        }
        try (FileOutputStream zapisi = new FileOutputStream("sdcard/narocila/narocilo.bin")) {
            zapisi.write(byteOutput);
            zapisi.close();
            double kompresija = (double)filesize/(double)st;
            //showMessageDialog(null,"Encode function exit.");
            //showMessageDialog(null,"CompressionRatio: " + kompresija);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

