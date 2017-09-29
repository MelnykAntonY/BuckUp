package com.globallogic.melnykanton.parking.dao.impl.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import com.globallogic.melnykanton.parking.utils.CastHelper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * This class implements interface VehicleDAO
 */
@Component
@Profile("mongo")
public class MongoVehicleDAOImpl implements VehicleDAO {

    public MongoVehicleDAOImpl() {
    }

    //init Logger
    private static Logger log = Logger.getLogger(MongoVehicleDAOImpl.class.getName());

    private static DBCollection vehicleTable = MongoConnect.connectVehicleToDb();

    /**
     * Adding vehicle to DB
     *
     * @param vehicle
     */
    @Override
    public void addVehicle(Vehicle vehicle) {
        BasicDBObject tmpVehicle = new BasicDBObject();
        tmpVehicle.put("id", vehicle.getId());
        tmpVehicle.put("brand", vehicle.getBrand());
        tmpVehicle.put("series", vehicle.getSeries());
        tmpVehicle.put("number", vehicle.getNumber());

        vehicleTable.insert(tmpVehicle);
        log.info("vehicle added to DB");
    }

    /**
     * Get the vehicle from DB by id
     *
     * @param id
     * @return vehicle or null
     */
    @Override
    public Vehicle readVehicle(int id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", id);

        DBCursor cursor = vehicleTable.find(searchQuery);

        if (!cursor.hasNext()) {
            log.info("Vehicle doesn't exist");
            return null;
        }

        Vehicle tmpVehicle = null;

        try {
            tmpVehicle = CastHelper.transferToVehicle(cursor.next());
        }catch (ClassCastException cse) {
            cse.printStackTrace();
        }finally {
            cursor.close();
        }
        return tmpVehicle;
    }

    /**
     * Update values of vehicle
     * which one placed in parameters
     *
     * @param vehicle
     */
    @Override
    public void updateVehicle(Vehicle vehicle) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", vehicle.getId());

        BasicDBObject tmpVehicle = new BasicDBObject();
        tmpVehicle.put("id", vehicle.getId());
        tmpVehicle.put("brand", vehicle.getBrand());
        tmpVehicle.put("series", vehicle.getSeries());
        tmpVehicle.put("number", vehicle.getNumber());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", tmpVehicle);

        vehicleTable.update(query, update);
        log.info("Vehicle updated in DB");
    }

    @Override
    public int getSize() {
        return (int)vehicleTable.getCount();
    }

    /**
     * Delete vehicle from DB by id
     *
     * @param id
     */
    @Override
    public void deleteVehicle(int id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", id);

        DBCursor cursor = vehicleTable.find(searchQuery);

        if (!cursor.hasNext()) {
            log.info("Vehicle doesn't exist in DB");
            return;
        }

        vehicleTable.remove(cursor.next());
        cursor.close();
        log.info("Vehicle removed");
    }
}
