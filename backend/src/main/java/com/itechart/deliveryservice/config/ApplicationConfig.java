package com.itechart.deliveryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Configuration
public class ApplicationConfig {

    @Bean
    public EntityManager entityManager() {
        return Persistence.createEntityManagerFactory("PersistenceUnit").createEntityManager();
    }

}