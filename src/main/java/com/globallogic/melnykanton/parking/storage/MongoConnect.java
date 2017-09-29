package com.globallogic.melnykanton.parking.storage;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * This class create connection to DB
 */
@Service
@Profile("mongo")
public class MongoConnect {

    private static MongoClient mongo = new MongoClient("localhost", 27017);

    /**
     * Create connection to DB
     * create DB
     * create table
     * @return collection of spots
     */
    public static DBCollection connectSpotToDb() {

        DB parkingDB = mongo.getDB("parking");

        DBCollection spotTable = parkingDB.getCollection("spots");

        return spotTable;
    }

    /**
     * Create connection to DB
     * create DB
     * create table
     * @return collection of vehicles
     */
    public static DBCollection connectVehicleToDb() {

        DB parkingDB = mongo.getDB("parking");

        DBCollection vehicleTable = parkingDB.getCollection("vehicles");

        return vehicleTable;
    }
}
