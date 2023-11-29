package app.summarease.model.entities.document;

import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service @Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    /**
     * Constructor
     * @param documentRepository the document repository
     */
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Find a document by its id
     * @param documentId the id of the document
     * @return the document
     * @throws ObjectNotFoundException if the document could not be found
     */
    @Observed(name = "document", contextualName = "findByIdService")
    public Document findById(Integer documentId) throws ObjectNotFoundException {
       return this.documentRepository.findById(documentId)
               .orElseThrow(() -> new ObjectNotFoundException("document", documentId));
    }
}
