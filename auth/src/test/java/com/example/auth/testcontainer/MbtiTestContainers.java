package com.example.auth.testcontainer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@org.testcontainers.junit.jupiter.Testcontainers
@ContextConfiguration(initializers = {MbtiTestContainers.Initializer.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ActiveProfiles("test-container")
public @interface MbtiTestContainers {

    @Container
    MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("mbti")
            .withInitScript("data/mbti_schema.sql")
            .withCommand("mysqld --max_connections=200");

    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            mySQLContainer.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
