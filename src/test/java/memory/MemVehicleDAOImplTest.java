package memory;

import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MemoryConfig;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.entities.Vehicle;
import com.globallogic.melnykanton.parking.storage.DataStore;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("memory")
@ContextConfiguration(classes = {AppConfig.class, MemoryConfig.class})
public class MemVehicleDAOImplTest {

    @Autowired
    private VehicleDAO vehicleDAO;

    @Test
    public void addVehicleTest() {
        Vehicle testVehicle = new Vehicle(34, "Audi", "A8", 0110);
        vehicleDAO.addVehicle(testVehicle);
        Assert.assertTrue(DataStore.vehicleStore.contains(testVehicle));
    }

    @Test
    public void readVehicleTest() {
        Assert.assertTrue(vehicleDAO.readVehicle(4) instanceof Vehicle ||
                vehicleDAO.readVehicle(4) == null);
    }

    @Test
    public void updateVehicleTest() {
        Vehicle testVehicle = vehicleDAO.readVehicle(3);

        if (testVehicle == null) {
            System.out.println("no vehicle");
            return;
        }

        testVehicle.setSeries("changed");
        vehicleDAO.updateVehicle(testVehicle);
        Assert.assertTrue(vehicleDAO.readVehicle(3).getSeries().equals("changed"));
    }

    @Test
    public void deleteVehicleTest() {
        vehicleDAO.deleteVehicle(2);
        Assert.assertFalse(DataStore.vehicleStore.contains(vehicleDAO.readVehicle(2)));
    }

    @Test
    public void getSizeTest() {
        Assert.assertTrue(DataStore.vehicleStore.size() == vehicleDAO.getSize());
    }
}
