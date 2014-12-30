package repositories;

import models.NDisease;
import models.NSymptom;
import models.NSymptomGroup;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 15:22
 */
public interface SymptomGroupRepository extends GraphRepository<NSymptomGroup> {

    NSymptomGroup getByCnText (String cnText);

    @Query( " start sg=node({0}) " +
            " match sg-[:INCLUDE]->symptom " +
            " return distinct symptom.id")
    List<Long> getRelatedSymptomIds (List<Long> symptomGroupIds);

    @Query( " start sg=node({0}) " +
                   " match sg-[:INCLUDE]->symptom " +
                   " return distinct symptom")
    List<NSymptom> getRelatedSymptoms (List<Long> symptomGroupIds);

    @Query( " start sg=node({0}) " +
            " match sg-[:INCLUDE]->symptom<-[:CAUSE]-disease " +
            " return distinct disease")
    List<NDisease> getRelatedDiseases (List<Long> symptomGroupIds);

    @Query( " start sg=node({0}) " +
            " match sg-[:INCLUDE]->symptom<-[:CAUSE]-disease " +
            " return distinct disease.id")
    List<Long> getRelatedDiseaseIds (List<Long> symptomGroupIds);
}
