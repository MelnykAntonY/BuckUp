package mongo;

import com.globallogic.melnykanton.parking.builders.SpotsBuilder;
import com.globallogic.melnykanton.parking.builders.VehicleBuilder;
import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MongoConfig;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import com.globallogic.melnykanton.parking.utils.CastHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mongo")
@ContextConfiguration(classes = {AppConfig.class, MongoConfig.class})
public class MongoVehicleDAOImplTest {

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private SpotsDAO spotsDAO;

    @Before
    public void createDB() {
        DBCollection spotTable = MongoConnect.connectSpotToDb();
        DBCollection vehicleTable = MongoConnect.connectVehicleToDb();
        spotTable.drop();
        vehicleTable.drop();
        new VehicleBuilder().fillVehicleDB();
        new SpotsBuilder().fillSpotDB();
    }

    @Test
    public void addVehicleToDB() {
        DBCollection vehicleTable = MongoConnect.connectVehicleToDb();

        Vehicle tmpVehicle = new Vehicle(15, "bmw", "x3", 1243);

        vehicleDAO.addVehicle(tmpVehicle);

        BasicDBObject search = new BasicDBObject();
        search.put("id", 15);

        DBCursor cursor = vehicleTable.find(search);

        Assert.assertTrue(CastHelper.transferToVehicle(cursor.next()) instanceof Vehicle);

        Vehicle testVehicle = vehicleDAO.readVehicle(15);

        Assert.assertTrue(testVehicle.getId() == 15);
    }

    @Test
    public void readVehicleFromDB() {

        Assert.assertTrue(vehicleDAO.readVehicle(43) == null);
        Assert.assertTrue(vehicleDAO.readVehicle(5).getId() == 5);
    }

    @Test
    public void updateVehicleInDB () {

        Vehicle tmpVehicle = vehicleDAO.readVehicle(5);

        Assert.assertFalse(tmpVehicle.getNumber() == 5555);

        tmpVehicle.setNumber(5555);

        vehicleDAO.updateVehicle(tmpVehicle);

        Assert.assertTrue(vehicleDAO.readVehicle(5).getNumber() == 5555);
    }

    @Test
    public void deleteVehicleFromDB () {

        vehicleDAO.deleteVehicle(3);

        Assert.assertTrue(vehicleDAO.readVehicle(3) == null);
    }
}
