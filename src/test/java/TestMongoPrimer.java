import manager.MongoServiceManagerTest;
import mongo.MongoSpotDAOImplTest;
import mongo.MongoVehicleDAOImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MongoSpotDAOImplTest.class,
        MongoVehicleDAOImplTest.class,
        MongoServiceManagerTest.class
})
public class TestMongoPrimer {
}
