package models;

import org.springframework.data.neo4j.annotation.GraphId;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 19:55
 */
public abstract class AbstractEntity {
    @GraphId
    public Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
