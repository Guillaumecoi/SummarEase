package app.summarease.model.entities.document;

import app.summarease.model.entities.document.dto.DocumentDto;
import app.summarease.model.entities.document.dto.DocumentToDocumentDtoConverter;
import app.summarease.model.entities.system.Result;
import app.summarease.model.entities.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class DocumentController {

    private final DocumentService documentService;

    private final DocumentToDocumentDtoConverter documentToDocumentDtoConverter;

    public DocumentController(DocumentService documentService, DocumentToDocumentDtoConverter documentToDocumentDtoConverter) {
        this.documentService = documentService;
        this.documentToDocumentDtoConverter = documentToDocumentDtoConverter;
    }

    /**
     * Find a document by its id
     * @param documentId the id of the document
     * @return the document
     */
    @GetMapping("/api/v1/documents/{documentId}")
    public Result findById(@PathVariable Integer documentId) {
        Document foundDocument = this.documentService.findById(documentId);
        DocumentDto foundDocumentDto = this.documentToDocumentDtoConverter.convert(foundDocument);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundDocumentDto);
    }


    /**
     * Find all documents
     * @return all documents
     */
    @GetMapping("/api/v1/documents")
    public Result findAll() {
        List<Document> foundDocuments = this.documentService.findAll();
        List<DocumentDto> foundDocumentDtos = new ArrayList<>();
        for (Document document : foundDocuments) {
            foundDocumentDtos.add(this.documentToDocumentDtoConverter.convert(document));
        }
        return new Result(true, StatusCode.SUCCESS, "Find All Success", foundDocumentDtos);
    }



}
