package com.example.springgateway;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Gateway.class, GatewayITConfiguration.class}, webEnvironment=RANDOM_PORT)
public class GatewayIT {

    @ClassRule
    public static final WireMockClassRule STUBBED_SERVICE = new WireMockClassRule(findAvailableTcpPort());
    
    @LocalServerPort
    private int port;
    
    private RestTemplate template = new RestTemplate();
    
    public static int getStubServicePort() {
        return STUBBED_SERVICE.port();
    }
    
    @Test
    public void emptyBody() {
        STUBBED_SERVICE.stubFor(get(anyUrl())
                .willReturn(ok()));
        
        ResponseEntity<Void> stubResponse = 
                template.getForEntity(format("http://localhost:%d/", getStubServicePort()), Void.class);
        
        assertThat(stubResponse.getStatusCode()).isEqualTo(OK);
        assertThat(stubResponse.getHeaders().getContentType()).isNull();
        assertThat(stubResponse.getBody()).isNull();
        
        ResponseEntity<Void> response
             = template.getForEntity(format("http://localhost:%d/", port), Void.class);
        
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
    
}
