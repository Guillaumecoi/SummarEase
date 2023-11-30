package app.summarease.model.entities.document.dto;

import app.summarease.model.entities.document.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentToDocumentDtoConverter implements Converter<Document, DocumentDto> {

    @Override
    public DocumentDto convert(Document source) {
        return new DocumentDto(source.getId(),
                source.getTitle(),
                source.getAuthor(),
                source.getDescription(),
                source.getImageUrl(),
                source.getCreatedDate(),
                source.getModifiedDate(),
                source.getChapterIds()
                );
    }
}
