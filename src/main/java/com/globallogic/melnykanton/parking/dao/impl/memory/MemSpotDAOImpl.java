package com.globallogic.melnykanton.parking.dao.impl.memory;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.storage.DataStore;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class with implementation interface SpotsDAO
 */
@Component
@Profile("memory")
public class MemSpotDAOImpl implements SpotsDAO {

    public MemSpotDAOImpl() {
    }

    private static Logger log = Logger.getLogger(MemSpotDAOImpl.class.getName());

    /**
     * Add spot to collection
     *
     * @param spot
     */
    @Override
    public void addSpot(Spot spot) {
        DataStore.spotsStore.add(spot);
        log.info("spot added");
    }

    /**
     * Returns spot by id
     *
     * @param id
     * @return spot or null
     */
    @Override
    public Spot readSpot(int id) {
        return DataStore.spotsStore.stream()
                .filter(spot -> spot.getId() == id)
                .map(spot -> new Spot(spot))
                .findAny()
                .orElse(null);
    }

    /**
     * Update spot
     *
     * @param spot
     */
    @Override
    public void updateSpot(Spot spot) {
        deleteSpot(spot.getId());
        addSpot(spot);
        log.info("spot updated");
    }

    /**
     * Delete spot by id
     *
     * @param id
     */
    @Override
    public void deleteSpot(int id) {
        DataStore.spotsStore.stream()
                .filter(spot -> spot.getId() == id)
                .findAny().ifPresent(spot -> DataStore.spotsStore.remove(spot));

        log.info("spot deleted");
    }

    @Override
    public int getSize() {
        return DataStore.spotsStore.size();
    }

    @Override
    public Collection<Spot> allFree() {
        List<Spot> freeSpotList = DataStore.spotsStore
                .stream()
                .filter(spot -> spot.getVehicle_id() == null)
                .collect(Collectors.toList());

        return freeSpotList;
    }
}
