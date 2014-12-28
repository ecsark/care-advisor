package models;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 16:37
 */
@NodeEntity
public class NSymptom extends AbstractEntity {

    public String cnText;

    public Iterable<NDisease> diseases;

    public Iterable<NSession> sessions;

    @RelatedTo(type="ASKS")
    public NQuestionGroup question;
}
