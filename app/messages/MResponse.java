package messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * User: ecsark
 * Date: 12/25/14
 * Time: 00:37
 */
public interface MResponse {
    @JsonIgnore
    int getResponseType ();
}