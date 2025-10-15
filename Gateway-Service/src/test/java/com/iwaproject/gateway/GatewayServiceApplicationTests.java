package com.iwaproject.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Gateway Service Application Tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class GatewayServiceApplicationTests {

    /**
     * Test that the application context loads.
     */
    @Test
    void contextLoads() {
    }

}
