package repositories;

import models.NDisease;
import models.NQuestion;
import models.NSymptom;
import models.NSymptomGroup;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 23:08
 */
public interface QuestionRepository extends GraphRepository<NQuestion> {
    NQuestion getByCnText (String cnText);

    @Query( " start question=node({0}) " +
            " match question-[:ASK]->symptom<-[:CAUSE]-disease " +
            " return disease")
    List<NDisease> getRelatedDiseases (long questionId);

    @Query( " start question=node({0}) " +
            " match question-[:ASK]->symptom<-[:CAUSE]-disease " +
            " return distinct disease")
    List<NDisease> getRelatedDiseases (List<Long> questionIds);

    @Query( " start question=node({0}) " +
            " match question-[:ASK]->symptom " +
            " return distinct symptom")
    List<NSymptom> getRelatedSymptoms (List<Long> questionIds);

    @Query( " start question=node({0}) " +
            " match question-[:ASK]->symptom-[] " +
            " return distinct symptom")
    List<NSymptomGroup> getRelatedSymptomGroups (List<Long> questionIds);
}
