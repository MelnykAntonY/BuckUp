package com.globallogic.melnykanton.parking.factory;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.utils.MemoryType;

public abstract class DAOFactory {

    private MemoryDAOFactory memoryDAOFactory;

    public DAOFactory(MemoryDAOFactory memoryDAOFactory, MongoDAOFactory mongoDAOFactory) {
        this.memoryDAOFactory = memoryDAOFactory;
        this.mongoDAOFactory = mongoDAOFactory;
    }

    private MongoDAOFactory mongoDAOFactory;

    public DAOFactory() {
    }

    public abstract SpotsDAO getSpotsDAO();

    public abstract VehicleDAO getVehicleDAO();

    public DAOFactory getDAOFactory(MemoryType type) {

        switch (type) {
            case COLLECTION:
                return memoryDAOFactory;
            case MONGO:
                return mongoDAOFactory;
            default:
                if(type == null){
                    throw new IllegalArgumentException("Wrong type, must be one of MemoryType enum");
                }
                return null;
        }
    }
}
