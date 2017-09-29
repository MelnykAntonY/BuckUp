package manager;

import com.globallogic.melnykanton.parking.builders.SpotsBuilder;
import com.globallogic.melnykanton.parking.builders.VehicleBuilder;
import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MemoryConfig;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.manager.impl.ServiceManager;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("memory")
@ContextConfiguration(classes = {AppConfig.class, MemoryConfig.class})
public class MemServiceManagerTest {

    @Autowired
    ServiceManager memServiceManager;

    @Autowired
    SpotsDAO spotsDAO;

    @Autowired
    VehicleDAO vehicleDAO;

    @BeforeClass
    public static void createCollection() {
        new SpotsBuilder();
        new VehicleBuilder();
    }

    @Test
    public void occupyTest() {

        Spot testSpot = spotsDAO.readSpot(7);
        if (testSpot == null){
            System.out.println("in occupyTest : no spot");
        }
        Vehicle testVehicle = vehicleDAO.readVehicle(5);
        if(testVehicle == null) {
            System.out.println("in occupyTest : no vehicle");
        }
        memServiceManager.isOccupy(testSpot, testVehicle);
        Assert.assertTrue(spotsDAO.readSpot(testSpot.getId()) != null);
        Assert.assertTrue(spotsDAO.readSpot(testSpot.getId()).getVehicle_id() == testVehicle.getId());
    }

    @Test
    public void ridTest() {
        memServiceManager.isOccupy(spotsDAO.readSpot(1), vehicleDAO.readVehicle(3));

        Assert.assertTrue(spotsDAO.readSpot(1).getVehicle_id() == 3);

        memServiceManager.rid(1,3);

        Assert.assertTrue(spotsDAO.readSpot(1).getVehicle_id() == null);
    }

    @Test
    public void findFreeAnyTest() {

        Spot testSpot = memServiceManager.findFreeAny();

        Assert.assertTrue(testSpot != null);
        Assert.assertTrue(testSpot.getVehicle_id() == null);
    }

    @Test
    public void findFreeTest() {

        for (Spot spot : memServiceManager.findFree()) {
            Assert.assertTrue(spot.getVehicle_id() == null);
        }
    }
}
