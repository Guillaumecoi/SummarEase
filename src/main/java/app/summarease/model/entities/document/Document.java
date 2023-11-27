package app.summarease.model.entities.document;

import app.summarease.model.entities.Chapter;
import app.summarease.model.entities.interfaces.DatabaseObject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity
public class Document implements Serializable, DatabaseObject {
    // Attributes
    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    // Todo image
    // Contains
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parent")
    private List<Chapter> chapters;
    //private @NonNull List<Content> contents;
    // Metadata
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
