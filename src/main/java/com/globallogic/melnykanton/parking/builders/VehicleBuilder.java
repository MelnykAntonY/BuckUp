package com.globallogic.melnykanton.parking.builders;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import com.globallogic.melnykanton.parking.storage.DataStore;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * In this class we create set of Vehicle
 */
@Service
public class VehicleBuilder {

    //filling the set
    static {
        DataStore.vehicleStore.add(new Vehicle(1, "Ford", "Scorpion", 1267));
        DataStore.vehicleStore.add(new Vehicle(2, "BMW", "X5", 1657));
        DataStore.vehicleStore.add(new Vehicle(3, "Ford", "Sierra", 1090));
        DataStore.vehicleStore.add(new Vehicle(4, "VW", "Golf", 2807));
        DataStore.vehicleStore.add(new Vehicle(5, "KIA", "Serrato", 2170));
        DataStore.vehicleStore.add(new Vehicle(6, "Audi", "Q7 quattro", 3274));
        DataStore.vehicleStore.add(new Vehicle(7, "BMW", "428i", 4987));
        DataStore.vehicleStore.add(new Vehicle(8, "Hyundai ", "Genesis Luxury", 4300));
        DataStore.vehicleStore.add(new Vehicle(9, "Infiniti ", "Q70 5.6 Sport", 6745));
        DataStore.vehicleStore.add(new Vehicle(10, "Jaguar", "XFR 5.0L S/C 510 HP JT4 XFR", 6373));
        DataStore.vehicleStore.add(new Vehicle(11, "Lexus", "RC F 5.0 Luxury", 1111));
        DataStore.vehicleStore.add(new Vehicle(12, "Porsche", "Panamera ", 2222));
        DataStore.vehicleStore.add(new Vehicle(13, "Bentley", "GT V8", 5555));
    }

    /**
     * adding vehicle to MongoDB
     */
    public static void fillVehicleDB() {
        DBCollection spotTable = MongoConnect.connectVehicleToDb();
        for (Vehicle vehicle : DataStore.vehicleStore) {
            BasicDBObject tmpVehicle = new BasicDBObject();
            tmpVehicle.put("id", vehicle.getId());
            tmpVehicle.put("brand", vehicle.getBrand());
            tmpVehicle.put("series", vehicle.getSeries());
            tmpVehicle.put("number", vehicle.getNumber());
            spotTable.insert(tmpVehicle);
        }
    }
}
