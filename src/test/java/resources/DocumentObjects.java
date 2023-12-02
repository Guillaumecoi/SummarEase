package resources;

import app.summarease.model.entities.chapter.dto.ChapterToChapterDtoConverter;
import app.summarease.model.entities.document.Document;
import app.summarease.model.entities.document.dto.DocumentDto;
import app.summarease.model.entities.document.dto.DocumentToDocumentDtoConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DocumentObjects {
    private final DocumentToDocumentDtoConverter documentToDocumentDtoConverter;

    public DocumentObjects() {
        this.documentToDocumentDtoConverter = new DocumentToDocumentDtoConverter(new ChapterToChapterDtoConverter());
    }


    public Document getDocument1() {
        return createDoucment1();
    }

    public Document getUpdatedDocument1() {
        return createUpdatedDocument1();
    }

    public Document getDocument2() {
        return createDoucment2();
    }
    private Document createDoucment1() {
        Document document1 = new Document();
        document1.setId(1L);
        document1.setTitle("Document 1");
        document1.setAuthor("Author 1");
        document1.setDescription("Description 1");
        document1.setForeword("Foreword 1");
        document1.setEndNote("End note 1");
        document1.setImageUrl("https://picsum.photos/id/1/200/300");
        document1.setDate(LocalDate.of(2020, 1, 1));
        document1.setCreatedDate(LocalDateTime.of(2020, 1, 1,0,0)); // January 1, 2020 00:00:00
        document1.setModifiedDate(LocalDateTime.now().minusDays(1)); // Yesterday
        return document1;
    }

    private Document createUpdatedDocument1() {
        Document updatedDocument1 = createDoucment1();
        updatedDocument1.setTitle("Updated Document 1");
        updatedDocument1.setAuthor("Updated Author 1");
        updatedDocument1.setDescription("Updated Description 1");
        updatedDocument1.setForeword("Updated Foreword 1");
        updatedDocument1.setEndNote("Updated End note 1");
        updatedDocument1.setImageUrl("https://picsum.photos/id/1/200/300");
        updatedDocument1.setDate(LocalDate.of(2020, 1, 2));
        updatedDocument1.setModifiedDate(LocalDateTime.now()); // Now
        return updatedDocument1;
    }

    private Document createDoucment2() {
        Document document2 = new Document();
        document2.setId(2L);
        document2.setTitle("Document 2");
        document2.setAuthor("Author 2");
        document2.setDescription("Description 2");
        document2.setForeword("Foreword 2");
        document2.setEndNote("End note 2");
        document2.setImageUrl("https://picsum.photos/id/2/200/300");
        document2.setDate(LocalDate.of(2020, 1, 2));
        document2.setCreatedDate(LocalDateTime.of(2020, 1, 2,0,0)); // January 2, 2020 00:00:00
        document2.setModifiedDate(LocalDateTime.now().minusDays(1)); // Yesterday
        return document2;
    }
}
