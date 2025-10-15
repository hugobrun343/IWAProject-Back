package com.iwaproject.announcement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class AnnouncementServiceApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }

}
