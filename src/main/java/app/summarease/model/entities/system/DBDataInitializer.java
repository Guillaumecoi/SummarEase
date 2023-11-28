package app.summarease.model.entities.system;

import app.summarease.model.entities.chapter.Chapter;
import app.summarease.model.entities.document.Document;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDateTime;
import java.util.List;

public class DBDataInitializer implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        Document document1 = new Document();
        Chapter doc1Chapter1 = new Chapter();
        Chapter doc1Chapter2 = new Chapter();
        Chapter doc1Chapter3 = new Chapter();
        document1.setId("1");
        document1.setTitle("SummarEase User Manual");
        document1.setAuthor("Guillaume Coigniez");
        document1.setDescription("This is the user manual for SummarEase.");
        document1.setImageUrl("https://picsum.photos/id/1/200/300");
        document1.setCreatedDate(LocalDateTime.of(2020, 1, 1,0,0)); // January 1, 2020 00:00:00
        document1.setModifiedDate(LocalDateTime.now().minusDays(1)); // Yesterday
        document1.setChapters(List.of(doc1Chapter1, doc1Chapter2, doc1Chapter3));

        doc1Chapter1.setId("1");
        doc1Chapter1.setTitle("Introduction");
        doc1Chapter1.setDescription("This is the introduction chapter.");
        doc1Chapter1.setImageUrl("https://picsum.photos/id/4/200/300");
        doc1Chapter1.setNumbered(false);
        doc1Chapter1.setParentDocument(document1);

        doc1Chapter2.setId("2");
        doc1Chapter2.setTitle("Getting Started");
        doc1Chapter2.setDescription("This is the getting started chapter.");
        doc1Chapter2.setImageUrl("https://picsum.photos/id/5/200/300");
        doc1Chapter2.setNumbered(true);
        doc1Chapter2.setParentChapter(doc1Chapter1);

        doc1Chapter3.setId("3");
        doc1Chapter3.setTitle("Advanced Features");
        doc1Chapter3.setDescription("This is the advanced features chapter.");
        doc1Chapter3.setImageUrl("https://picsum.photos/id/6/200/300");
        doc1Chapter3.setNumbered(true);
        doc1Chapter3.setParentDocument(document1);


        Document document2 = new Document();
        document2.setId("2");
        document2.setTitle("Advancements in Artificial Intelligence: A Review");
        document2.setAuthor("Dr. Emily Johnson");
        document2.setDescription("An in-depth review of recent advancements in the field of artificial intelligence, focusing on neural networks and machine learning algorithms.");
        document2.setImageUrl("https://picsum.photos/id/2/200/300");
        document2.setCreatedDate(LocalDateTime.of(2021, 10, 22, 15, 45)); // October 22, 2021 15:45:00
        document2.setModifiedDate(LocalDateTime.now().minusWeeks(2)); // Two weeks ago

        Document document3 = new Document();
        document3.setId("3");
        document3.setTitle("Project Phoenix Final Report");
        document3.setAuthor("Alex Martinez");
        document3.setDescription("Comprehensive final report of Project Phoenix, detailing project outcomes, lessons learned, and recommendations for future initiatives.");
        document3.setImageUrl("https://picsum.photos/id/3/200/300");
        document3.setCreatedDate(LocalDateTime.of(2022, 5, 15, 9, 30)); // May 15, 2022 09:30:00
        document3.setModifiedDate(LocalDateTime.now().minusDays(3)); // Three days ago

    }

}
