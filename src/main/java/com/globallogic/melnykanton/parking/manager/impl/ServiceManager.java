package com.globallogic.melnykanton.parking.manager.impl;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.manager.Service;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class work with MongoDB
 */

public class ServiceManager implements Service{

    private SpotsDAO spotsDAO;

    @Autowired
    public ServiceManager(SpotsDAO spotsDAO, VehicleDAO vehicleDAO) {
        super();
        this.spotsDAO = spotsDAO;
        this.vehicleDAO = vehicleDAO;
    }

    public ServiceManager() {
    }

    private VehicleDAO vehicleDAO;

    //init logger
    private static Logger log = Logger.getLogger(ServiceManager.class.getName());

    /**
     * Sets vehicle on the spot
     *
     * @param spot
     * @param vehicle
     * @return true or false
     */
    @Override
    public boolean isOccupy(Spot spot, Vehicle vehicle) {

        Spot tmpSpot = spotsDAO.readSpot(spot.getId());

        if (tmpSpot == null) {
            log.info("No spots");
            return false;
        }
        tmpSpot.setVehicle_id(vehicle.getId());
        spotsDAO.updateSpot(tmpSpot);
        log.info("Vehicle with id: " + vehicle.getId() + " is parked" +
                " on spot with id: " + spot.getId());
        return true;
    }

    /**
     * Sets spot as empty
     *
     * @param vehicleId
     * @param spotId
     */
    @Override
    public void rid(int spotId, int vehicleId) {
       Spot tmpSpot = spotsDAO.readSpot(spotId);

        if (tmpSpot == null && tmpSpot.getVehicle_id() != vehicleId) {
            log.info("Vehicle is not on parking");
            return;
        }

        tmpSpot.setVehicle_id(null);
        spotsDAO.updateSpot(tmpSpot);
        log.info("Spot was rid of vehicle");
    }

    /**
     * Search and return empty place
     *
     * @return spot or null
     */
    @Override
    public Spot findFreeAny() {
      Spot tmpSpot = null;
      int i = 0;
      while (i <= spotsDAO.getSize()) {
          tmpSpot = spotsDAO.readSpot(i);
          if(tmpSpot.getVehicle_id() == null) {
              return tmpSpot;
          }
          i++;
      }
      return null;
    }

    /**
     * Merge all free spots into list
     *
     * @return list of free spots
     */
    @Override
    public List<Spot> findFree() {
        List<Spot> freeSpotList = new LinkedList<>();

        for (Object spot : spotsDAO.allFree()) {
            freeSpotList.add((Spot) spot);
        }

        return freeSpotList;
    }
}
