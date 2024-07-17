package com.michaelyi;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class TestConfig {
    private static final int REDIS_PORT = 6379;
    private static final String MYSQL_IMAGE = "mysql:8.0.36";
    private static final String REDIS_IMAGE = "redis:6.2.14";
    private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private static final String SPRING_DATASOURCE_USERNAME
            = "spring.datasource.username";
    private static final String SPRING_DATASOURCE_PASSWORD =
            "spring.datasource.password";
    private static final String SPRING_DATA_REDIS_HOST
            = "spring.data.redis.host";
    private static final String SPRING_DATA_REDIS_PORT
            = "spring.data.redis.port";

    @Container
    private static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>(MYSQL_IMAGE);

    @Container
    private static final GenericContainer<?> REDIS  =
            new GenericContainer<>(REDIS_IMAGE).withExposedPorts(REDIS_PORT);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(SPRING_DATASOURCE_URL, MYSQL::getJdbcUrl);
        registry.add(SPRING_DATASOURCE_USERNAME, MYSQL::getUsername);
        registry.add(SPRING_DATASOURCE_PASSWORD, MYSQL::getPassword);
        registry.add(SPRING_DATA_REDIS_HOST, REDIS::getHost);
        registry.add(
                SPRING_DATA_REDIS_PORT,
                () -> String.valueOf(REDIS.getMappedPort(REDIS_PORT))
        );
    }
}
