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
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    DocumentRepository documentRepository;
    @InjectMocks
    DocumentService documentService;

    List<Document> documents;

    Document document1;

    Document document2;

    //Todo test for chapters and content


    @BeforeEach
    void setUp() {
        document1 = new Document();
        document1.setId(1L);
        document1.setTitle("Document 1");
        document1.setAuthor("Author 1");
        document1.setDescription("Description 1");
        document1.setImageUrl("https://picsum.photos/id/1/200/300");
        document1.setCreatedDate(LocalDateTime.of(2020, 1, 1,0,0)); // January 1, 2020 00:00:00
        document1.setModifiedDate(LocalDateTime.now().minusDays(1)); // Yesterday

        document2 = new Document();
        document2.setId(2L);
        document2.setTitle("Document 2");
        document2.setAuthor("Author 2");
        document2.setDescription("Description 2");
        document2.setImageUrl("https://picsum.photos/id/2/200/300");
        document2.setCreatedDate(LocalDateTime.of(2020, 1, 2,0,0)); // January 2, 2020 00:00:00
        document2.setModifiedDate(LocalDateTime.now()); // Today


        documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindById_Success() {
        // Arrange
        Long id = document1.getId();
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
        verify(documentRepository, times(1)).findById(id); // Verifying that the repository's findById method was called exactly once with document1's ID
    }

    @Test
    void testFindById_Failure() {
        // Arrange
        Long id = document1.getId();
        given(documentRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty()); // Mocks database

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> documentService.findById(id));
        assertEquals("Could not find document with Id: " + id, exception.getMessage());
        verify(documentRepository, times(1)).findById(id); // Verifying that the repository's findById method was called exactly once with document1's ID
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        given(documentRepository.findAll()).willReturn(documents); // Mocks database

        // Act
        List<Document> returnedDocuments = documentService.findAll();

        // Assert
        assertEquals(documents.size(), returnedDocuments.size(), "The number of documents should be the same");
        verify(documentRepository, times(1)).findAll(); // Verifying that the repository's findAll method was called exactly once
    }

    @Test
    void testSave_Success() {
        // Arrange
        given(documentRepository.save(Mockito.any(Document.class))).willReturn(document1);

        // Act
        Document returnedDocument = documentService.save(document1);

        // Assert
        assertThat(returnedDocument.getId()).isEqualTo(document1.getId());
        assertThat(returnedDocument.getTitle()).isEqualTo(document1.getTitle());
        assertThat(returnedDocument.getAuthor()).isEqualTo(document1.getAuthor());
        assertThat(returnedDocument.getDescription()).isEqualTo(document1.getDescription());
        assertThat(returnedDocument.getImageUrl()).isEqualTo(document1.getImageUrl());
        assertThat(returnedDocument.getCreatedDate()).isEqualTo(document1.getCreatedDate());
        assertThat(returnedDocument.getModifiedDate()).isEqualTo(document1.getModifiedDate());
        verify(documentRepository, times(1)).save(Mockito.any(Document.class));
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        Document updatedDocument = new Document();
        updatedDocument.setId(document1.getId());
        updatedDocument.setTitle("Updated Document");
        updatedDocument.setAuthor("Updated Author");
        updatedDocument.setDescription("Updated Description");
        updatedDocument.setImageUrl("https://picsum.photos/id/4/200/300");
        updatedDocument.setModifiedDate(LocalDateTime.now().plusDays(2)); // Day after tomorrow

        given(documentRepository.findById(document1.getId())).willReturn(Optional.of(document1));
        given(documentRepository.save(document1)).willReturn(document1);

        // Act
        documentService.update(document1.getId(), updatedDocument);

        // Assert
        assertThat(document1.getId()).isEqualTo(updatedDocument.getId());
        assertThat(document1.getTitle()).isEqualTo(updatedDocument.getTitle());
        assertThat(document1.getAuthor()).isEqualTo(updatedDocument.getAuthor());
        assertThat(document1.getDescription()).isEqualTo(updatedDocument.getDescription());
        assertThat(document1.getImageUrl()).isEqualTo(updatedDocument.getImageUrl());
        assertThat(document1.getCreatedDate()).isEqualTo(document1.getCreatedDate());
        assertThat(document1.getModifiedDate()).isEqualTo(updatedDocument.getModifiedDate());

        verify(documentRepository, times(1)).findById(document1.getId());
        verify(documentRepository, times(1)).save(document1);

    }

    @Test
    void testUpdate_Failure() {
        // Arrange
        Document updatedDocument = new Document();
        updatedDocument.setId(document1.getId());
        updatedDocument.setTitle("Updated Document");
        updatedDocument.setAuthor("Updated Author");
        updatedDocument.setDescription("Updated Description");
        updatedDocument.setImageUrl("https://picsum.photos/id/4/200/300");
        updatedDocument.setModifiedDate(LocalDateTime.now().plusDays(2)); // Day after tomorrow

        given(documentRepository.findById(document1.getId())).willReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> documentService.update(document1.getId(), updatedDocument));
        assertEquals("Could not find document with Id: " + document1.getId(), exception.getMessage());

        verify(documentRepository, times(1)).findById(document1.getId());
        verify(documentRepository, times(0)).save(Mockito.any(Document.class));

    }

    @Test
    void testDelete_Success() {
        // Arrange
        Long id = document1.getId();
        given(documentRepository.findById(id)).willReturn(Optional.of(document1));
        doNothing().when(documentRepository).deleteById(id);

        // Act
        documentService.delete(id);

        // Assert
        verify(documentRepository, times(1)).deleteById(id);
    }

    @Test
    void testDelete_Failure() {
        // Arrange
        Long id = document1.getId();
        given(documentRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> documentService.delete(id));
        assertEquals("Could not find document with Id: " + id, exception.getMessage());

        verify(documentRepository, times(0)).deleteById(id);
    }

}