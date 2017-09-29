package mongo;

import com.globallogic.melnykanton.parking.builders.SpotsBuilder;
import com.globallogic.melnykanton.parking.builders.VehicleBuilder;
import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MongoConfig;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.mongodb.*;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.impl.mongodb.MongoSpotsDAOImpl;
import com.globallogic.melnykanton.parking.entities.Spot;
import org.junit.*;
import com.globallogic.melnykanton.parking.storage.MongoConnect;
import com.globallogic.melnykanton.parking.utils.CastHelper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("mongo")
@ContextConfiguration(classes = {AppConfig.class, MongoConfig.class})
public class MongoSpotDAOImplTest {

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
        new SpotsBuilder().fillSpotDB();
        new VehicleBuilder().fillVehicleDB();
    }

    @Test
    public void addSpotToDBTest() {
        DBCollection spotTable = MongoConnect.connectSpotToDb();

        Spot tmpSpot = new Spot(15, 33, null);
        spotsDAO.addSpot(tmpSpot);

        BasicDBObject search = new BasicDBObject();
        search.put("id", 15);

        DBCursor cursor = spotTable.find(search);

        Assert.assertTrue(CastHelper.transferToSpot(cursor.next()) instanceof Spot);

        Spot testSpot = spotsDAO.readSpot(15);

        Assert.assertTrue(testSpot.getId() == 15);
    }

    @Test
    public void readSpotFromDBTest() {

        Assert.assertTrue(spotsDAO.readSpot(5).getId() == 5);
    }

    @Test
    public void spotUpdateDBTest(){

        Spot testSpot = spotsDAO.readSpot(5);

        testSpot.setVehicle_id(13);

        spotsDAO.updateSpot(testSpot);

        Assert.assertTrue(spotsDAO.readSpot(5).getVehicle_id() == 13);
    }

    @Test
    public void deleteSpotFromDBTest() {

        spotsDAO.deleteSpot(2);


        Assert.assertTrue(spotsDAO.readSpot(2) == null);
    }

    @Test
    public void getSizeTest() {
        SpotsDAO spotsDAO = new MongoSpotsDAOImpl();

        Assert.assertTrue(Pattern.matches("^\\d*$", String.valueOf(spotsDAO.getSize())));
    }

    @Test
    public  void allFreeTest() {

        for (Object spot : spotsDAO.allFree()) {
            Assert.assertTrue(((Spot) spot).getVehicle_id() == null);
        }
    }
}
