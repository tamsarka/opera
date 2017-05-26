package com.lntinfotech.ge.predix.datasource.datagrid.entity;

/**** 10612804 Devashree Khot*/
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;


import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;


@Entity
//@NamedQuery(name = "findByIncidentID", query = "select t from TicketComment t where t.ticketincidentid =?1")
@Table(name="ISSUE_LOG_TBL")
public class TicketComment implements Serializable {
	
	private static final long serialVersionUID = 1500516986755256732L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull
	private Long logid;
	
	@Column(name="COMMENTED_BY_NAME")
	 private String commenteByUser ;
	
	@Column(name="COMMENTED_DESC")
	 private String commentDesc ;
	  
	
	  @Column(name="LAST_UPDATED_DATE")
	 private Date lastupdatedDate ;

	  
 @JoinColumn(name="INCIDENT_ID" ,referencedColumnName="ticketincidentid")
private Long ticketincidentid;
		
		/*
		 @ApiModelProperty(hidden = true)	 
		
		  @JoinColumn(name="INCIDENT_ID")
private Ticket ticket;
	*/
	  
	/*  @Column(name="INCIDENT_ID")
	  private Long incidentID;*/
	
	


	

	/* @ApiModelProperty(hidden = true)
	@ManyToOne
	 @JoinColumn(name = "INCIDENT_ID")*/




	




	public Long getTicketincidentid() {
	return ticketincidentid;
}


public void setTicketincidentid(Long ticketincidentid) {
	this.ticketincidentid = ticketincidentid;
}


	public String getCommenteByUser() {
		return commenteByUser;
	}


	public Long getLogid() {
		return logid;
	}


	public void setLogid(Long logid) {
		this.logid = logid;
	}


	public void setCommenteByUser(String commenteByUser) {
		this.commenteByUser = commenteByUser;
	}


	public String getCommentDesc() {
		return commentDesc;
	}


	public void setCommentDesc(String commentDesc) {
		this.commentDesc = commentDesc;
	}


	public Date getLastupdatedDate() {
		return lastupdatedDate;
	}


	public void setLastupdatedDate(Date lastupdatedDate) {
		this.lastupdatedDate = lastupdatedDate;
	}


/*	public Long getTicketincidentid(Long incidentid) {
		
		// TODO Auto-generated method stub
		return ticketincidentid;
		
	}
*/

	  

	
	
	
	/*
	private Ticket comments;

	public TicketAssetId getTicketassetid() {
		return ticketassetid;
	}

	public void setTicketassetid(TicketAssetId ticketassetid) {
		this.ticketassetid = ticketassetid;
	}

	public Ticket getComments() {
		return comments;
	}

	public void setComments(Ticket comments) {
		this.comments = comments;
	}

	public TicketComment() {
		
		// TODO Auto-generated constructor stub
	}

	public TicketComment(TicketAssetId ticketassetid, Ticket comments) {
		
		this.ticketassetid = ticketassetid;
		this.comments = comments;
	}
	
*/
	
	
}
