package com.globallogic.melnykanton.parking.builders;

import com.globallogic.melnykanton.parking.utils.HibernateUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.storage.DataStore;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

/**
 * In this class we create set of Spot
 */
@Service
public class SpotsBuilder {

    //filling the set
    static {
        for (int i = 0; i < 15; i++) {
            DataStore.spotsStore.add(new Spot(i, i+1, null));
        }
    }

    /**
     * adding spots to MongoDB
     */
    public static void fillSpotDB() {
        DBCollection spotTable = MongoConnect.connectSpotToDb();
        for (Spot spot : DataStore.spotsStore) {
            BasicDBObject tmpSpot = new BasicDBObject();
            tmpSpot.put("id", spot.getId());
            tmpSpot.put("placeNumber", spot.getPlaceNumber());
            tmpSpot.put("vehicleID", spot.getVehicle_id());
            spotTable.insert(tmpSpot);
        }
    }

    public static void fillPostgresDB() {
        SessionFactory factory = HibernateUtils.getFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.createNativeQuery("create table if not exists spots_table " +
                "(s_id int not null primary key, place_number int, vehicle_id int)").executeUpdate();
        //tx.commit();
        //session.close();

        for (Spot spot : DataStore.spotsStore) {
            session.save(spot);
        }
        tx.commit();
        //session.close();
        //factory.close();
    }
}