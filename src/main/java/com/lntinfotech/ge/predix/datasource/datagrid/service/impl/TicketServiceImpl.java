package com.lntinfotech.ge.predix.datasource.datagrid.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.Ticket;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketAsset;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketComment;
import com.lntinfotech.ge.predix.datasource.datagrid.repository.AssetListDAO;
import com.lntinfotech.ge.predix.datasource.datagrid.repository.TicketCommentDAO;
import com.lntinfotech.ge.predix.datasource.datagrid.repository.TicketDAO;
import com.lntinfotech.ge.predix.datasource.datagrid.service.TicketService;

@Service(value = "ticketService")
@Transactional
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDAO ticketDAO;
	
	@Autowired
	private AssetListDAO assetListDAO;
	
	@Autowired
	private TicketCommentDAO  ticketCommentDAO;
	
	@Override
	public Ticket createTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketDAO.save(ticket);
	}

	@Override
	public List<Ticket> FindAllTicket() {
		
		return ticketDAO.findAll();
	}

	
	
	@Override
	public void delete(Long incidentId) {
		// TODO Auto-generated method stub
		
	}
	/*@Override
	public TicketComments findOne(Long incidentId) {
		// TODO Auto-generated method stub
		return ticketDAO.findOne(incidentId);
	}*/

	@Override
	public List<TicketAsset> AssetList() {
		// TODO Auto-generated method stub
		return assetListDAO.findAll();
	}

	@Override
	public TicketAsset findByAssetId(Long assetId) {
		// TODO Auto-generated method stub
		return assetListDAO.findOne(assetId);
	}

	@Override
	public TicketAsset update(TicketAsset tAsset) {
		// TODO Auto-generated method stub
		return assetListDAO.save(tAsset);
	}

	@Override
	public Ticket findByTicketId(Long ticketId) {
		// TODO Auto-generated method stub
		return ticketDAO.findOne(ticketId);
	}

	@Override
	public TicketAsset updateSnooze(TicketAsset tAsset) {
		// TODO Auto-generated method stub
		return assetListDAO.save(tAsset);
	}

	
	@Override
	public TicketComment createComment(TicketComment ticketComment) {
		// TODO Auto-generated method stub
		return ticketCommentDAO.save(ticketComment);
		
	}
	
	@Override
	public List<TicketComment> FindAllComment() {
		return ticketCommentDAO.findAll();
	}
	
	@Override
	public List<TicketComment> findCommentByIncidentId(Long incidentid) {	
		return (List<TicketComment>) ticketCommentDAO.findByIncidentID(incidentid);
	}

	@Override
		
	public List<Ticket> openTicketListStatus() {
		// TODO Auto-generated method stub
		return(List<Ticket>)ticketDAO.openTicketList();
	}  	
}