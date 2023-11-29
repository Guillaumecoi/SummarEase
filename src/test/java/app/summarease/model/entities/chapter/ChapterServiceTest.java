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

    List<Chapter> chapters;

    Document parentDocument;
    Chapter chapter1;
    Chapter chapter2;

    @BeforeEach
    void setUp() {
        parentDocument = new Document();

        chapter1 = new Chapter();
        chapter1.setId(1);
        chapter1.setTitle("Chapter 1");
        chapter1.setDescription("Description 1");
        chapter1.setNumbered(true);
        chapter1.setImageUrl("https://picsum.photos/id/1/200/300");
        chapter1.setParentDocument(parentDocument);

        chapter2 = new Chapter();
        chapter2.setId(2);
        chapter2.setTitle("Chapter 2");
        chapter2.setDescription("Description 2");
        chapter2.setNumbered(false);
        chapter2.setImageUrl("https://picsum.photos/id/2/200/300");
        chapter2.setParentChapter(chapter1);

        chapters = new ArrayList<>();
        chapters.add(chapter1);
        chapters.add(chapter2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindById_Success() {
        // Arrange
        given(chapterRepository.findById(chapter1.getId())).willReturn(Optional.of(chapter1));  // chapter1 has parent document
        given(chapterRepository.findById(chapter2.getId())).willReturn(Optional.of(chapter2));  // chapter2 has parent chapter

        // Act
        Chapter returnedChapter1 = chapterService.findById(chapter1.getId());
        Chapter returnedChapter2 = chapterService.findById(chapter2.getId());

        // Assert
        assertNotNull(returnedChapter1, "Returned chapter1 should not be null");
        assertEquals(chapter1.getId(), returnedChapter1.getId(), "The id of chapter1 should match");
        assertEquals(chapter1.getTitle(), returnedChapter1.getTitle(), "The title of chapter1 should match");
        assertEquals(chapter1.getDescription(), returnedChapter1.getDescription(), "The description of chapter1 should match");
        assertEquals(chapter1.isNumbered(), returnedChapter1.isNumbered(), "The isNumbered flag of chapter1 should match");
        assertEquals(chapter1.getImageUrl(), returnedChapter1.getImageUrl(), "The imageUrl of chapter1 should match");
        assertEquals(chapter1.getParentDocument(), returnedChapter1.getParentDocument(), "The parent document of chapter1 should match");
        assertEquals(chapter1.getParentChapter(), returnedChapter1.getParentChapter(), "The parent chapter of chapter1 should match");
        verify(chapterRepository, times(1)).findById(chapter1.getId()); // Verifying that the repository's findById method was called exactly once with chapter1's ID

        assertNotNull(returnedChapter2, "Returned chapter2 should not be null");
        assertEquals(chapter2.getId(), returnedChapter2.getId(), "The id of chapter2 should match");
        assertEquals(chapter2.getTitle(), returnedChapter2.getTitle(), "The title of chapter2 should match");
        assertEquals(chapter2.getDescription(), returnedChapter2.getDescription(), "The description of chapter2 should match");
        assertEquals(chapter2.isNumbered(), returnedChapter2.isNumbered(), "The isNumbered flag of chapter2 should match");
        assertEquals(chapter2.getImageUrl(), returnedChapter2.getImageUrl(), "The imageUrl of chapter2 should match");
        assertEquals(chapter2.getParentDocument(), returnedChapter2.getParentDocument(), "The parent document of chapter2 should match");
        assertEquals(chapter2.getParentChapter(), returnedChapter2.getParentChapter(), "The parent chapter of chapter2 should match");
        verify(chapterRepository, times(1)).findById(chapter2.getId()); // Verifying that the repository's findById method was called exactly once with chapter2's ID
    }

    @Test
    void testFindById_Failure() {
        // Arrange
        Integer id = chapter1.getId();
        given(chapterRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> chapterService.findById(id));
        assertEquals("Could not find chapter with Id: " + id, exception.getMessage());
        verify(chapterRepository, times(1)).findById(id); // Verifying that the repository's findById method was called exactly once with chapter1's ID
    }
}