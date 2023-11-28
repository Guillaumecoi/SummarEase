package app.summarease.model.entities.document;

import app.summarease.model.entities.system.Result;
import app.summarease.model.entities.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DocumentController {

    private final DocumentService documentService;

    /**
     * Constructor
     * @param documentService the document service
     */
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Find a document by its id
     * @param documentId the id of the document
     * @return the document
     */
    @GetMapping("/api/v1/documents/{documentId}")
    public Result findDocumentById(@PathVariable String documentId) {
        Document foundDocument = this.documentService.findById(documentId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundDocument);
    }
}
