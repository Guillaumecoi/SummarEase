package app.summarease.model.entities.document;

import app.summarease.model.entities.document.dto.DocumentDto;
import app.summarease.model.entities.document.dto.DocumentToDocumentDtoConverter;
import app.summarease.model.entities.system.StatusCode;
import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest @AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
class DocumentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DocumentService documentService;

    @Autowired
    ObjectMapper objectMapper;

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
    void testFindById_Success() throws Exception {
        // Arrange
        Long id = document1.getId();
        given(documentService.findById(id)).willReturn(document1);

        // Act & Assert
        this.mockMvc.perform(get(baseUrl + "/" + id).accept(MediaType.APPLICATION_JSON))
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
    void testFindById_Failure() throws Exception {
        // Arrange
        Long id = -1L;
        given(documentService.findById(id)).willThrow(new ObjectNotFoundException("document", id));

        // Act & Assert
        this.mockMvc.perform(get(baseUrl + "/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find document with Id: " + id))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAll_Success() throws Exception{
        // Arrange
        given(documentService.findAll()).willReturn(documents);

        // Act & Assert
        this.mockMvc.perform(get(baseUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data[0].id").value(document1.getId()))
                .andExpect(jsonPath("$.data[0].title").value(document1.getTitle()))
                .andExpect(jsonPath("$.data[0].author").value(document1.getAuthor()))
                .andExpect(jsonPath("$.data[0].description").value(document1.getDescription()))
                .andExpect(jsonPath("$.data[0].imageUrl").value(document1.getImageUrl()))
                .andExpect(jsonPath("$.data[0].createdDate").value(document1.getCreatedDate().format(formatter)))
                .andExpect(jsonPath("$.data[0].modifiedDate").value(document1.getModifiedDate().format(formatter)))
                .andExpect(jsonPath("$.data[1].id").value(document2.getId()))
                .andExpect(jsonPath("$.data[1].title").value(document2.getTitle()))
                .andExpect(jsonPath("$.data[1].author").value(document2.getAuthor()))
                .andExpect(jsonPath("$.data[1].description").value(document2.getDescription()))
                .andExpect(jsonPath("$.data[1].imageUrl").value(document2.getImageUrl()))
                .andExpect(jsonPath("$.data[1].createdDate").value(document2.getCreatedDate().format(formatter)))
                .andExpect(jsonPath("$.data[1].modifiedDate").value(document2.getModifiedDate().format(formatter)
        ));
    }

    @Test
    void testAddDocument_Success() throws Exception {
        // Arrange
        DocumentDto documentDto = new DocumentDto(null, "Title",
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                LocalDateTime.of(2023, 11, 1,0,0),
                LocalDateTime.now(), null);

        String json = objectMapper.writeValueAsString(documentDto);

        Document savedDocument = new Document();
        savedDocument.setId(1L);
        savedDocument.setTitle("Title");
        savedDocument.setAuthor("Author");
        savedDocument.setDescription("Description");
        savedDocument.setImageUrl("https://picsum.photos/id/1/200/300");
        savedDocument.setCreatedDate(LocalDateTime.of(2023, 11, 1,0,0)); // November 1, 2023 00:00:00
        savedDocument.setModifiedDate(LocalDateTime.now()); // Today

        given(documentService.save(Mockito.any(Document.class))).willReturn(savedDocument);

        // Act & Assert

        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(savedDocument.getId()))
                .andExpect(jsonPath("$.data.title").value(savedDocument.getTitle()))
                .andExpect(jsonPath("$.data.author").value(savedDocument.getAuthor()))
                .andExpect(jsonPath("$.data.description").value(savedDocument.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedDocument.getImageUrl()))
                .andExpect(jsonPath("$.data.createdDate").value(savedDocument.getCreatedDate().format(formatter)));
    }

    @Test
    void testAddDocument_Failure_Title() throws Exception {
        // Title is null
        // Arrange
        DocumentDto nullTitle = new DocumentDto(null, null,
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                LocalDateTime.of(2023, 11, 1,0,0),
                LocalDateTime.now(), null);

        String jsonNull = objectMapper.writeValueAsString(nullTitle);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonNull))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.title").value("Title cannot be null"));

        // Title is empty
        // Arrange
        DocumentDto emptyTitle = new DocumentDto(null, "",
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                LocalDateTime.of(2023, 11, 1,0,0),
                LocalDateTime.now(), null);

        String jsonEmpty = objectMapper.writeValueAsString(emptyTitle);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonEmpty))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.title").value("Title must be between 3 and 100 characters"));

        // Title is too short
        // Arrange
        DocumentDto shortTitle = new DocumentDto(null, "12",
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                LocalDateTime.of(2023, 11, 1,0,0),
                LocalDateTime.now(), null);

        String jsonShort = objectMapper.writeValueAsString(shortTitle);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonShort))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.title").value("Title must be between 3 and 100 characters"));

        // Title is too long
        // Arrange
        String longString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-abcdefghijklmnopqrstuvwxyzABCDEFGHIJKL";
        DocumentDto longTitle = new DocumentDto(null, "",
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                LocalDateTime.of(2023, 11, 1,0,0),
                LocalDateTime.now(), null);

        String jsonLong = objectMapper.writeValueAsString(longTitle);

        // Act & Assert
        assertEquals(101, longString.length(), "The length of the string should be 101");
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonLong))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.title").value("Title must be between 3 and 100 characters"));
    }

    @Test
    void testAddDocument_Failure_Date() throws Exception {
        // Created date is null
        // Arrange
        DocumentDto nullCreatedDate = new DocumentDto(null, "Title",
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                null,
                LocalDateTime.now(), null);

        String jsonCreate = objectMapper.writeValueAsString(nullCreatedDate);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonCreate))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.createdDate").value("Created date cannot be empty"));


        // Modified date is null
        // Arrange
        DocumentDto nullModifiedDate = new DocumentDto(null, "Title",
                "Author",
                "Description",
                "https://picsum.photos/id/1/200/300",
                LocalDateTime.of(2023, 11, 1,0,0),
                null, null);

        String jsonModified = objectMapper.writeValueAsString(nullModifiedDate);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonModified))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.modifiedDate").value("Modified date cannot be empty"));

    }

}