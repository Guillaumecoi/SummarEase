package app.summarease.model.entities.document;

import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;


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
    public Document findById(Long documentId) throws ObjectNotFoundException {
       return this.documentRepository.findById(documentId)
               .orElseThrow(() -> new ObjectNotFoundException("document", documentId));
    }

    /**
     * Find all documents
     * @return all documents
     */
    public List<Document> findAll() {
        return this.documentRepository.findAll();
    }

    public Document save(Document document) {
        return this.documentRepository.save(document);
    }

    public Document update(Long documentId, Document document) {
        Document oldDocument = this.findById(documentId); // throws ObjectNotFoundException if the document could not be found
        oldDocument.setTitle(document.getTitle());
        oldDocument.setAuthor(document.getAuthor());
        oldDocument.setDescription(document.getDescription());
        oldDocument.setForeword(document.getForeword());
        oldDocument.setEndNote(document.getEndNote());
        oldDocument.setImageUrl(document.getImageUrl());
        oldDocument.setDate(document.getDate());
        oldDocument.setModifiedDate(document.getModifiedDate());
        return this.documentRepository.save(oldDocument);
    }

    public void delete(Long documentId) {
        this.findById(documentId);  // throws ObjectNotFoundException if the document could not be found
        this.documentRepository.deleteById(documentId);
    }
}
