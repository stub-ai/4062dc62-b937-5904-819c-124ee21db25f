import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@MicronautTest
public class FeatureMartControllerV2Test {

    @Inject
    @Client("/")
    HttpClient client;

    @Mock
    FeatureMartServiceImpl featureMartService;

    @InjectMocks
    FeatureMartControllerV2 featureMartController;

    @Test
    void testRetrieveFeaturesSuccess() {
        // Arrange
        String expectedResponse = "Success";
        when(featureMartService.getFeaturesV2()).thenReturn(expectedResponse);

        // Act
        HttpResponse<String> response = client.toBlocking().exchange(HttpRequest.GET("/retrieveFeatures"), String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(expectedResponse, response.body());
    }

    @Test
    void testRetrieveFeaturesException() {
        // Arrange
        when(featureMartService.getFeaturesV2()).thenThrow(new RuntimeException("Service exception"));

        // Act
        HttpResponse<String> response = client.toBlocking().exchange(HttpRequest.GET("/retrieveFeatures"), String.class);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    // Add more tests for different scenarios and edge cases
}