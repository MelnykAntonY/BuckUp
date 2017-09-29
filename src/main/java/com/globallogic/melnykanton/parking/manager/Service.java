package com.globallogic.melnykanton.parking.manager;

import com.globallogic.melnykanton.parking.entities.Spot;
import com.globallogic.melnykanton.parking.entities.Vehicle;

import java.util.Collection;

public interface Service {

    boolean isOccupy(Spot spot, Vehicle vehicle);

    void rid(int spot_id, int vehicle_id);

    Spot findFreeAny();

    Collection<Spot> findFree();
}
