package app.summarease.model.entities.document.dto;


import app.summarease.model.entities.chapter.Chapter;
import app.summarease.model.entities.chapter.dto.ChapterDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentDto(Long id,
                          @NotNull(message = "Title cannot be null")
                          @Length(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
                          String title,
                          @Length(max = 100, message = "Author cannot be longer than 100 characters")
                          String author,
                          @Length(max = 1000, message = "Description cannot be longer than 1000 characters")
                          String description,
                          @Length(max = 255, message = "Image URL cannot be longer than 1000 characters")
                          String imageUrl,
                          @NotNull(message = "Created date cannot be empty")
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
                          LocalDateTime createdDate,
                          @NotNull(message = "Modified date cannot be empty")
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
                          LocalDateTime modifiedDate,
                          List<ChapterDto> chapters) {
}
