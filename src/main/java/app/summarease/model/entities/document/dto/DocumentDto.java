package app.summarease.model.entities.document.dto;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentDto(Integer id,
                          String title,
                          String author,
                          String description,
                          String imageUrl,
                          LocalDateTime createdDate,
                          LocalDateTime modifiedDate,
                          List<Integer> chapterIds) {
}
