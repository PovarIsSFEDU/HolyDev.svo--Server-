package com.holydev.sher;


import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;                                              //ID из базы данных
    @Column(name = "date")
    private String date;                                         //дата последнего обновления
    @Column(name = "type")
    private int type;                                            //тип техники
    @Column(name = "latitude")
    private double latitude;                                     //Latitude
    @Column(name = "longitude")
    private double longitude;                                    //Longitude
    @Column(name = "user_type")
    private int user_type;                                       //Ревизор или нет
    @Column(name = "status")
    private boolean status;                                      //статус - свободен или нет
    @Column(name = "username")
    private String username;                                     //username

    @Override
    public String toString() {
        return username + " " + date;
    }

    public Worker() {
    }

    public Worker(int id, String date, int type, double latitude, double longitude, int user_type, boolean status, String username) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_type = user_type;
        this.status = status;
        this.username = username;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
