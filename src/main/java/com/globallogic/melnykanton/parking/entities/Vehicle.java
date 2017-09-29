package com.globallogic.melnykanton.parking.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_table")
public class Vehicle {

    private int id;

    private String brand;

    private String series;

    private int number;

    public Vehicle(int id, String brand, String series, int number) {
        this.id = id;
        this.brand = brand;
        this.series = series;
        this.number = number;
    }

    public Vehicle(Vehicle vehicle) {
        setId(vehicle.getId());
        setBrand(vehicle.getBrand());
        setSeries(vehicle.getSeries());
        setNumber(vehicle.getNumber());
    }

    public Vehicle() {
    }

    @Id
    @Column(name = "v_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
