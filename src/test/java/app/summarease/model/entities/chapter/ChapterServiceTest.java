package app.summarease.model.entities.chapter;

import app.summarease.model.entities.document.Document;
import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import resources.ChapterObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChapterServiceTest {

    @Mock
    ChapterRepository chapterRepository;

    @InjectMocks
    ChapterService chapterService;

    ChapterObjects chapterObjects = new ChapterObjects();

    List<Chapter> chapters;

    Document parentDocument1;
    Document parentDocument2;
    Chapter chapter1;
    Chapter chapter2;
    Chapter chapter3;
    Chapter subChapter1;
    Chapter subChapter2;

    @BeforeEach
    void setUp() {
        parentDocument1 = new Document();
        parentDocument1.setId(1L);
        parentDocument2 = new Document();
        parentDocument2.setId(2L);

        chapter1 = chapterObjects.createChapter1();
        chapter1.setParentDocument(parentDocument1);
        chapter2 = chapterObjects.getChapter2();
        chapter2.setParentDocument(parentDocument1);
        chapter3 = chapterObjects.getChapter3();
        chapter3.setParentDocument(parentDocument2);

        subChapter1 = chapterObjects.getSubChapter1();
        chapter1.addSubChapter(subChapter1);
        subChapter2 = chapterObjects.getSubChapter2();
        chapter1.addSubChapter(subChapter2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindById_Success() {
        // Arrange
        given(chapterRepository.findById(chapter1.getId())).willReturn(Optional.of(chapter1));  // chapter1 has parent document
        given(chapterRepository.findById(subChapter1.getId())).willReturn(Optional.of(subChapter1));  // chapter2 has parent chapter

        // Act
        Chapter returnedChapter = chapterService.findById(chapter1.getId());
        Chapter returnedSubChapter = chapterService.findById(subChapter2.getId());

        // Assert
        assertNotNull(returnedChapter, "Returned chapter1 should not be null");
        assertEquals(chapter1.getId(), returnedChapter.getId(), "The id of chapter1 should match");
        assertEquals(chapter1.getTitle(), returnedChapter.getTitle(), "The title of chapter1 should match");
        assertEquals(chapter1.getDescription(), returnedChapter.getDescription(), "The description of chapter1 should match");
        assertEquals(chapter1.getForeword(), returnedChapter.getForeword(), "The foreword of chapter1 should match");
        assertEquals(chapter1.getEndNote(), returnedChapter.getEndNote(), "The endNote of chapter1 should match");
        assertEquals(chapter1.isNumbered(), returnedChapter.isNumbered(), "The isNumbered flag of chapter1 should match");
        assertEquals(chapter1.getImageUrl(), returnedChapter.getImageUrl(), "The imageUrl of chapter1 should match");
        assertEquals(chapter1.getParentDocument(), returnedChapter.getParentDocument(), "The parent document of chapter1 should match");
        assertEquals(chapter1.getParentChapter(), returnedChapter.getParentChapter(), "The parent chapter of chapter1 should match");
        verify(chapterRepository, times(1)).findById(chapter1.getId()); // Verifying that the repository's findById method was called exactly once with chapter1's ID

        assertNotNull(returnedSubChapter, "Returned chapter2 should not be null");
        assertEquals(subChapter2.getId(), returnedSubChapter.getId(), "The id of chapter2 should match");
        assertEquals(subChapter2.getTitle(), returnedSubChapter.getTitle(), "The title of chapter2 should match");
        assertEquals(subChapter2.getDescription(), returnedSubChapter.getDescription(), "The description of chapter2 should match");
        assertEquals(subChapter2.isNumbered(), returnedSubChapter.isNumbered(), "The isNumbered flag of chapter2 should match");
        assertEquals(subChapter2.getImageUrl(), returnedSubChapter.getImageUrl(), "The imageUrl of chapter2 should match");
        assertEquals(subChapter2.getParentDocument(), returnedSubChapter.getParentDocument(), "The parent document of chapter2 should match");
        assertEquals(subChapter2.getParentChapter(), returnedSubChapter.getParentChapter(), "The parent chapter of chapter2 should match");
        verify(chapterRepository, times(1)).findById(subChapter2.getId()); // Verifying that the repository's findById method was called exactly once with chapter2's ID
    }

    @Test
    void testFindById_Failure() {
        // Arrange
        Long id = chapter1.getId();
        given(chapterRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> chapterService.findById(id));
        assertEquals("Could not find chapter with Id: " + id, exception.getMessage());
        verify(chapterRepository, times(1)).findById(id); // Verifying that the repository's findById method was called exactly once with chapter1's ID
    }

    @Test
    void testFindByDocumentId_Success() {
        // Arrange
        Long documentId = parentDocument1.getId();
        given(chapterRepository.findByParentDocumentId(documentId)).willReturn(chapters);


    }
}