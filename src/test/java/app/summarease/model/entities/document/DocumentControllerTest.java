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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
    String baseUrl = "/api/v1/";

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
    void TestFindById_Succes() throws Exception {
        // Arrange
        given(documentService.findById(id1)).willReturn(doc1);

        // Act
        this.mockMvc.perform(get(this.baseUrl + "documents/" + id1).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(id1))
                .andExpect(jsonPath("$.data.title").value(title1));
    }

    @Test
    void TestFindById_Fail() throws Exception {
        // Arrange
        given(documentService.findById(id1)).willThrow(new ObjectNotFoundException("document", id1));

        // Act
        this.mockMvc.perform(get(this.baseUrl + "documents/" + id1).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find document with Id: " + id1))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

}