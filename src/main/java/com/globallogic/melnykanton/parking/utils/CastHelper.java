package com.globallogic.melnykanton.parking.utils;

import com.mongodb.DBObject;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.entities.Vehicle;

/**
 * Contain methods which help work with objects
 */
public class CastHelper {

    /**
     * Casts DBObject to Spot
     * @param object
     * @return spot
     */
    public static Spot transferToSpot(DBObject object) {

        int id = (int) object.get("id");
        int place = (int) object.get("placeNumber");
        Integer vehicleID = (Integer) object.get("vehicleID");

        return new Spot(id, place, vehicleID);
    }

    /**
     * Casts DBObject to Vehicle
     * @param object
     * @return vehicle
     */
    public static Vehicle transferToVehicle(DBObject object) {

        int id = (int) object.get("id");
        String brand = (String) object.get("brand");
        String series = (String) object.get("series");
        int number = (int) object.get("number");

        return new Vehicle(id, brand, series, number);
    }
}
