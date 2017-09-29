package com.globallogic.melnykanton.parking.factory;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;

public class MongoDAOFactory extends DAOFactory {

    private SpotsDAO spotsDAO;

    private VehicleDAO vehicleDAO;

    @Override
    public SpotsDAO getSpotsDAO() {
        return spotsDAO;
    }

    public void setSpotsDAO(SpotsDAO spotsDAO) {
        this.spotsDAO = spotsDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public MongoDAOFactory() {

    }

    @Override
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
}
