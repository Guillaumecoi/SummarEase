package app.summarease.model.entities.document;

import app.summarease.model.entities.document.dto.DocumentDto;
import app.summarease.model.entities.system.StatusCode;
import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
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
import resources.DocumentObjects;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    DateTimeFormatter dateTimeFormatter = Document.getFORMATTER();
    DateTimeFormatter dateFormatter = Document.getDATE_FORMATTER();


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
                .andExpect(jsonPath("$.data.foreword").value(document1.getForeword()))
                .andExpect(jsonPath("$.data.endNote").value(document1.getEndNote()))
                .andExpect(jsonPath("$.data.imageUrl").value(document1.getImageUrl()))
                .andExpect(jsonPath("$.data.date").value(document1.getDate().format(dateFormatter)))
                .andExpect(jsonPath("$.data.createdDate").value(document1.getCreatedDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.data.modifiedDate").value(document1.getModifiedDate().format(dateTimeFormatter)));

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
                .andExpect(jsonPath("$.data[0].foreword").value(document1.getForeword()))
                .andExpect(jsonPath("$.data[0].endNote").value(document1.getEndNote()))
                .andExpect(jsonPath("$.data[0].imageUrl").value(document1.getImageUrl()))
                .andExpect(jsonPath("$.data[0].date").value(document1.getDate().format(dateFormatter)))
                .andExpect(jsonPath("$.data[0].createdDate").value(document1.getCreatedDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.data[0].modifiedDate").value(document1.getModifiedDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.data[1].id").value(document2.getId()))
                .andExpect(jsonPath("$.data[1].title").value(document2.getTitle()))
                .andExpect(jsonPath("$.data[1].author").value(document2.getAuthor()))
                .andExpect(jsonPath("$.data[1].description").value(document2.getDescription()))
                .andExpect(jsonPath("$.data[1].foreword").value(document2.getForeword()))
                .andExpect(jsonPath("$.data[1].endNote").value(document2.getEndNote()))
                .andExpect(jsonPath("$.data[1].imageUrl").value(document2.getImageUrl()))
                .andExpect(jsonPath("$.data[1].date").value(document2.getDate().format(dateFormatter)))
                .andExpect(jsonPath("$.data[1].createdDate").value(document2.getCreatedDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.data[1].modifiedDate").value(document2.getModifiedDate().format(dateTimeFormatter)
        ));
    }

    @Test
    void testAddDocument_Success() throws Exception {
        // Arrange
        DocumentDto documentDto = new DocumentDto(null, // id should be created here
                document1.getTitle(),
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                document1.getModifiedDate(), null);

        String json = objectMapper.writeValueAsString(documentDto);

        given(documentService.save(Mockito.any(Document.class))).willReturn(document1);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(document1.getId()))
                .andExpect(jsonPath("$.data.title").value(document1.getTitle()))
                .andExpect(jsonPath("$.data.author").value(document1.getAuthor()))
                .andExpect(jsonPath("$.data.description").value(document1.getDescription()))
                .andExpect(jsonPath("$.data.foreword").value(document1.getForeword()))
                .andExpect(jsonPath("$.data.endNote").value(document1.getEndNote()))
                .andExpect(jsonPath("$.data.imageUrl").value(document1.getImageUrl()))
                .andExpect(jsonPath("$.data.date").value(document1.getDate().format(dateFormatter)))
                .andExpect(jsonPath("$.data.createdDate").value(document1.getCreatedDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.data.modifiedDate").value(document1.getModifiedDate().format(dateTimeFormatter)));
    }

    @Test
    void testAddDocument_Failure_Title() throws Exception {
        // Title is null
        // Arrange
        DocumentDto nullTitle = new DocumentDto(null,
                null, // Title is null
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                document1.getModifiedDate(), null);

        String jsonNull = objectMapper.writeValueAsString(nullTitle);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonNull))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.title").value("Title cannot be null"));

        // Title is empty
        // Arrange
        DocumentDto emptyTitle = new DocumentDto(null,
                "", // empty string
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                document1.getModifiedDate(), null);

        String jsonEmpty = objectMapper.writeValueAsString(emptyTitle);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonEmpty))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.title").value("Title must be between 3 and 100 characters"));

        // Title is too short
        // Arrange
        DocumentDto shortTitle = new DocumentDto(null,
                "12", // Title is too short
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                document1.getModifiedDate(), null);

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
        DocumentDto longTitle = new DocumentDto(null,
                longString, // Title is too long"",
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                document1.getModifiedDate(), null);

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
        DocumentDto nullCreatedDate = new DocumentDto(null,
                document1.getTitle(),
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                null, // Created date is null
                document1.getModifiedDate(), null);

        String jsonCreate = objectMapper.writeValueAsString(nullCreatedDate);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonCreate))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.createdDate").value("Created date cannot be empty"));


        // Modified date is null
        // Arrange
        DocumentDto nullModifiedDate = new DocumentDto(null,
                document1.getTitle(),
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                null, // Modified date is null
                null);

        String jsonModified = objectMapper.writeValueAsString(nullModifiedDate);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(jsonModified))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.modifiedDate").value("Modified date cannot be empty"));

    }

    @Test
    void testUpdateDocument_Success() throws Exception {
        // Arrange
        DocumentDto documentDto = new DocumentDto(updatedDocument1.getId(),
                updatedDocument1.getTitle(),
                updatedDocument1.getAuthor(),
                updatedDocument1.getDescription(),
                updatedDocument1.getForeword(),
                updatedDocument1.getEndNote(),
                updatedDocument1.getImageUrl(),
                updatedDocument1.getDate(),
                document1.getCreatedDate(),
                updatedDocument1.getModifiedDate(), null);

        String json = objectMapper.writeValueAsString(documentDto);

        given(documentService.update(eq(1L), Mockito.any(Document.class))).willReturn(updatedDocument1);

        // Act & Assert

        this.mockMvc.perform(put(baseUrl + "/" + updatedDocument1.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(updatedDocument1.getId()))
                .andExpect(jsonPath("$.data.title").value(updatedDocument1.getTitle()))
                .andExpect(jsonPath("$.data.author").value(updatedDocument1.getAuthor()))
                .andExpect(jsonPath("$.data.description").value(updatedDocument1.getDescription()))
                .andExpect(jsonPath("$.data.foreword").value(updatedDocument1.getForeword()))
                .andExpect(jsonPath("$.data.endNote").value(updatedDocument1.getEndNote()))
                .andExpect(jsonPath("$.data.imageUrl").value(updatedDocument1.getImageUrl()))
                .andExpect(jsonPath("$.data.date").value(updatedDocument1.getDate().format(dateFormatter)))
                .andExpect(jsonPath("$.data.createdDate").value(updatedDocument1.getCreatedDate().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.data.modifiedDate").value(updatedDocument1.getModifiedDate().format(dateTimeFormatter)));
    }

    @Test
    void testUpdateDocument_Failure() throws Exception {
        // Arrange
        DocumentDto documentDto = new DocumentDto(null,
                document1.getTitle(),
                document1.getAuthor(),
                document1.getDescription(),
                document1.getForeword(),
                document1.getEndNote(),
                document1.getImageUrl(),
                document1.getDate(),
                document1.getCreatedDate(),
                document1.getModifiedDate(), null);

        String json = objectMapper.writeValueAsString(documentDto);
        given(documentService.update(eq(1L), Mockito.any(Document.class))).willThrow(new ObjectNotFoundException("document", 1L));

        // Act & Assert
        this.mockMvc.perform(put(baseUrl + "/" + 1L).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find document with Id: " + 1L))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteDocument_Success() throws Exception {
        // Arrange
        Long id = document1.getId();
        doNothing().when(documentService).delete(id);

        // Act & Assert
        this.mockMvc.perform(delete(baseUrl + "/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteDocument_Failure() throws Exception {
        // Arrange
        Long id = document1.getId();
        Mockito.doThrow(new ObjectNotFoundException("document", id)).when(documentService).delete(id);

        // Act & Assert
        this.mockMvc.perform(delete(baseUrl + "/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find document with Id: " + id))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}