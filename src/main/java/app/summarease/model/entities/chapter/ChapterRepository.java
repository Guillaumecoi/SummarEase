package app.summarease.model.entities.chapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByParentDocumentId(Long documentId);
}
