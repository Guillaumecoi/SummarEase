package app.summarease.model.entities.document;

import app.summarease.model.entities.document.dto.DocumentDto;
import app.summarease.model.entities.document.dto.DocumentDtoToDocumentConverter;
import app.summarease.model.entities.document.dto.DocumentToDocumentDtoConverter;
import app.summarease.model.entities.system.Result;
import app.summarease.model.entities.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    private final DocumentToDocumentDtoConverter documentToDocumentDtoConverter;
    private final DocumentDtoToDocumentConverter documentDtoToDocumentConverter;

    public DocumentController(DocumentService documentService, DocumentToDocumentDtoConverter documentToDocumentDtoConverter, DocumentDtoToDocumentConverter documentDtoToDocumentConverter) {
        this.documentService = documentService;
        this.documentToDocumentDtoConverter = documentToDocumentDtoConverter;
        this.documentDtoToDocumentConverter = documentDtoToDocumentConverter;
    }

    /**
     * Find a document by its id
     * @param documentId the id of the document
     * @return the document
     */
    @GetMapping("/{documentId}")
    public Result findById(@PathVariable Long documentId) {
        Document foundDocument = this.documentService.findById(documentId);
        DocumentDto foundDocumentDto = this.documentToDocumentDtoConverter.convert(foundDocument);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundDocumentDto);
    }


    /**
     * Find all documents
     * @return all documents
     */
    @GetMapping
    public Result findAll() {
        List<Document> foundDocuments = this.documentService.findAll();
        List<DocumentDto> foundDocumentDtos = new ArrayList<>();
        for (Document document : foundDocuments) {
            foundDocumentDtos.add(this.documentToDocumentDtoConverter.convert(document));
        }
        return new Result(true, StatusCode.SUCCESS, "Find All Success", foundDocumentDtos);
    }

    @PostMapping
    public Result addDocument(@Valid @RequestBody DocumentDto documentDto) {
        Document document = documentDtoToDocumentConverter.convert(documentDto);
        Document savedDocument = documentService.save(document);
        DocumentDto savedDocumentDto = documentToDocumentDtoConverter.convert(savedDocument);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedDocumentDto);
    }

    @PutMapping("/{documentId}")
    public Result updateDocument(@PathVariable Long documentId, @Valid @RequestBody DocumentDto documentDto) {
        Document document = documentDtoToDocumentConverter.convert(documentDto);
        Document updatedDocument = documentService.update(documentId, Objects.requireNonNull(document));
        DocumentDto updatedDocumentDto = documentToDocumentDtoConverter.convert(updatedDocument);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedDocumentDto);
    }

    @DeleteMapping("/{documentId}")
    public Result deleteDocument(@PathVariable Long documentId) {
        documentService.delete(documentId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success", null);
    }


}
