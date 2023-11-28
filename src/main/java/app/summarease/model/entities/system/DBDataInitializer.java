package app.summarease.model.entities.system;

import app.summarease.model.entities.document.Document;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDateTime;

public class DBDataInitializer implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

    }

    private void createDocuments() {
        Document document1 = new Document();
        document1.setId("1");
        document1.setTitle("Document 1");
        document1.setAuthor("Author 1");
        document1.setDescription("Description 1");
        document1.setImageUrl("https://picsum.photos/id/1/200/300");
        document1.setCreatedDate(LocalDateTime.of(2020, 1, 1,0,0)); // January 1, 2020 00:00:00
        document1.setModifiedDate(LocalDateTime.now().minusDays(1)); // Yesterday

        Document document2 = new Document();
        document2.setId("2");
        document2.setTitle("Document 2");
        document2.setAuthor("Author 2");
        document2.setDescription("Description 2");
        document2.setImageUrl("https://picsum.photos/id/2/200/300");
        document2.setCreatedDate(LocalDateTime.of(2020, 1, 2,0,0)); // January 2, 2020 00:00:00
        document2.setModifiedDate(LocalDateTime.now()); // Today

    }

}
