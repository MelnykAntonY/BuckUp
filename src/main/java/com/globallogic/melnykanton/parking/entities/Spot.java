package com.globallogic.melnykanton.parking.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "spots_table")
@Transactional
public class Spot {

    public Spot(Spot spot) {
        setId(spot.getId());
        setPlaceNumber(spot.getPlaceNumber());
        setVehicle_id(spot.getVehicle_id());
    }

    private int id;

    private int placeNumber;

    private Integer vehicle_id;

    public Spot() {
    }

    @Id
    @Column(name = "s_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "place_number")
    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    @Column(name = "vehicle_id")
    public Integer getVehicle_id() {
        return vehicle_id;
    }

    public Spot(int id, int placeNumber, Integer vehicle_id) {
        this.id = id;
        this.placeNumber = placeNumber;
        this.vehicle_id = vehicle_id;
    }

    public void setVehicle_id(Integer vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
