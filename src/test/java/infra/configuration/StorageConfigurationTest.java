package infra.configuration;

import com.fiap.tech.infra.configuration.StorageConfiguration;
import com.fiap.tech.infra.configuration.properties.storage.AwsS3Properties;
import com.fiap.tech.infra.configuration.properties.storage.StorageProperties;
import com.fiap.tech.infra.services.StorageService;
import com.fiap.tech.infra.services.impl.S3StorageServiceImpl;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageConfigurationTest {

    @Test
    void shouldCreateStoragePropertiesBeanSuccessfully() {
        StorageConfiguration configuration = new StorageConfiguration();

        StorageProperties properties = configuration.storageProperties();

        assertNotNull(properties);
    }

    @Test
    void shouldCreateAwsS3PropertiesBeanSuccessfully() {
        StorageConfiguration configuration = new StorageConfiguration();

        AwsS3Properties properties = configuration.awsS3Properties();

        assertNotNull(properties);
    }

    @Test
    void shouldCreateS3ClientSuccessfully() {
        AwsS3Properties props = mock(AwsS3Properties.class);
        when(props.getAccessKey()).thenReturn("accessKey");
        when(props.getSecretKey()).thenReturn("secretKey");
        when(props.getEndpoint()).thenReturn("http://localhost:4566");
        when(props.getRegion()).thenReturn("us-east-1");

        StorageConfiguration configuration = new StorageConfiguration();

        S3Client client = configuration.s3Client(props);

        assertNotNull(client);
    }

    @Test
    void shouldCreateS3StorageServiceBeanSuccessfully() {
        AwsS3Properties props = mock(AwsS3Properties.class);
        when(props.getBucket()).thenReturn("test-bucket");
        StorageConfiguration configuration = spy(new StorageConfiguration());
        S3Client s3Client = mock(S3Client.class);
        doReturn(s3Client).when(configuration).s3Client(props);

        StorageService service = configuration.s3Storage(props);

        assertNotNull(service);
        assertTrue(service instanceof S3StorageServiceImpl);
    }

    @Test
    void shouldThrowExceptionWhenS3ClientFailsToCreate() {
        AwsS3Properties props = mock(AwsS3Properties.class);
        when(props.getAccessKey()).thenReturn(null);
        when(props.getSecretKey()).thenReturn(null);

        StorageConfiguration configuration = new StorageConfiguration();

        assertThrows(NullPointerException.class, () -> configuration.s3Client(props));
    }
}