package com.globallogic.melnykanton.parking.storage;

import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.entities.Vehicle;

import java.util.HashSet;
import java.util.Set;

/**
 * Contain two set, spot and vehicle, to store data
 *
 */
public class DataStore {

    public static Set<Vehicle> vehicleStore = new HashSet<>();

    public static Set<Spot> spotsStore = new HashSet<>();

}
