package com.globallogic.melnykanton.parking.dao.impl.postgres;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Profile("postgres")
public class PostgresSpotsDAOImpl implements SpotsDAO{

    private static SessionFactory factory;
    private static Session session;
    private static Transaction tx;

    private static Logger log = LoggerFactory.getLogger(PostgresSpotsDAOImpl.class);

    public PostgresSpotsDAOImpl() {
    }

    @Override
    public void addSpot(Spot spot) {
        init();

            session.save(spot);
            tx.commit();

            close();
    }

    @Override
    public Spot readSpot(int id) {
        init();
        Spot spot;

            spot = session.get(Spot.class, id);
            tx.commit();
            if (spot == null) {
                log.debug("Method 'readSpot': No spot");
                return null;
            }
            log.debug("ID: "+spot.getId()+"\nPlace:"+spot.getPlaceNumber()+"\nVehicle_ID: "+spot.getVehicle_id());

            close();
            return spot;
    }

    @Override
    public void updateSpot(Spot spot) {
        init();

            session.update(spot);
            tx.commit();

            close();
    }

    @Override
    public void deleteSpot(int id) {
        init();

        Spot spot = new Spot(id,0,0);

            session.delete(spot);
            tx.commit();

            close();
    }

    @Override
    public int getSize() {
        init();
        int count = (int)session.createQuery("select count(*) from spots_table").uniqueResult();
        close();
        return count;


    }

    @Override
    public Collection allFree() {
        init();

        List<Spot> allFree;
        Criteria criteria = session.createCriteria(Spot.class);

        criteria.add(Restrictions.eq("vehicle_id", null));

        allFree = (List<Spot>) criteria.list();

        close();
        return allFree;
    }

    private void init() {
        factory = HibernateUtils.getFactory();
        session = factory.openSession();
        tx = session.beginTransaction();
    }

    private void close() {
        session.close();
        factory.close();
    }
}
