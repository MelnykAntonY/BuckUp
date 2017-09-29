package com.globallogic.melnykanton.parking.dao.impl.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import com.globallogic.melnykanton.parking.utils.CastHelper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class implements interface SpotDAO
 */
@Component
@Profile("mongo")
public class MongoSpotsDAOImpl implements SpotsDAO {


    public MongoSpotsDAOImpl() {
    }

    //init Logger
    private static Logger log = Logger.getLogger(MongoSpotsDAOImpl.class.getName());

    private static DBCollection spotTable = MongoConnect.connectSpotToDb();

    @Override
    public int getSize() {
        return (int) spotTable.getCount();
    }

    @Override
    public List<Spot> allFree() {
        List<Spot> freeSpotSet = new LinkedList<>();

        BasicDBObject query = new BasicDBObject();
        query.put("vehicleID", null);

        DBCursor cursor = spotTable.find(query);

        if (!cursor.hasNext()) {
            log.info("No empty spots");
            return null;
        }

        while (cursor.hasNext()) {
            freeSpotSet.add(CastHelper.transferToSpot(cursor.next()));
        }
        cursor.close();
        return freeSpotSet;
    }

    /**
     * Adding Spot to DB
     *
     * @param spot
     */
    @Override
    public void addSpot(Spot spot) {
        BasicDBObject tmpSpot = new BasicDBObject();
        tmpSpot.put("id", spot.getId());
        tmpSpot.put("placeNumber", spot.getPlaceNumber());
        tmpSpot.put("vehicleID", spot.getVehicle_id());

        spotTable.insert(tmpSpot);
        log.info("spot added to DB");
    }

    /**
     * Get the spot from DB by id
     *
     * @param id
     * @return spot or null
     */
    @Override
    public Spot readSpot(int id) {
        DBCollection spotTable = MongoConnect.connectSpotToDb();
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", id);

        DBCursor cursor = spotTable.find(searchQuery);

        if (!cursor.hasNext()) {
            log.info("no spot in DB");
            return null;
        }

        Spot tmpSpot = null;
        try {
            tmpSpot = CastHelper.transferToSpot(cursor.next());
        } catch (ClassCastException cse) {
            cse.printStackTrace();
        }finally {
            cursor.close();
        }
        return tmpSpot;
    }

    /**
     * Update values of spot
     * which one placed in parameters
     *
     * @param spot
     */
    @Override
    public void updateSpot(Spot spot) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", spot.getId());

        BasicDBObject tmpSpot = new BasicDBObject();
        tmpSpot.put("id", spot.getId());
        tmpSpot.put("placeNumber", spot.getPlaceNumber());
        tmpSpot.put("vehicleID", spot.getVehicle_id());

        BasicDBObject update = new BasicDBObject();
        update.put("$set", tmpSpot);

        spotTable.update(query, update);
        log.info("spot updated in DB");

    }

    /**
     * Delete spot from DB by id
     *
     * @param id
     */
    @Override
    public void deleteSpot(int id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", id);

        DBCursor cursor = spotTable.find(searchQuery);

        if (!cursor.hasNext()) {
            log.info("spot doesn't exist");
        }

        spotTable.remove(cursor.next());
        cursor.close();
        log.info("spot removed from DB");
    }
}
