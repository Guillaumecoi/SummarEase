package resources;

import app.summarease.model.entities.chapter.Chapter;

public class ChapterObjects {


    public Chapter createChapter1() {
        Chapter chapter1 = new Chapter();
        chapter1.setId(1L);
        chapter1.setTitle("Chapter 1");
        chapter1.setDescription("Description 1");
        chapter1.setForeword("Foreword 1");
        chapter1.setEndNote("End note 1");
        chapter1.setImageUrl("https://picsum.photos/id/1/200/300");
        chapter1.setNumbered(true);

        return chapter1;
    }

    public Chapter createUpdatedChapter1() {
        Chapter updatedChapter1 = createChapter1();
        updatedChapter1.setTitle("Updated Chapter 1");
        updatedChapter1.setDescription("Updated Description 1");
        updatedChapter1.setForeword("Updated Foreword 1");
        updatedChapter1.setEndNote("Updated End note 1");
        updatedChapter1.setImageUrl("https://picsum.photos/id/1/200/300");
        updatedChapter1.setNumbered(false);

        return updatedChapter1;
    }

    public Chapter getChapter2() {
        Chapter chapter2 = new Chapter();
        chapter2.setId(2L);
        chapter2.setTitle("Chapter 2");
        chapter2.setDescription("Description 2");
        chapter2.setForeword("Foreword 2");
        chapter2.setEndNote("End note 2");
        chapter2.setImageUrl("https://picsum.photos/id/2/200/300");
        chapter2.setNumbered(true);
        return chapter2;
    }

    public Chapter getChapter3() {
        Chapter chapter3 = new Chapter();
        chapter3.setId(3L);
        chapter3.setTitle("Chapter 3");
        chapter3.setDescription("Description 3");
        chapter3.setForeword("Foreword 3");
        chapter3.setEndNote("End note 3");
        chapter3.setImageUrl("https://picsum.photos/id/3/200/300");
        chapter3.setNumbered(true);
        return chapter3;
    }

    public Chapter getSubChapter1() {
        Chapter subChapter1 = new Chapter();
        subChapter1.setId(4L);
        subChapter1.setTitle("Sub Chapter 1");
        subChapter1.setDescription("Description 4");
        subChapter1.setForeword("Foreword 4");
        subChapter1.setEndNote("End note 4");
        subChapter1.setImageUrl("https://picsum.photos/id/4/200/300");
        subChapter1.setNumbered(true);

        return subChapter1;
    }

    public Chapter getSubChapter2() {
        Chapter subChapter2 = new Chapter();
        subChapter2.setId(5L);
        subChapter2.setTitle("Sub Chapter 2");
        subChapter2.setDescription("Description 5");
        subChapter2.setForeword("Foreword 5");
        subChapter2.setEndNote("End note 5");
        subChapter2.setImageUrl("https://picsum.photos/id/5/200/300");
        subChapter2.setNumbered(true);

        return subChapter2;
    }
}
