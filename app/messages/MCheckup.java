package messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: ecsark
 * Date: 12/31/14
 * Time: 02:34
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCheckup {

    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public Long id;

    @JsonProperty("imp")
    public Double importance;

    @JsonProperty("pricelo")
    public Double priceLow;

    @JsonProperty("pricehi")
    public Double priceHigh;
}
