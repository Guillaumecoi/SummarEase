package app.summarease.model.entities.chapter.dto;

import java.util.List;

public record ChapterDto(Long id,
                         String title,
                         String description,
                         boolean isNumbered,
                         String imageUrl,
                         Long parentDocumentId,
                         Long parentChapterId,
                         List<ChapterDto> subChapters
                         ) {
}
