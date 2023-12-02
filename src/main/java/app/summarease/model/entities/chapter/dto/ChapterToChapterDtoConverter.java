package app.summarease.model.entities.chapter.dto;

import app.summarease.model.entities.chapter.Chapter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
                source.getParentChapter() != null ? source.getParentChapter().getId() : null,
                this.convertChapters(source.getSubChapters())
        );
    }

    /**
     * Converts a list of chapters to a list of chapterDtos
     *
     * @param chapters list of chapters
     * @return list of chapterDtos
     */
    public List<ChapterDto> convertChapters(List<Chapter> chapters) {
        List<ChapterDto> chapterDtos = new ArrayList<>();
        for (Chapter chapter : chapters) {
            chapterDtos.add(this.convert(chapter));
        }
        return chapterDtos;
    }
}
