package infra.services.impl;

import com.fiap.tech.domain.resource.Resource;
import com.fiap.tech.infra.services.impl.S3StorageServiceImpl;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class S3StorageServiceImplTest {

    @Test
    void shouldRetrieveResourceSuccessfully() throws IOException {
        S3Client s3Client = mock(S3Client.class);
        String bucket = "test-bucket";
        String filePath = "folder/file.txt";

        HeadObjectResponse headObjectResponse = mock(HeadObjectResponse.class);
        when(headObjectResponse.contentType()).thenReturn("text/plain");
        when(s3Client.headObject(any(HeadObjectRequest.class))).thenReturn(headObjectResponse);

        S3StorageServiceImpl service = new S3StorageServiceImpl(bucket, s3Client);

        Optional<Resource> resource = service.get(filePath);

        assertTrue(resource.isPresent());
        assertEquals("file content", new String(resource.get().content()));
        assertEquals("text/plain", resource.get().contentType());
        assertEquals("file.txt", resource.get().name());
    }

    @Test
    void shouldReturnEmptyWhenFileDoesNotExist() {
        S3Client s3Client = mock(S3Client.class);
        String bucket = "test-bucket";
        String filePath = "folder/nonexistent.txt";

        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(NoSuchKeyException.class);

        S3StorageServiceImpl service = new S3StorageServiceImpl(bucket, s3Client);

        Optional<Resource> resource = service.get(filePath);

        assertTrue(resource.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenS3FailsToRetrieveResource() {
        S3Client s3Client = mock(S3Client.class);
        String bucket = "test-bucket";
        String filePath = "folder/file.txt";

        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(S3Exception.class);

        S3StorageServiceImpl service = new S3StorageServiceImpl(bucket, s3Client);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.get(filePath));

        assertTrue(exception.getMessage().contains("Falha ao recuperar recurso do S3"));
    }

    @Test
    void shouldStoreResourceSuccessfully() {
        S3Client s3Client = mock(S3Client.class);
        String bucket = "test-bucket";

        Resource resource = Resource.with("file content".getBytes(), "text/plain", "file.txt", "folder", "folder/file.txt");

        S3StorageServiceImpl service = new S3StorageServiceImpl(bucket, s3Client);

        service.store(resource);

        verify(s3Client, times(1)).putObject(
                any(PutObjectRequest.class),
                eq(RequestBody.fromBytes(resource.content()))
        );
    }

    @Test
    void shouldThrowExceptionWhenS3FailsToStoreResource() {
        S3Client s3Client = mock(S3Client.class);
        String bucket = "test-bucket";

        Resource resource = Resource.with("file content".getBytes(), "text/plain", "file.txt", "folder", "folder/file.txt");

        doThrow(S3Exception.class).when(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));

        S3StorageServiceImpl service = new S3StorageServiceImpl(bucket, s3Client);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.store(resource));

        assertTrue(exception.getMessage().contains("Falha ao armazenar recurso no S3"));
    }
}