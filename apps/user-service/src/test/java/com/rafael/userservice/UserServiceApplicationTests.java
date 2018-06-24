package com.rafael.userservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelipost.userservice.UserServiceApplication;
import com.intelipost.userservice.dto.UserLoginForm;
import com.intelipost.userservice.model.User;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceApplicationTests {

    private String baseUrl;
    private CloseableHttpClient client;
    private String basicAuthorization;
    private ObjectMapper mapper;
    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.basic.auth.username}")
    private String basicUsername;
    @Value("${spring.basic.auth.password}")
    private String basicPassword;

    @Before
    public void init() {
        this.baseUrl = "http://localhost:" + serverPort;
        this.client = HttpClientBuilder.create().build();
        this.basicAuthorization = "Basic " + Base64.getEncoder().encodeToString((basicUsername + ":" + basicPassword).getBytes(StandardCharsets.UTF_8));
        this.mapper = new ObjectMapper();
    }

    @Test
    public void unauthorizedRequestTest() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(baseUrl + "/users/");

        // When
        HttpResponse httpResponse = client.execute(request);

        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void userNotFoundWithIdTest() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(baseUrl + "/users/" + UUID.randomUUID().toString());
        request.setHeader("Authorization", basicAuthorization);

        // When
        HttpResponse httpResponse = client.execute(request);

        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }
    
    @Test
    public void userNotFoundWithLoginDataTest() throws IOException {
        // Given
        HttpPost request = new HttpPost(baseUrl + "/users/login");
        request.setHeader("Authorization", basicAuthorization);
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(mapper.writeValueAsString(new UserLoginForm("admin@admin.com", "123456"))));

        // When
        HttpResponse httpResponse = client.execute(request);

        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }
    
    @Test
    public void mediaTypeTest() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(baseUrl + "/users/");
        request.setHeader("Authorization", basicAuthorization);

        // When
        HttpResponse httpResponse = client.execute(request);

        // Then
        assertThat(ContentType.APPLICATION_JSON.getMimeType(), equalTo(ContentType.getOrDefault(httpResponse.getEntity()).getMimeType()));
    }
    
    @Test
    public void userDataRetrievedTest() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(baseUrl + "/users/96c3d5a81c01472a8db5ff71c373ec97");
        request.setHeader("Authorization", basicAuthorization);

        // When
        HttpResponse httpResponse = client.execute(request);

        User user = mapper.readValue(httpResponse.getEntity().getContent(), User.class);
        
        // Then
        assertThat("Pedro Paulo", equalTo(user.getName()));
        assertThat("operator@system.com", equalTo(user.getUsername()));
        assertThat("OPERATOR", equalTo(user.getProfile().getName()));
    }
    
    @Test
    public void searchUserDataRetrievedTest() throws IOException {
        // Given
        HttpUriRequest request = new HttpGet(baseUrl + "/users/?name=Rocha");
        request.setHeader("Authorization", basicAuthorization);

        // When
        HttpResponse httpResponse = client.execute(request);

        List<User> users = mapper.readValue(httpResponse.getEntity().getContent(), new TypeReference<List<User>>() { });
        
        // Then
        users.forEach((u) -> {
            assertThat(u.getName(), containsString("Rocha"));
        });
    }

}
