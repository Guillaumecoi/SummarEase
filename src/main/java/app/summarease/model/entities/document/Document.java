package app.summarease.model.entities.document;

import app.summarease.model.entities.chapter.Chapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity
public class Document implements Serializable {
    // Static Attributes
    @Getter
    private final static String BASE_URL = "/api/v1/documents";
    @Getter
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy'T'HH:mm:ss"; // Also change in DocumentDto
    @Getter
    private static final String DATE_FORMAT = "dd-MM-yyyy"; // Also change in DocumentDto
    @Getter
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    // Attributes
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 100)
    private String title;
    @Column(length = 100)
    private String author;
    @Column(length = 1000)
    private String description;
    @Column(length = 10000)
    private String Foreword;
    @Column(length = 10000)
    private String endNote;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDate date;

    // Metadata
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime modifiedDate;

    // Relations to other objects
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "parentDocument")
    private List<Chapter> chapters = new ArrayList<>();
    //Todo Content


    public void addChapter(Chapter chapter) {
        chapter.setParentDocument(this);
        this.chapters.add(chapter);
    }

    /**
     * Get a list of all chapter ids
     * @return a list of all chapter ids
     */
    public List<Long> getChapterIds() {
        List<Long> chapterIds = new ArrayList<>();
        for (Chapter chapter : this.chapters) {
            chapterIds.add(chapter.getId());
        }
        return chapterIds;
    }
}
