import manager.MemServiceManagerTest;
import memory.MemSpotDAOImplTest;
import memory.MemVehicleDAOImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MemSpotDAOImplTest.class,
        MemVehicleDAOImplTest.class,
        MemServiceManagerTest.class
})
public class TestMemoryPrimer {
}
