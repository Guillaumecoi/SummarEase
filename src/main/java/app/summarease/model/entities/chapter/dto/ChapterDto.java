package app.summarease.model.entities.chapter.dto;

import app.summarease.model.entities.document.dto.DocumentDto;

public record ChapterDto(Integer id,

                         String title,
                         String description,
                         boolean isNumbered,
                         String imageUrl,
                         Integer parentDocumentId,
                         Integer parentChapterId
                         ) {
}
