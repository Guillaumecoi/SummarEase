package app.summarease.model.entities.document;

import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    DocumentRepository documentRepository;
    @InjectMocks
    DocumentService documentService;

    List<Document> documents;

    Document document1;

    Document document2;


    //Todo createdDate and modifiedDate
    //Todo test for chapters and content


    @BeforeEach
    void setUp() {
        document1 = new Document();
        document1.setId("1");
        document1.setTitle("Document 1");
        document1.setAuthor("Author 1");
        document1.setDescription("Description 1");
        document1.setImageUrl("https://picsum.photos/id/1/200/300");
        document1.setCreatedDate(LocalDateTime.of(2020, 1, 1,0,0)); // January 1, 2020 00:00:00
        document1.setModifiedDate(LocalDateTime.now().minusDays(1)); // Yesterday

        document2 = new Document();
        document2.setId("2");
        document2.setTitle("Document 2");
        document2.setAuthor("Author 2");
        document2.setDescription("Description 2");
        document2.setImageUrl("https://picsum.photos/id/2/200/300");
        document2.setCreatedDate(LocalDateTime.of(2020, 1, 2,0,0)); // January 2, 2020 00:00:00
        document2.setModifiedDate(LocalDateTime.now()); // Today


        documents = new ArrayList<>();
        documents.add(document1);
        documents.add(this.document2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestFindById_Success() {
        // Arrange
        String id = document1.getId();
        //Todo createdDate and modifiedDate
        //Todo test for chapters and content

        given(documentRepository.findById(id)).willReturn(Optional.of(document1)); // Mocks database

        // Act
        Document returnedDoc = documentService.findById(id);

        // Assert
        assertEquals(document1.getId(), returnedDoc.getId(), "The id should remain the same");
        assertEquals(document1.getTitle(), returnedDoc.getTitle(), "The title remains the same");
        assertEquals(document1.getAuthor(), returnedDoc.getAuthor(), "The author should remain the same");
        assertEquals(document1.getDescription(), returnedDoc.getDescription(), "The description should remain the same");
        assertEquals(document1.getImageUrl(), returnedDoc.getImageUrl(), "The imageUrl should remain the same");
        assertEquals(document1.getCreatedDate(), returnedDoc.getCreatedDate(), "The createdDate should remain the same");
        assertEquals(document1.getModifiedDate(), returnedDoc.getModifiedDate(), "The modifiedDate should remain the same");

        // Verifies that the method is only called once
        verify(documentRepository, times(1)).findById(id);
    }

    @Test
    void TestFindById_Failure() {
        // Arrange
        String id = document1.getId();

        given(documentRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty()); // Mocks database

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            documentService.findById(id);
        });
        assertEquals("Could not find document with Id: " + id, exception.getMessage());
        // Verifies that the method is only called once
        verify(documentRepository, times(1)).findById(id);
    }
}