package postgres;

import com.globallogic.melnykanton.parking.builders.SpotsBuilder;
import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.PostgresqlConfig;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("postgres")
@ContextConfiguration(classes = {AppConfig.class, PostgresqlConfig.class})
public class PostgresSpotDAOImplTest {

    private static SessionFactory factory = HibernateUtils.getFactory();
    private static Session session = factory.openSession();
    private static Transaction tx = session.beginTransaction();

    @Autowired
    private SpotsDAO spotsDAO;

    @Before
    public void init() {
        session.createNativeQuery("drop table if exists spots_table").executeUpdate();
        tx.commit();
        new SpotsBuilder().fillPostgresDB();
    }

    @Test
    public void addSpotTest() {
        Spot spot = new Spot(23, 777, null);
        spotsDAO.addSpot(spot);
        Spot testSpot = spotsDAO.readSpot(23);
        Assert.assertTrue(testSpot.getId() == spot.getId());
        Assert.assertTrue(testSpot.getPlaceNumber() == spot.getPlaceNumber());
    }

    @Test
    public void readTest() {
        Spot spot = spotsDAO.readSpot(4);
        Assert.assertTrue( spot instanceof Spot);
        Assert.assertTrue(spot.getId() == 4);
    }

    @Test
    public void updateTest() {
        Spot testSpot = new Spot(13, 33, 17);
        Spot spotBeforUpdate = spotsDAO.readSpot(13);
        Assert.assertTrue(spotBeforUpdate.getId() == 13 && spotBeforUpdate.getVehicle_id() == null);
        spotsDAO.updateSpot(testSpot);
        Spot spotAfterUpdate = spotsDAO.readSpot(13);
        Assert.assertTrue(spotAfterUpdate.getPlaceNumber() == 33);
        Assert.assertTrue(spotAfterUpdate.getVehicle_id() == 17);
    }

    @Test
    public void deleteTest() {
        Spot testSpot = spotsDAO.readSpot(6);
        Assert.assertTrue(testSpot != null);

        spotsDAO.deleteSpot(testSpot.getId());
        Assert.assertNull(spotsDAO.readSpot(6));
    }

    @Test
    public void allFreeTest() {
        List<Spot> test;
        test = (List<Spot>) spotsDAO.allFree();
        for (Spot spot : test) {
            Assert.assertTrue(spot.getVehicle_id() == null);
        }
    }

    @AfterClass
    public static void tearDown() {
        if(session != null && session.isConnected()){
            session.close();
        }
        if(!factory.isClosed() && factory != null) {
            factory.close();
        }
    }
}
