package app.summarease.model.entities.document;

import app.summarease.model.entities.Chapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity
public class Document implements Serializable {
    // Static Attributes
    @Getter
    private final static String BASE_URL = "/api/v1/documents/";
    @Getter
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy'T'HH:mm:ss";
    @Getter
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    // Attributes
    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    // Contains
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parentDocument")
    private List<Chapter> chapters;
    //private @NonNull List<Content> contents;
    // Metadata

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime modifiedDate;
}
