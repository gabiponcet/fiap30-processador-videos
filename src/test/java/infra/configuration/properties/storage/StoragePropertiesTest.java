package infra.configuration.properties.storage;

import com.fiap.tech.infra.configuration.properties.storage.StorageProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoragePropertiesTest {

    @Test
    void shouldSetAndGetLocationPatternSuccessfully() {
        StorageProperties properties = new StorageProperties();
        properties.setLocationPattern("test-location");

        assertEquals("test-location", properties.getLocationPattern());
    }

    @Test
    void shouldSetAndGetFileNamePatternSuccessfully() {
        StorageProperties properties = new StorageProperties();
        properties.setFileNamePattern("test-file-name");

        assertEquals("test-file-name", properties.getFileNamePattern());
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        StorageProperties properties = new StorageProperties()
                .setLocationPattern("test-location")
                .setFileNamePattern("test-file-name");

        String expected = "StorageProperties{locationPattern='test-location', fileNamePattern='test-file-name'}";
        assertEquals(expected, properties.toString());
    }

    @Test
    void shouldLogPropertiesAfterInitialization() {
        StorageProperties properties = new StorageProperties()
                .setLocationPattern("test-location")
                .setFileNamePattern("test-file-name");

        properties.afterPropertiesSet();

        assertNotNull(properties.toString()); // Ensures no exception is thrown during logging
    }
}