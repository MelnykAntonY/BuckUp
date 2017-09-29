package com.globallogic.melnykanton.parking.dao;

import com.globallogic.melnykanton.parking.entities.Spot;

import java.util.Collection;

public interface SpotsDAO {

    void addSpot(Spot spot);

    Spot readSpot(int id);

    void updateSpot(Spot spot);

    void deleteSpot(int id);

    int getSize();

    Collection allFree();
}
