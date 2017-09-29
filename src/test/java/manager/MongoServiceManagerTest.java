package manager;

import com.globallogic.melnykanton.parking.builders.SpotsBuilder;
import com.globallogic.melnykanton.parking.builders.VehicleBuilder;
import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MongoConfig;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.manager.impl.ServiceManager;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import com.mongodb.DBCollection;
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
public class MongoServiceManagerTest {


    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private SpotsDAO spotsDAO;

    @Autowired
    private ServiceManager serviceManager;

    @Before
    public void createCollections() {
        DBCollection tableSpot = MongoConnect.connectSpotToDb();
        DBCollection tableVehicle = MongoConnect.connectVehicleToDb();
        tableSpot.drop();
        tableVehicle.drop();
        SpotsBuilder.fillSpotDB();
        VehicleBuilder.fillVehicleDB();
    }

    @Test
    public void occupyDBTest() {

        Spot testSpot = spotsDAO.readSpot(5);
        if (testSpot == null) {
            System.out.println("occupyDBTest: no spot");
            return;
        }

        Vehicle testVehicle = vehicleDAO.readVehicle(8);
        if (testVehicle == null) {
            System.out.println("occupyDBTest: no vehicle");
            return;
        }

        serviceManager.isOccupy(testSpot, testVehicle);

        Assert.assertTrue(spotsDAO.readSpot(5).getVehicle_id() == testVehicle.getId());
    }

    @Test
    public void ridDBTest() {

        serviceManager.isOccupy(spotsDAO.readSpot(6), vehicleDAO.readVehicle(9));

        Assert.assertTrue(spotsDAO.readSpot(6).getVehicle_id() == 9);

        serviceManager.rid(6,9);

        Assert.assertTrue(spotsDAO.readSpot(6).getVehicle_id() == null);

    }

    @Test
    public void findFreeAnyDBTst() {

        Spot testSpot = serviceManager.findFreeAny();

        Assert.assertTrue(testSpot.getVehicle_id() == null);
    }

    @Test
    public void findFreeDBTest() {

        for (Spot spot : serviceManager.findFree()) {
            Assert.assertTrue(spot.getVehicle_id() == null);
        }
    }
}
