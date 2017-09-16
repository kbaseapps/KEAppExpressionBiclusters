
package keappexpressionbiclusters;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: ConstructExprBiclustersOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "new_re_nodes",
    "updated_re_nodes",
    "new_re_links",
    "properties_set",
    "message"
})
public class ConstructExprBiclustersOutput {

    @JsonProperty("new_re_nodes")
    private Long newReNodes;
    @JsonProperty("updated_re_nodes")
    private Long updatedReNodes;
    @JsonProperty("new_re_links")
    private Long newReLinks;
    @JsonProperty("properties_set")
    private Long propertiesSet;
    @JsonProperty("message")
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("new_re_nodes")
    public Long getNewReNodes() {
        return newReNodes;
    }

    @JsonProperty("new_re_nodes")
    public void setNewReNodes(Long newReNodes) {
        this.newReNodes = newReNodes;
    }

    public ConstructExprBiclustersOutput withNewReNodes(Long newReNodes) {
        this.newReNodes = newReNodes;
        return this;
    }

    @JsonProperty("updated_re_nodes")
    public Long getUpdatedReNodes() {
        return updatedReNodes;
    }

    @JsonProperty("updated_re_nodes")
    public void setUpdatedReNodes(Long updatedReNodes) {
        this.updatedReNodes = updatedReNodes;
    }

    public ConstructExprBiclustersOutput withUpdatedReNodes(Long updatedReNodes) {
        this.updatedReNodes = updatedReNodes;
        return this;
    }

    @JsonProperty("new_re_links")
    public Long getNewReLinks() {
        return newReLinks;
    }

    @JsonProperty("new_re_links")
    public void setNewReLinks(Long newReLinks) {
        this.newReLinks = newReLinks;
    }

    public ConstructExprBiclustersOutput withNewReLinks(Long newReLinks) {
        this.newReLinks = newReLinks;
        return this;
    }

    @JsonProperty("properties_set")
    public Long getPropertiesSet() {
        return propertiesSet;
    }

    @JsonProperty("properties_set")
    public void setPropertiesSet(Long propertiesSet) {
        this.propertiesSet = propertiesSet;
    }

    public ConstructExprBiclustersOutput withPropertiesSet(Long propertiesSet) {
        this.propertiesSet = propertiesSet;
        return this;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public ConstructExprBiclustersOutput withMessage(String message) {
        this.message = message;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((((((((((("ConstructExprBiclustersOutput"+" [newReNodes=")+ newReNodes)+", updatedReNodes=")+ updatedReNodes)+", newReLinks=")+ newReLinks)+", propertiesSet=")+ propertiesSet)+", message=")+ message)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
