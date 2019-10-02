package nl.rabobank;

import nl.rabobank.model.Person;
import nl.rabobank.repository.PersonRepository;
import nl.rabobank.service.PersonService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void smokeTest() throws Exception {


        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange(getBasePersonUrl(), HttpMethod.POST,
                new HttpEntity<>(new Person(), getAuthorizationHeader()), Object.class).getStatusCode());

        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange(getBasePersonUrl(), HttpMethod.GET,
                new HttpEntity<>(getAuthorizationHeader()), Object.class).getStatusCode());

        Assert.assertEquals(HttpStatus.OK, restTemplate.exchange(getBasePersonUrl(), HttpMethod.PUT,
                new HttpEntity<>(getAuthorizationHeader()), Object.class).getStatusCode());

        Assert.assertEquals(HttpStatus.NOT_FOUND, restTemplate.exchange(getBasePersonUrl()+"s", HttpMethod.GET,
                new HttpEntity<>(getAuthorizationHeader()), Object.class).getStatusCode());

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, restTemplate.getForEntity(getBasePersonUrl(),Object.class).getStatusCode());

    }


    private String getBasePersonUrl() {
        return "http://localhost:" + port + "/person";
    }

    private HttpHeaders getAuthorizationHeader() {
        return new HttpHeaders() {{
            String auth = "user" + ":" + "password";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

}