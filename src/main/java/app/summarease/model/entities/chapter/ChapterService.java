package app.summarease.model.entities.chapter;

import app.summarease.model.entities.document.DocumentRepository;
import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Transactional
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final DocumentRepository documentRepository;

    public ChapterService(ChapterRepository chapterRepository, DocumentRepository documentRepository) {
        this.chapterRepository = chapterRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Find a chapter by its id
     *
     * @param chapterId the id of the chapter
     * @return the chapter
     * @throws ObjectNotFoundException if the chapter could not be found
     */
    @Observed(name = "chapter", contextualName = "findByIdService")
    public Chapter findById(Long chapterId) throws ObjectNotFoundException {
        return this.chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ObjectNotFoundException("chapter", chapterId));
    }

    /**
     * Find all chapters of a document
     *
     * @param documentId The ID of the document
     * @return all chapters of the document
     */
    public List<Chapter> findByDocumentId(Long documentId) {
        if(!this.documentRepository.existsById(documentId)) {
            throw new ObjectNotFoundException("document", documentId);
        }
        return chapterRepository.findByParentDocumentId(documentId);
    }


}
