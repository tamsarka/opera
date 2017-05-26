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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "DASHBOARD_DETAILS1")
public class TicketAsset implements Serializable {

	
	private static final long serialVersionUID = 1500516986755256732L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
	private Long assetId;
	
	private String assetName;
	private String  functionalID;
	//@NotNull
	private String serialNumber;
	//@NotNull
	private String modelNumber;
	@Column(name="occurenceTime")
	private Date occurenceTime;
	private String priority;
	private String equipmentStatus;
	private String ticketStatusCurr;
	private String actualFailureArea;
	private String indicatedFailureArea;
	private String resolutionComments;
	private String comments;
	private String ackFlag;
	private String snooz;
	
	
	
	public String getSnooz() {
		return snooz;
	}

	public void setSnooz(String snooz) {
		this.snooz = snooz;
	}

	public String getAckFlag() {
		return ackFlag;
	}

	public void setAckFlag(String ackFlag) {
		this.ackFlag = ackFlag;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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
	
		
}