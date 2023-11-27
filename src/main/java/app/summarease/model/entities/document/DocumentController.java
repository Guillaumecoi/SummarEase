package app.summarease.model.entities.document;

import app.summarease.model.entities.system.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/api/v1/documents/{documentId}")
    public Result findDocumentById(@PathVariable String documentId) {
        return null;
    }
}
