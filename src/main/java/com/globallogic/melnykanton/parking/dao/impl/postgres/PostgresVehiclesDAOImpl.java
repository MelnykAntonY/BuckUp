package com.globallogic.melnykanton.parking.dao.impl.postgres;

import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("postgres")
public class PostgresVehiclesDAOImpl implements VehicleDAO{
    @Override
    public void addVehicle(Vehicle vehicle) {

    }

    @Override
    public Vehicle readVehicle(int id) {
        return null;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {

    }

    @Override
    public void deleteVehicle(int id) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
