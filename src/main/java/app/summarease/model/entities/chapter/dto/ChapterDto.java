package app.summarease.model.entities.chapter.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record ChapterDto(Long id,
                         @NotNull(message = "Title cannot be null")
                         @Length(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
                         String title,
                         @Length(max = 1000, message = "Description cannot be longer than 1000 characters")
                         String description,
                         @Length(max = 10000, message = "Foreword cannot be longer than 10000 characters")
                         String foreword,
                         @Length(max = 10000, message = "End note cannot be longer than 10000 characters")
                         String endNote,
                         boolean isNumbered,
                         @Length(max = 255, message = "Image URL cannot be longer than 255 characters")
                         String imageUrl,
                         Long parentDocumentId,
                         Long parentChapterId,
                         List<ChapterDto> subChapters
                         ) {
}
