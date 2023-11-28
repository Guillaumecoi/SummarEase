package app.summarease.model.entities.chapter;

import app.summarease.model.entities.document.Document;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity
public class Chapter implements Serializable {
    // Static Attributes
    @Getter
    private final static String BASE_URL = "/api/v1/chapters/";

    // Attributes
    @Id
    private String id;
    private String title;
    private String description;
    private boolean isNumbered; // true (default) e.g. ch1 (true), introduction (false)
    private String imageUrl;
    // Relations to other objects
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parentChapter")
    private List<Chapter> subchapters;
    //private List<Content> contents;

    @ManyToOne
    @JoinColumn(name = "parent_document_id")
    private Document parentDocument; // Parent document

    @ManyToOne
    @JoinColumn(name = "parent_chapter_id")
    private Chapter parentChapter; // Parent chapter

    // Ensure that only one parent is set
    public void setParentDocument(Document document) {
        this.parentDocument = document;
        this.parentChapter = null;
    }

    public void setParentChapter(Chapter chapter) {
        this.parentChapter = chapter;
        this.parentDocument = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chapter )) return false;
        return id != null && id.equals(((Chapter) o).getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
