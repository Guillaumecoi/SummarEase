package app.summarease.model.entities.chapter;

import app.summarease.model.entities.system.Result;
import app.summarease.model.entities.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChapterController {

    private final ChapterService chapterService;

    /**
     * Constructor
     * @param chapterService the chapter service
     */
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    /**
     * Find a chapter by its id
     * @param chapterId the id of the chapter
     * @return the chapter
     */
    @GetMapping("/api/v1/chapters/{chapterId}")
    public Result findById(@PathVariable String chapterId) {
        Chapter foundChapter = this.chapterService.findById(chapterId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundChapter);
    }
}
