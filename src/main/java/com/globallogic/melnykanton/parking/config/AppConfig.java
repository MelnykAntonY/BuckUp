package com.globallogic.melnykanton.parking.config;

import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.VehicleDAO;
import com.globallogic.melnykanton.parking.manager.impl.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.cloballogic.melnykanton.parking")
public class AppConfig {

    @Autowired
    private SpotsDAO spotsDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Bean
    public ServiceManager serviceManager() {
        return new ServiceManager(spotsDAO, vehicleDAO);
    }


}
