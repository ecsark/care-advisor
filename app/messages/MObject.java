package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 17:18
 */
public class MObject {

    public MObject() {}

    public MObject(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public MObject setParam(String key, Object value) {
        if (params == null)
            params = new LinkedHashMap<>();
        params.put(key, value);
        return this;
    }

    public Object getParam(String key) {
        if (params == null)
            return null;
        return params.get(key);
    }

    @JsonProperty("name")
    public String name;

    public MObject setName(String name) {
        this.name = name;
        return this;
    }

    public MObject setId(long id) {
        this.id = id;
        return this;
    }

    @JsonProperty("id")
    public long id;

    @JsonProperty("p")
    Map<String, Object> params;
}
