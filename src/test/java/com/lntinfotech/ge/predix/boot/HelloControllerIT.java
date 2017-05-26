package com.lntinfotech.ge.predix.boot;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import java.net.URL;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spins up Spring Boot and accesses the URLs of the Rest apis
 * 
 * @author Ritesh Dave
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class, webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@Ignore
public class HelloControllerIT
{

    @Value("${local.server.port}")
    private int   localServerPort;

    private URL base;
    
    
    private TestRestTemplate template;

    /**
     * @throws Exception
     *             -
     */
    @Before
    public void setUp()
            throws Exception
    {
        this.template = new TestRestTemplate();
    }

    /**
     * 
     * @throws Exception
     *             -
     */
    @SuppressWarnings("nls")
    @Test
    public void getHello()
            throws Exception
    {
        this.base = new URL("http://localhost:" + this.localServerPort + "/echo");
        ResponseEntity<String> response = this.template.getForEntity(this.base.toString(), String.class);
        assertThat(response.getBody(), startsWith("Greetings from Predix"));

    }
}
