package app.summarease.model.entities.chapter.dto;

public record ChapterDto(Integer id,

                         String title,
                         String description,
                         boolean isNumbered,
                         String imageUrl,
                         Integer parentDocumentId,
                         Integer parentChapterId
                         ) {
}
