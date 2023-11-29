package app.summarease.model.entities.document.dto;

import app.summarease.model.entities.document.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentDto(Integer id,
                          String title,
                          String author,
                          String description,
                          String imageUrl,
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
                          LocalDateTime createdDate,
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
                          LocalDateTime modifiedDate,
                          List<Integer> chapterIds) {
}
