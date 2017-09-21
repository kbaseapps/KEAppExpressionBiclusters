
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
 * <p>Original spec-file type: TestBiclusteringParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "app_guid",
    "data_type",
    "dist_metric",
    "dist_threshold",
    "fcluster_criterion",
    "linkage_method"
})
public class TestBiclusteringParams {

    @JsonProperty("app_guid")
    private String appGuid;
    @JsonProperty("data_type")
    private String dataType;
    @JsonProperty("dist_metric")
    private String distMetric;
    @JsonProperty("dist_threshold")
    private Double distThreshold;
    @JsonProperty("fcluster_criterion")
    private String fclusterCriterion;
    @JsonProperty("linkage_method")
    private String linkageMethod;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("app_guid")
    public String getAppGuid() {
        return appGuid;
    }

    @JsonProperty("app_guid")
    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public TestBiclusteringParams withAppGuid(String appGuid) {
        this.appGuid = appGuid;
        return this;
    }

    @JsonProperty("data_type")
    public String getDataType() {
        return dataType;
    }

    @JsonProperty("data_type")
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public TestBiclusteringParams withDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    @JsonProperty("dist_metric")
    public String getDistMetric() {
        return distMetric;
    }

    @JsonProperty("dist_metric")
    public void setDistMetric(String distMetric) {
        this.distMetric = distMetric;
    }

    public TestBiclusteringParams withDistMetric(String distMetric) {
        this.distMetric = distMetric;
        return this;
    }

    @JsonProperty("dist_threshold")
    public Double getDistThreshold() {
        return distThreshold;
    }

    @JsonProperty("dist_threshold")
    public void setDistThreshold(Double distThreshold) {
        this.distThreshold = distThreshold;
    }

    public TestBiclusteringParams withDistThreshold(Double distThreshold) {
        this.distThreshold = distThreshold;
        return this;
    }

    @JsonProperty("fcluster_criterion")
    public String getFclusterCriterion() {
        return fclusterCriterion;
    }

    @JsonProperty("fcluster_criterion")
    public void setFclusterCriterion(String fclusterCriterion) {
        this.fclusterCriterion = fclusterCriterion;
    }

    public TestBiclusteringParams withFclusterCriterion(String fclusterCriterion) {
        this.fclusterCriterion = fclusterCriterion;
        return this;
    }

    @JsonProperty("linkage_method")
    public String getLinkageMethod() {
        return linkageMethod;
    }

    @JsonProperty("linkage_method")
    public void setLinkageMethod(String linkageMethod) {
        this.linkageMethod = linkageMethod;
    }

    public TestBiclusteringParams withLinkageMethod(String linkageMethod) {
        this.linkageMethod = linkageMethod;
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
        return ((((((((((((((("TestBiclusteringParams"+" [appGuid=")+ appGuid)+", dataType=")+ dataType)+", distMetric=")+ distMetric)+", distThreshold=")+ distThreshold)+", fclusterCriterion=")+ fclusterCriterion)+", linkageMethod=")+ linkageMethod)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
