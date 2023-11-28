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

    Document doc1;
    String id1 = "127638764829";
    String title1 = "Title1";
    String author1 = "Author1";
    String description1 = "This is the description of the document";

    Document doc2;
    String id2 = "227638764829";
    String title2 = "Title1";
    String author2 = "Author2";
    String description2 = "This is the description of the document2";

    //Todo createdDate and modifiedDate
    //Todo test for chapters and content


    @BeforeEach
    void setUp() {
        doc1 = new Document();
        doc1.setId(id1);
        doc1.setTitle(title1);
        doc1.setAuthor(author1);
        doc1.setDescription(description1);

        doc2 = new Document();
        doc2.setId(id2);
        doc2.setTitle(title2);
        doc2.setAuthor(author2);
        doc2.setDescription(description2);

        documents = new ArrayList<>();
        documents.add(doc1);
        documents.add(doc2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestFindById_Success() {
        // Arrange

        //Todo createdDate and modifiedDate
        //Todo test for chapters and content

        given(documentRepository.findById(id1)).willReturn(Optional.of(doc1)); // Mocks database

        // Act
        Document returnedDoc = documentService.findById(id1);

        // Assert
        assertEquals(returnedDoc.getId(), doc1.getId(), "The id should remain the same");
        assertEquals(returnedDoc.getTitle(), doc1.getTitle(), "The title remains the same");
        assertEquals(returnedDoc.getAuthor(), doc1.getAuthor(), "The author should remain the same");
        assertEquals(returnedDoc.getDescription(), doc1.getDescription(), "The description should remain the same");
        // Verifies that the method is only called once
        verify(documentRepository, times(1)).findById(id1);
    }

    @Test
    void TestFindById_Failure() {
        // Arrange
        String id = "123456789";

        given(documentRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty()); // Mocks database

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            documentService.findById(id);
        });
        assertEquals("Could not find document with Id :" + id, exception.getMessage());
        // Verifies that the method is only called once
        verify(documentRepository, times(1)).findById(id);
    }
}