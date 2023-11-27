package app.summarease.model.entities.document;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service @Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document findById(String documentId) {
        return this.documentRepository.findById(documentId).get();
    }
}
