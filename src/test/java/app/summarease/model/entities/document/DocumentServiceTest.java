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
import resources.DocumentObjects;

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

    DocumentObjects documentObjects = new DocumentObjects();

    List<Document> documents;
    Document document1;
    Document updatedDocument1;
    Document document2;

    //Todo test for chapters and content


    @BeforeEach
    void setUp() {
        document1 = documentObjects.getDocument1();
        document2 = documentObjects.getDocument2();
        updatedDocument1 = documentObjects.getUpdatedDocument1();

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
        assertEquals(document1.getForeword(), returnedDoc.getForeword(), "The foreword should remain the same");
        assertEquals(document1.getEndNote(), returnedDoc.getEndNote(), "The endNote should remain the same");
        assertEquals(document1.getImageUrl(), returnedDoc.getImageUrl(), "The imageUrl should remain the same");
        assertEquals(document1.getDate(), returnedDoc.getDate(), "The date should remain the same");
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
        assertThat(returnedDocument.getForeword()).isEqualTo(document1.getForeword());
        assertThat(returnedDocument.getEndNote()).isEqualTo(document1.getEndNote());
        assertThat(returnedDocument.getImageUrl()).isEqualTo(document1.getImageUrl());
        assertThat(returnedDocument.getDate()).isEqualTo(document1.getDate());
        assertThat(returnedDocument.getCreatedDate()).isEqualTo(document1.getCreatedDate());
        assertThat(returnedDocument.getModifiedDate()).isEqualTo(document1.getModifiedDate());
        verify(documentRepository, times(1)).save(Mockito.any(Document.class));
    }

    @Test
    void testUpdate_Success() {
        // Arrange

        given(documentRepository.findById(document1.getId())).willReturn(Optional.of(document1));
        given(documentRepository.save(document1)).willReturn(document1);

        // Act
        documentService.update(document1.getId(), updatedDocument1);

        // Assert
        assertThat(document1.getId()).isEqualTo(updatedDocument1.getId());
        assertThat(document1.getTitle()).isEqualTo(updatedDocument1.getTitle());
        assertThat(document1.getAuthor()).isEqualTo(updatedDocument1.getAuthor());
        assertThat(document1.getDescription()).isEqualTo(updatedDocument1.getDescription());
        assertThat(document1.getForeword()).isEqualTo(updatedDocument1.getForeword());
        assertThat(document1.getEndNote()).isEqualTo(updatedDocument1.getEndNote());
        assertThat(document1.getImageUrl()).isEqualTo(updatedDocument1.getImageUrl());
        assertThat(document1.getDate()).isEqualTo(updatedDocument1.getDate());
        assertThat(document1.getCreatedDate()).isEqualTo(document1.getCreatedDate());
        assertThat(document1.getModifiedDate()).isEqualTo(updatedDocument1.getModifiedDate());

        verify(documentRepository, times(1)).findById(document1.getId());
        verify(documentRepository, times(1)).save(document1);

    }

    @Test
    void testUpdate_Failure() {
        // Arrange
        Document updatedDocument = new Document();
        updatedDocument.setId(document1.getId());
        updatedDocument.setTitle("Updated Document");

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