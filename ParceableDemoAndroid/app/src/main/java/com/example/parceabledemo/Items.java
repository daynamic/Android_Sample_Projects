package com.example.parceabledemo;

import org.parceler.Parcel;

@Parcel
public class Items {
    public int id;
    public String name;
    public int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public Items(){

    }

    public Items(String name, int year){
        this.name=name;
        this.year=year;
    }

    @Override
    public String toString(){
        return "Item{" +
                "name='" + name + '\'' +
                ", year=" + year +
                '}';
    }

}
