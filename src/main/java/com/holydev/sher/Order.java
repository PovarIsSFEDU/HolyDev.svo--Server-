package com.holydev.sher;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    private int order_id;
    @Column(name = "lat")
    private double lat;
    @Column(name = "lng")
    private double lng;
    @Column(name = "order_type")
    private int order_type;
    @Column(name = "workers_id")
    private String workers_id;
    @Column(name = "date")
    private String date;
    @Column(name = "status")
    private int status;                     // TODO - 1) В обработке; 2) Выполняется; 3) В архиве
    @Column(name = "checker_id")
    private int checker_id;

    @Override
    public String toString() {
        return order_id + " " + lat + " " + lng;
    }

    public Order(int order_id, double lat, double lng, int order_type, String workers_id, String date, int status, int checker_id) {
        this.order_id = order_id;
        this.lat = lat;
        this.lng = lng;
        this.order_type = order_type;
        this.workers_id = workers_id;
        this.date = date;
        this.status = status;

        this.checker_id = checker_id;
    }

    public Order() {
    }

    public static Order generateOkOrder() {
        return new Order(-1, 0.0, 0.0, -1, "", "", -1, -1);
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getWorkers_id() {
        return workers_id;
    }

    public void setWorkers_id(String workers_id) {
        this.workers_id = workers_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChecker_id() {
        return checker_id;
    }

    public void setChecker_id(int checker_id) {
        this.checker_id = checker_id;
    }
}
