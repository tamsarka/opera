package com.lntinfotech.ge.predix.datasource.datagrid.dto;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ge.predix.solsvc.bootstrap.ams.dto.CustomModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"@type",
"uri",
"name",
"description",
"classification",
"group",
"manufacturer",
"supplier",
"obselete",
"assetId",
"parameters"
})
public class SolarAsset extends CustomModel{

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
@JsonProperty("name")
private String name;
@JsonProperty("description")
private String description;
/**
* 
* (Required)
* 
*/
@JsonProperty("classification")
private String classification;
/**
* 
* (Required)
* 
*/
@JsonProperty("group")
private String group;
@JsonProperty("manufacturer")
private String manufacturer;
@JsonProperty("supplier")
private String supplier;
/**
* 
* (Required)
* 
*/
@JsonProperty("obselete")
private Boolean obselete;
/**
* 
* (Required)
* 
*/
@JsonProperty("assetId")
private String assetId;
@JsonProperty("parameters")
private SolarParameter parameters;
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
* @return
* The name
*/
@JsonProperty("name")
public String getName() {
return name;
}

/**
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
* (Required)
* 
* @return
* The classification
*/
@JsonProperty("classification")
public String getClassification() {
return classification;
}

/**
* 
* (Required)
* 
* @param classification
* The classification
*/
@JsonProperty("classification")
public void setClassification(String classification) {
this.classification = classification;
}

/**
* 
* (Required)
* 
* @return
* The group
*/
@JsonProperty("group")
public String getGroup() {
return group;
}

/**
* 
* (Required)
* 
* @param group
* The group
*/
@JsonProperty("group")
public void setGroup(String group) {
this.group = group;
}

/**
* 
* @return
* The manufacturer
*/
@JsonProperty("manufacturer")
public String getManufacturer() {
return manufacturer;
}

/**
* 
* @param manufacturer
* The manufacturer
*/
@JsonProperty("manufacturer")
public void setManufacturer(String manufacturer) {
this.manufacturer = manufacturer;
}

/**
* 
* @return
* The supplier
*/
@JsonProperty("supplier")
public String getSupplier() {
return supplier;
}

/**
* 
* @param supplier
* The supplier
*/
@JsonProperty("supplier")
public void setSupplier(String supplier) {
this.supplier = supplier;
}

/**
* 
* (Required)
* 
* @return
* The obselete
*/
@JsonProperty("obselete")
public Boolean getObselete() {
return obselete;
}

/**
* 
* (Required)
* 
* @param obselete
* The obselete
*/
@JsonProperty("obselete")
public void setObselete(Boolean obselete) {
this.obselete = obselete;
}

/**
* 
* (Required)
* 
* @return
* The assetId
*/
@JsonProperty("assetId")
public String getAssetId() {
return assetId;
}

/**
* 
* (Required)
* 
* @param assetId
* The assetId
*/
@JsonProperty("assetId")
public void setAssetId(String assetId) {
this.assetId = assetId;
}

/**
* 
* @return
* The parameters
*/
@JsonProperty("parameters")
public SolarParameter getParameters() {
return parameters;
}

/**
* 
* @param parameters
* The parameters
*/
@JsonProperty("parameters")
public void setParameters(SolarParameter parameters) {
this.parameters = parameters;
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
