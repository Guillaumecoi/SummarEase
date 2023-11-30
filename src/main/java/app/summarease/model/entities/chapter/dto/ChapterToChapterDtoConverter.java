package app.summarease.model.entities.chapter.dto;

import app.summarease.model.entities.chapter.Chapter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChapterToChapterDtoConverter implements Converter<Chapter, ChapterDto> {

    @Override
    public ChapterDto convert(Chapter source) {
        return new ChapterDto(source.getId(),
                source.getTitle(),
                source.getDescription(),
                source.isNumbered(),
                source.getImageUrl(),
                source.getParentDocument() != null ? source.getParentDocument().getId() : null,
                source.getParentChapter() != null ? source.getParentChapter().getId() : null
        );
    }
}
