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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"uri",
"name",
"description",
"obselete",
"attributes"
})
public class SolarClassification {

@JsonProperty("uri")
private String uri;
@JsonProperty("name")
private String name;
@JsonProperty("description")
private String description;
@JsonProperty("obselete")
private Boolean obselete;
@JsonProperty("attributes")
private SolarAttributes attributes;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
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
* @return
* The obselete
*/
@JsonProperty("obselete")
public Boolean getObselete() {
return obselete;
}

/**
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
* @return
* The attributes
*/
@JsonProperty("attributes")
public SolarAttributes getAttributes() {
return attributes;
}

/**
* 
* @param attributes
* The attributes
*/
@JsonProperty("attributes")
public void setAttributes(SolarAttributes attributes) {
this.attributes = attributes;
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