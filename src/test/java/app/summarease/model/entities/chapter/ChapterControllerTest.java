package app.summarease.model.entities.chapter;

import app.summarease.model.entities.document.Document;
import app.summarease.model.entities.system.StatusCode;
import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest @AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
class ChapterControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ChapterService chapterService;

    String baseUrl = Chapter.getBASE_URL();

    List<Chapter> chapters;
    Document parentDocument;
    Chapter chapter1;
    Chapter chapter2;

    @BeforeEach
    void setUp() {
        parentDocument = new Document();
        parentDocument.setId("parentId");

        chapter1 = new Chapter();
        chapter1.setId("1");
        chapter1.setTitle("Chapter 1");
        chapter1.setDescription("Description 1");
        chapter1.setNumbered(true);
        chapter1.setImageUrl("https://picsum.photos/id/1/200/300");
        chapter1.setParentDocument(parentDocument);

        chapter2 = new Chapter();
        chapter2.setId("2");
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
    void testFindChapterById_Success() throws Exception {
        // Arrange
        String id1 = chapter1.getId();  // chapter1 has parent document
        given(chapterService.findById(id1)).willReturn(chapter1);

        String id2 = chapter2.getId();  // chapter2 has parent chapter
        given(chapterService.findById(id2)).willReturn(chapter2);

        // Act & Assert
        this.mockMvc.perform(get(baseUrl + id1).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(chapter1.getId()))
                .andExpect(jsonPath("$.data.title").value(chapter1.getTitle()))
                .andExpect(jsonPath("$.data.description").value(chapter1.getDescription()))
                .andExpect(jsonPath("$.data.numbered").value(chapter1.isNumbered()))
                .andExpect(jsonPath("$.data.imageUrl").value(chapter1.getImageUrl()))
                .andExpect(jsonPath("$.data.parentDocument").value(chapter1.getParentDocument()))
                .andExpect(jsonPath("$.data.parentChapter").value(chapter1.getParentChapter()));

        this.mockMvc.perform(get(baseUrl + id2).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(chapter2.getId()))
                .andExpect(jsonPath("$.data.title").value(chapter2.getTitle()))
                .andExpect(jsonPath("$.data.description").value(chapter2.getDescription()))
                .andExpect(jsonPath("$.data.numbered").value(chapter2.isNumbered()))
                .andExpect(jsonPath("$.data.imageUrl").value(chapter2.getImageUrl()))
                .andExpect(jsonPath("$.data.parentDocument").value(chapter2.getParentDocument()))
                .andExpect(jsonPath("$.data.parentChapter").value(chapter2.getParentChapter()));
    }

    @Test
    void testFindChapterById_Fail() throws Exception{
        // Arrange
        String id = "invalidId";
        given(chapterService.findById(id)).willThrow(new ObjectNotFoundException("chapter", id));

        // Act & Assert
        this.mockMvc.perform(get(baseUrl + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find chapter with Id: " + id))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}