package com.globallogic.melnykanton.parking.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("postgres")
public class HibernateUtils {

    private static SessionFactory factory;

    public static SessionFactory getFactory() {

        factory = buildFactory();

        return factory;
    }

    private static SessionFactory buildFactory() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        factory = null;

        try {
            factory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        }catch (Exception e) {
            System.err.println("ERROR: couldn't create session_factory");
            StandardServiceRegistryBuilder.destroy(registry);
            System.exit(1);
        }
        return factory;
    }
}
