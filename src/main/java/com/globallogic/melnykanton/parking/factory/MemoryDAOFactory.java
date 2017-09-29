package com.globallogic.melnykanton.parking.factory;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.dao.impl.memory.MemSpotDAOImpl;
import com.globallogic.melnykanton.parking.dao.impl.memory.MemVehicleDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class MemoryDAOFactory extends DAOFactory {

    private SpotsDAO spotsDAO;

    private VehicleDAO vehicleDAO;

    @Override
    public SpotsDAO getSpotsDAO() {
        return spotsDAO;
    }

    public MemoryDAOFactory() {
    }

    public void setSpotsDAO(SpotsDAO spotsDAO) {

        this.spotsDAO = spotsDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
}
