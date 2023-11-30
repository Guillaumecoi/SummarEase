package app.summarease.model.entities.document.dto;

import app.summarease.model.entities.document.Document;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentDtoToDocumentConverter implements Converter<DocumentDto, Document> {

    @Override
    public Document convert(@NotNull DocumentDto source) {
        Document document = new Document();
        document.setId(source.id());
        document.setTitle(source.title());
        document.setAuthor(source.author());
        document.setDescription(source.description());
        document.setImageUrl(source.imageUrl());
        document.setCreatedDate(source.createdDate());
        document.setModifiedDate(source.modifiedDate());

        return document;
    }
}
