package repositories;

import models.NDisease;
import models.NQuestionGroup;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 23:08
 */
public interface QuestionGroupRepository extends GraphRepository<NQuestionGroup> {
    NQuestionGroup getByCnText (String cnText);

    @Query( " start question=node({0}) " +
            " match question-[:ASKS]->symptom<-[:CAUSES]-disease " +
            " return disease")
    List<NDisease> getRelatedDiseases (NQuestionGroup question);
}
