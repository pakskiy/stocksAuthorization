package com.pakskiy.authorization;

import com.pakskiy.authorization.config.AppConfig;
import com.pakskiy.authorization.config.JwtService;
import com.pakskiy.authorization.config.SecurityConfig;
import com.pakskiy.authorization.dto.LoginRequestDto;
import com.pakskiy.authorization.dto.LoginResponseDto;
import com.pakskiy.authorization.dto.RegisterRequestDto;
import com.pakskiy.authorization.dto.RegisterResponseDto;
import com.pakskiy.authorization.repository.UserRepository;
import com.pakskiy.authorization.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@RequiredArgsConstructor
@Import({AppConfig.class, SecurityConfig.class, JwtService.class})
class AuthorizationApplicationTests {
//    final AuthControllerTest authControllerTest;

    @Autowired
    AuthServiceImpl authService;
    @Autowired
    UserRepository userRepository;

    static final String USERNAME = "testuser";
    static final String PASSWORD = "123456";
    static final String EMAIL = "testuser@gmail.com";


    private static final DockerImageName dockerImageName = DockerImageName.parse("postgres:12.15");
    @Container
    static PostgreSQLContainer<?> postgresqlContainer = (PostgreSQLContainer) new PostgreSQLContainer(dockerImageName)
            .withDatabaseName("stocksTestDb")
            .withUsername("postgres")
            .withPassword("123456").withReuse(true);
    @BeforeAll
    static void beforeAll(){
        postgresqlContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresqlContainer.stop();
    }

    @Test
    void checkContainerIsRunning() {
        assertTrue(postgresqlContainer.isRunning());
    }

    @Test
    void checkUserRegistration(){
        RegisterResponseDto registerResponseDto = authService.register(RegisterRequestDto.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build());

        assertEquals("testuser@gmail.com", registerResponseDto.getEmail());

//        LoginResponseDto loginResponseDto = authService.login(LoginRequestDto.builder()
//                .email(EMAIL)
//                .password(PASSWORD)
//                .build());
//
//        assertTrue(loginResponseDto.getToken().length()>0);
    }

    @Test
    void checkLoginUser(){

    }



//    @Test
//    void checkRegisterMethod(){
//        authControllerTest.register();
//    }

}
