package app.summarease.model.entities.chapter.dto;

public record ChapterDto(Long id,
                         String title,
                         String description,
                         boolean isNumbered,
                         String imageUrl,
                         Long parentDocumentId,
                         Long parentChapterId
) {
}
