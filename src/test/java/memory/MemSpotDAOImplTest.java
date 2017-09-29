package memory;

import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MemoryConfig;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.impl.memory.MemSpotDAOImpl;
import com.globallogic.melnykanton.parking.entities.Spot;
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
public class MemSpotDAOImplTest {

    @Autowired
    SpotsDAO spotsDAO;

    @Test
    public void addSpotTest() {
        Spot testSpot = new Spot(15, 43, null);
        spotsDAO.addSpot(testSpot);
        Assert.assertTrue(DataStore.spotsStore.contains(testSpot));
    }

    @Test
    public void readSpotTest() {
        SpotsDAO spotsDAO = new MemSpotDAOImpl();
        Assert.assertTrue(spotsDAO.readSpot(7) instanceof Spot || spotsDAO.readSpot(7) == null);
    }

    @Test
    public void updateSpotTest() {
        Spot testSpot = spotsDAO.readSpot(8);

        if (testSpot == null) {
            System.out.println("no spot");
            return;
        }

        testSpot.setVehicle_id(13);
        spotsDAO.updateSpot(testSpot);
        Assert.assertTrue(spotsDAO.readSpot(8).getVehicle_id() == 13);
    }

    @Test
    public void deleteSpotTest() {
        spotsDAO.deleteSpot(12);
        Assert.assertFalse(DataStore.spotsStore.contains(spotsDAO.readSpot(12)));
    }

    @Test
    public void getSizeTest() {
        Assert.assertTrue(DataStore.spotsStore.size() == spotsDAO.getSize());
    }

    @Test
    public void allFreeSpotsTest() {
        for (Object spot : spotsDAO.allFree()) {
            Assert.assertTrue(((Spot) spot).getVehicle_id() == null);
        }


    }
}
