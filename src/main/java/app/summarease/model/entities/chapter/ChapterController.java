package app.summarease.model.entities.chapter;

import app.summarease.model.entities.chapter.dto.ChapterDto;
import app.summarease.model.entities.chapter.dto.ChapterToChapterDtoConverter;
import app.summarease.model.entities.system.Result;
import app.summarease.model.entities.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChapterController {

    private final ChapterService chapterService;

    private final ChapterToChapterDtoConverter chapterToChapterDtoConverter;

    public ChapterController(ChapterService chapterService, ChapterToChapterDtoConverter chapterToChapterDtoConverter) {
        this.chapterService = chapterService;
        this.chapterToChapterDtoConverter = chapterToChapterDtoConverter;
    }

    /**
     * Find a chapter by its id
     * @param chapterId the id of the chapter
     * @return the chapter
     */
    @GetMapping("/api/v1/chapters/{chapterId}")
    public Result findById(@PathVariable Long chapterId) {
        Chapter foundChapter = this.chapterService.findById(chapterId);
        ChapterDto foundChapterDto = this.chapterToChapterDtoConverter.convert(foundChapter);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundChapterDto);
    }

}
