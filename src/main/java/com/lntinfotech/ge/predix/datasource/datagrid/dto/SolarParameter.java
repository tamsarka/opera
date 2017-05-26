package com.lntinfotech.ge.predix.datasource.datagrid.dto;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"@type",
"uri",
"name",
"asset",
"Min",
"Max",
"description",
"tagType",
"unit"
})
public class SolarParameter {

/**
* 
* (Required)
* 
*/
@JsonProperty("@type")
private String type;
/**
* 
* (Required)
* 
*/
@JsonProperty("uri")
private String uri;
/**
* 
* (Required)
* 
*/
@JsonProperty("name")
private String name;
/**
* 
* (Required)
* 
*/
@JsonProperty("asset")
private String asset;
@JsonProperty("MinPowerOutput")
private String minPowerOutput;
@JsonProperty("MaxPowerOutput")
private String maxPowerOutput;
@JsonProperty("description")
private String description;
@JsonProperty("tagType")
private String tagType;
@JsonProperty("unit")
private String unit;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* (Required)
* 
* @return
* The type
*/
@JsonProperty("@type")
public String getType() {
return type;
}

/**
* 
* (Required)
* 
* @param type
* The @type
*/
@JsonProperty("@type")
public void setType(String type) {
this.type = type;
}

/**
* 
* (Required)
* 
* @return
* The uri
*/
@JsonProperty("uri")
public String getUri() {
return uri;
}

/**
* 
* (Required)
* 
* @param uri
* The uri
*/
@JsonProperty("uri")
public void setUri(String uri) {
this.uri = uri;
}

/**
* 
* (Required)
* 
* @return
* The name
*/
@JsonProperty("name")
public String getName() {
return name;
}

/**
* 
* (Required)
* 
* @param name
* The name
*/
@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

/**
* 
* (Required)
* 
* @return
* The asset
*/
@JsonProperty("asset")
public String getAsset() {
return asset;
}

/**
* 
* (Required)
* 
* @param asset
* The asset
*/
@JsonProperty("asset")
public void setAsset(String asset) {
this.asset = asset;
}

/**
* 
* @return
* The minPowerOutput
*/
@JsonProperty("MinPowerOutput")
public String getMinPowerOutput() {
return minPowerOutput;
}

/**
* 
* @param minPowerOutput
* The MinPowerOutput
*/
@JsonProperty("MinPowerOutput")
public void setMinPowerOutput(String minPowerOutput) {
this.minPowerOutput = minPowerOutput;
}

/**
* 
* @return
* The maxPowerOutput
*/
@JsonProperty("MaxPowerOutput")
public String getMaxPowerOutput() {
return maxPowerOutput;
}

/**
* 
* @param maxPowerOutput
* The MaxPowerOutput
*/
@JsonProperty("MaxPowerOutput")
public void setMaxPowerOutput(String maxPowerOutput) {
this.maxPowerOutput = maxPowerOutput;
}

/**
* 
* @return
* The description
*/
@JsonProperty("description")
public String getDescription() {
return description;
}

/**
* 
* @param description
* The description
*/
@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

/**
* 
* @return
* The tagType
*/
@JsonProperty("tagType")
public String getTagType() {
return tagType;
}

/**
* 
* @param tagType
* The tagType
*/
@JsonProperty("tagType")
public void setTagType(String tagType) {
this.tagType = tagType;
}

/**
* 
* @return
* The unit
*/
@JsonProperty("unit")
public String getUnit() {
return unit;
}

/**
* 
* @param unit
* The unit
*/
@JsonProperty("unit")
public void setUnit(String unit) {
this.unit = unit;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
