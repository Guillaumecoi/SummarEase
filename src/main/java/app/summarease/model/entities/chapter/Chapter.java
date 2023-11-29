package app.summarease.model.entities.chapter;

import app.summarease.model.entities.document.Document;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity
public class Chapter implements Serializable {
    // Static Attributes
    @Getter
    private final static String BASE_URL = "/api/v1/chapters/";

    // Attributes
    @Id
    private Integer id;
    private String title;
    private String description;
    private boolean isNumbered; // true (default) e.g. ch1 (true), introduction (false)
    private String imageUrl;
    // Relations to other objects
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parentChapter")
    private List<Chapter> subchapters = new ArrayList<>();
    //private List<Content> contents;

    @ManyToOne
    @JoinColumn(name = "parent_document_id")
    private Document parentDocument; // Parent document

    @ManyToOne
    @JoinColumn(name = "parent_chapter_id")
    private Chapter parentChapter; // Parent chapter

    // Ensure that only one parent is set
    public void setParentDocument(Document document) {
        this.parentChapter = null;
        this.parentDocument = document;
    }

    public void setParentChapter(Chapter chapter) {
        this.parentDocument = null;
        this.parentChapter = chapter;
    }

    public void addSubchapter(Chapter chapter) {
        chapter.setParentChapter(this);
        this.subchapters.add(chapter);
    }

}
