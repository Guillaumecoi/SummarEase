package app.summarease.model.entities;

import app.summarease.model.entities.interfaces.DatabaseObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity
public class Chapter implements Serializable, DatabaseObject {
    // Attributes
    @Id
    private String id;
    private String title;
    private String description;
    private boolean isNumbered; // true (default) e.g. ch1 (true), introduction (false)
    // Todo private Image image;
    // Relations to other objects
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parent")
    private List<Chapter> subchapters;
    //private List<Content> contents;
    @ManyToOne
    private DatabaseObject parent;
}
