package com.globallogic.melnykanton.parking;

import com.globallogic.melnykanton.parking.config.AppConfig;
import com.globallogic.melnykanton.parking.config.MemoryConfig;
import com.globallogic.melnykanton.parking.config.MongoConfig;
import com.globallogic.melnykanton.parking.config.PostgresqlConfig;
import com.globallogic.melnykanton.parking.dao.SpotsDAO;
import com.globallogic.melnykanton.parking.dao.impl.postgres.PostgresSpotsDAOImpl;
import com.globallogic.melnykanton.parking.manager.impl.ServiceManager;
import com.globallogic.melnykanton.parking.entities.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {

    @Autowired
    ServiceManager serviceManager;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        ctx.getEnvironment().setActiveProfiles("postgres");
        ctx.register(AppConfig.class, MongoConfig.class, MemoryConfig.class, PostgresqlConfig.class);
        ctx.refresh();

        ServiceManager serviceManager = ctx.getBean(ServiceManager.class);

        SpotsDAO dao = new PostgresSpotsDAOImpl();
        dao.addSpot(new Spot(3,10,null));
    }
}
