package com.globallogic.melnykanton.parking.dao.impl.memory;

import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import com.globallogic.melnykanton.parking.storage.DataStore;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Profile("memory")
public class MemVehicleDAOImpl implements VehicleDAO {

    public MemVehicleDAOImpl() {
    }

    private static Logger log = Logger.getLogger(MemVehicleDAOImpl.class.getName());

    /**
     * Add vehicle to collection
     *
     * @param vehicle
     */
    @Override
    public void addVehicle(Vehicle vehicle) {

        DataStore.vehicleStore.add(vehicle);
        log.info("vehicle added");
    }

    /**
     * Returns vehicle by id
     *
     * @param id
     * @return
     */
    @Override
    public Vehicle readVehicle(int id) {
        return DataStore.vehicleStore.stream()
                .filter(vehicle -> vehicle.getId() == id)
                .map(vehicle -> new Vehicle(vehicle))
                .findAny()
                .orElse(null);
    }

    /**
     * Update vehicle
     *
     * @param vehicle
     */
    @Override
    public void updateVehicle(Vehicle vehicle) {
        deleteVehicle(vehicle.getId());
        addVehicle(vehicle);
        log.info("vehicle updated");
    }

    /**
     * Delete vehicle by id
     *
     * @param id
     */
    @Override
    public void deleteVehicle(int id) {
        DataStore.vehicleStore.stream()
                .filter(vehicle1 -> vehicle1.getId() == id)
                .findAny()
                .ifPresent(vehicle -> DataStore.vehicleStore.remove(vehicle));
        log.info("vehicle deleted");
    }

    @Override
    public int getSize() {
        return DataStore.vehicleStore.size();
    }
}
