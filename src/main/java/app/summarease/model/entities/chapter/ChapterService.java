package app.summarease.model.entities.chapter;

import app.summarease.model.entities.system.exceptions.ObjectNotFoundException;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service @Transactional
public class ChapterService {

    private final ChapterRepository chapterRepository;

    /**
     * Constructor
     * @param chapterRepository the chapter repository
     */
    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    /**
     * Find a chapter by its id
     * @param chapterId the id of the chapter
     * @return the chapter
     * @throws ObjectNotFoundException if the chapter could not be found
     */
    @Observed(name = "chapter", contextualName = "findByIdService")
    public Chapter findById(Integer chapterId) throws ObjectNotFoundException {
       return this.chapterRepository.findById(chapterId)
               .orElseThrow(() -> new ObjectNotFoundException("chapter", chapterId));
    }
}
