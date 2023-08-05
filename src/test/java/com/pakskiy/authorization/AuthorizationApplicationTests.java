package com.pakskiy.authorization;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@RequiredArgsConstructor
class AuthorizationApplicationTests {
//    final AuthControllerTest authControllerTest;

    private static final DockerImageName dockerImageName = DockerImageName.parse("postgres:12.15");
    @Container
    private final static PostgreSQLContainer postgresqlContainer = (PostgreSQLContainer) new PostgreSQLContainer(dockerImageName)
            .withDatabaseName("stockAuth")
            .withUsername("postgres")
            .withPassword("123456").withReuse(true);

    @Test
    void checkContainerIsRunning() {
        assertTrue(postgresqlContainer.isRunning());
    }

//    @Test
//    void checkRegisterMethod(){
//        authControllerTest.register();
//    }

}
