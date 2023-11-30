package app.summarease.model.entities.document.dto;

import app.summarease.model.entities.document.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentDto(Long id,
                          @NotNull(message = "Title cannot be null")
                          @Length(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
                          String title,
                          String author,
                          String description,
                          String imageUrl,
                          @NotNull(message = "Created date cannot be empty")
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
                          LocalDateTime createdDate,
                          @NotNull(message = "Modified date cannot be empty")
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
                          LocalDateTime modifiedDate,
                          List<Long> chapterIds) {
}
