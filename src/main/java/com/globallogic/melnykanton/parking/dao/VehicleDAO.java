package com.globallogic.melnykanton.parking.dao;

import com.globallogic.melnykanton.parking.entities.Vehicle;

public interface VehicleDAO {

    void addVehicle(Vehicle vehicle);

    Vehicle readVehicle(int id);

    void updateVehicle(Vehicle vehicle);

    void deleteVehicle(int id);

    int getSize();
}
