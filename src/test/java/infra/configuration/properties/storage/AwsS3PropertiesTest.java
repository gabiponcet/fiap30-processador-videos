package infra.configuration.properties.storage;

import com.fiap.tech.infra.configuration.properties.storage.AwsS3Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AwsS3PropertiesTest {

    @Test
    void shouldSetAndGetBucketSuccessfully() {
        AwsS3Properties properties = new AwsS3Properties();
        properties.setBucket("test-bucket");

        assertEquals("test-bucket", properties.getBucket());
    }

    @Test
    void shouldSetAndGetRegionSuccessfully() {
        AwsS3Properties properties = new AwsS3Properties();
        properties.setRegion("us-east-1");

        assertEquals("us-east-1", properties.getRegion());
    }

    @Test
    void shouldSetAndGetEndpointSuccessfully() {
        AwsS3Properties properties = new AwsS3Properties();
        properties.setEndpoint("http://localhost:4566");

        assertEquals("http://localhost:4566", properties.getEndpoint());
    }

    @Test
    void shouldSetAndGetAccessKeySuccessfully() {
        AwsS3Properties properties = new AwsS3Properties();
        properties.setAccessKey("access-key");

        assertEquals("access-key", properties.getAccessKey());
    }

    @Test
    void shouldSetAndGetSecretKeySuccessfully() {
        AwsS3Properties properties = new AwsS3Properties();
        properties.setSecretKey("secret-key");

        assertEquals("secret-key", properties.getSecretKey());
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        AwsS3Properties properties = new AwsS3Properties()
                .setBucket("test-bucket")
                .setRegion("us-east-1")
                .setEndpoint("http://localhost:4566")
                .setAccessKey("access-key")
                .setSecretKey("secret-key");

        String expected = "AwsS3Properties{bucket='test-bucket', region='us-east-1', endpoint='http://localhost:4566', accessKey='access-key', secretKey='secret-key'}";
        assertEquals(expected, properties.toString());
    }

    @Test
    void shouldLogPropertiesAfterInitialization() {
        AwsS3Properties properties = new AwsS3Properties()
                .setBucket("test-bucket")
                .setRegion("us-east-1")
                .setEndpoint("http://localhost:4566")
                .setAccessKey("access-key")
                .setSecretKey("secret-key");

        properties.afterPropertiesSet();

        assertNotNull(properties.toString()); // Ensures no exception is thrown during logging
    }
}