package app.summarease.model.entities.document;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest @AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
class DocumentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DocumentService documentService;

    //@Value("${api.endpoint.base-url}") // Spring will go to application-dev.yml to find the value and inject into this field.
    String baseUrl = Document.getBASE_URL();
    DateTimeFormatter formatter = Document.getFORMATTER();


    List<Document> documents;

    Document document1;

    Document document2;

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
        documents.add(document2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestFindById_Success() throws Exception {
        // Arrange
        String id = document1.getId();
        given(documentService.findById(id)).willReturn(document1);

        // Act & Assert
        this.mockMvc.perform(get(baseUrl + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.title").value(document1.getTitle()))
                .andExpect(jsonPath("$.data.author").value(document1.getAuthor()))
                .andExpect(jsonPath("$.data.description").value(document1.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(document1.getImageUrl()))
                .andExpect(jsonPath("$.data.createdDate").value(document1.getCreatedDate().format(formatter)))
                .andExpect(jsonPath("$.data.modifiedDate").value(document1.getModifiedDate().format(formatter)));

    }

    @Test
    void TestFindById_Fail() throws Exception {
        // Arrange
        String id = "invalidId";
        given(documentService.findById(id)).willThrow(new ObjectNotFoundException("document", id));

        // Act & Assert
        this.mockMvc.perform(get(baseUrl + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find document with Id: " + id))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}