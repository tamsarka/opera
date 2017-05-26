/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.entity;

import javax.persistence.Column;
/**
 * @author 10620506
 *
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INCIDENT_DETAILS")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1500516986755256732L;

	
    
	//@NotNull
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="INCIDENT_ID")
	private Long incidentID;
	//@NotNull
	private String assetName;
	//@NotNull
	private String  functionalID;
	//@NotNull
	private String serialNumber;
	//@NotNull
	private String modelNumber;
	private Date occurenceTime;
	private String priority;
	private String equipmentStatus;
	private String ticketStatusCurr;
	private String actualFailureArea;
	private String indicatedFailureArea;
	private String resolutionComments;
	private Date logDate;
	
	private String updatedByName;
	
	private Date updatedTime;
	private String resolvedBy;
	private Date resolvedDate;
	
	private String comments;
	/* @ApiModelProperty(hidden = true)	
		@OneToMany(mappedBy="ticketincidentid")	
	public Set<TicketComment> ticketcomment=new HashSet<TicketComment>();*/
	public Long getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(Long incidentID) {
		this.incidentID = incidentID;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getFunctionalID() {
		return functionalID;
	}

	public void setFunctionalID(String functionalID) {
		this.functionalID = functionalID;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public Date getOccurenceTime() {
		return occurenceTime;
	}

	public void setOccurenceTime(Date occurenceTime) {
		this.occurenceTime = occurenceTime;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public String getTicketStatusCurr() {
		return ticketStatusCurr;
	}

	public void setTicketStatusCurr(String ticketStatusCurr) {
		this.ticketStatusCurr = ticketStatusCurr;
	}

	public String getActualFailureArea() {
		return actualFailureArea;
	}

	public void setActualFailureArea(String actualFailureArea) {
		this.actualFailureArea = actualFailureArea;
	}

	public String getIndicatedFailureArea() {
		return indicatedFailureArea;
	}

	public void setIndicatedFailureArea(String indicatedFailureArea) {
		this.indicatedFailureArea = indicatedFailureArea;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getResolvedBy() {
		return resolvedBy;
	}

	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	public Date getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}