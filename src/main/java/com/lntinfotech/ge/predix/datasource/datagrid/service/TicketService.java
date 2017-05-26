package com.lntinfotech.ge.predix.datasource.datagrid.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.lntinfotech.ge.predix.datasource.datagrid.entity.Ticket;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketAsset;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketComment;


public interface TicketService {

	
	public List<Ticket> FindAllTicket();
	public Ticket createTicket(Ticket ticket);
	public List<TicketAsset> AssetList();
	public TicketAsset findByAssetId(Long assetId );
	public TicketAsset update(TicketAsset tAsset);
	public Ticket findByTicketId(Long ticketId );
	public void delete(Long incidentId);
	public TicketAsset updateSnooze(TicketAsset tAsset);

	/*@author Devashree Khot*/
	public TicketComment createComment(TicketComment ticketComment);
	public List<TicketComment> FindAllComment();
	public List<TicketComment> findCommentByIncidentId(Long incidentid);
	/*santanu*/
	public List<Ticket> openTicketListStatus();
	
}
