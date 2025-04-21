package domain.resource;

import com.fiap.tech.domain.resource.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void shouldCreateResourceWithValidParameters() {
        byte[] content = "file content".getBytes();
        String contentType = "text/plain";
        String name = "file.txt";
        String folderPath = "/documents";
        String filePath = "/documents/file.txt";

        Resource resource = Resource.with(content, contentType, name, folderPath, filePath);

        assertArrayEquals(content, resource.content());
        assertEquals(contentType, resource.contentType());
        assertEquals(name, resource.name());
        assertEquals(folderPath, resource.folderPath());
        assertEquals(filePath, resource.filePath());
    }

    @Test
    void shouldThrowExceptionWhenContentIsNull() {
        String contentType = "text/plain";
        String name = "file.txt";
        String folderPath = "/documents";
        String filePath = "/documents/file.txt";

        assertThrows(NullPointerException.class, () ->
                Resource.with(null, contentType, name, folderPath, filePath)
        );
    }

    @Test
    void shouldThrowExceptionWhenContentTypeIsNull() {
        byte[] content = "file content".getBytes();
        String name = "file.txt";
        String folderPath = "/documents";
        String filePath = "/documents/file.txt";

        assertThrows(NullPointerException.class, () ->
                Resource.with(content, null, name, folderPath, filePath)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        byte[] content = "file content".getBytes();
        String contentType = "text/plain";
        String folderPath = "/documents";
        String filePath = "/documents/file.txt";

        assertThrows(NullPointerException.class, () ->
                Resource.with(content, contentType, null, folderPath, filePath)
        );
    }

    @Test
    void shouldThrowExceptionWhenFolderPathIsNull() {
        byte[] content = "file content".getBytes();
        String contentType = "text/plain";
        String name = "file.txt";
        String filePath = "/documents/file.txt";

        assertThrows(NullPointerException.class, () ->
                Resource.with(content, contentType, name, null, filePath)
        );
    }

    @Test
    void shouldThrowExceptionWhenFilePathIsNull() {
        byte[] content = "file content".getBytes();
        String contentType = "text/plain";
        String name = "file.txt";
        String folderPath = "/documents";

        assertThrows(NullPointerException.class, () ->
                Resource.with(content, contentType, name, folderPath, null)
        );
    }
}