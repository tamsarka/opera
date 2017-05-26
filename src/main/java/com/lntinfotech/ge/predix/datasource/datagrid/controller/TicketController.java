package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.Ticket;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketAsset;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketComment;
import com.lntinfotech.ge.predix.datasource.datagrid.repository.TicketDAO;
import com.lntinfotech.ge.predix.datasource.datagrid.repository.TicketRepository;
import com.lntinfotech.ge.predix.datasource.datagrid.service.TicketService;

/**
 * @author Virendra
 *
 */
@RestController
public class TicketController {
	
	@Autowired
	private TicketService ticketService; 
	@Autowired
	private TicketRepository ticketRepository;
	
	 public static final String TICKET = "/asset";
	 public static final String ASSET = "/asset";
	 public static final String SEARCH_CUSTOMERS = "/search";
	 public static final String GET_ASSET_BY_ID = ASSET + "/{id}";
	 public static final String GET_TICKETS_BY_ID = TICKET + "/{tid}";
	 public static final String UPDATE_ASSET_BY_ID = "/{id}" +"/{ackFlag}";
//	 public static final String UPDATE_SNOOZE_BY_ID = "UPDATE_SNOOZE_BY_ID/{idS}" +"/{snooz}";
	 public static final String GET_COMMENT_BY_INCIDENT_ID =  "/api/comment/{incidentid}";
	 
	 
	private Logger logger=LoggerFactory.getLogger(TicketController.class);
	
	/*
	   * Create the ticket .
	   */
	@RequestMapping(value = "/api/createticket", method = RequestMethod.POST)
    public Ticket createTicket(@RequestBody Ticket ticket) {
		
		//try {
				ticket = ticketService.createTicket(ticket);
		/*} catch (Exception ex) {
			 System.out.println(ex.getMessage()); 
		}
		*/
		return ticket;
    }
	/*
	   * get the All ticket values
	   */
	@RequestMapping(value = "/api/getAllTicket", method = RequestMethod.GET)
     public List<Ticket> geAllTicket()
	{
		return ticketService.FindAllTicket();	
		
	}
	/*
	   * create dashboard for insertion data
	   */
	@RequestMapping(value = "/api/createDashBoard", method = RequestMethod.POST)
    public void addAsset(@RequestBody TicketAsset ticketAsset){
         ticketRepository.createAssetValues(ticketAsset);
	}

	@RequestMapping(value = "/api/getAllAssetList", method = RequestMethod.GET)
    public List<TicketAsset> geAllAssetList(){
		return ticketService.AssetList();		
	}
	

	  /*
	   * Retrieve the Asset id for the asset.
	   */
	@RequestMapping(value =GET_ASSET_BY_ID, method = RequestMethod.GET)
    public TicketAsset assetById(@PathVariable  Long id) {
	  logger.debug("Provider has received request to get ticket with id: " + id);
	  return ticketService.findByAssetId(id);
    }
	  
	  /*
	   * Retrieve the Ticket id for the asset.	   
	   */
//	@RequestMapping(value =GET_TICKETS_BY_ID, method = RequestMethod.GET)
		@RequestMapping(value = "/api/{tid}", method = RequestMethod.GET)
		@ResponseBody
	    public Ticket ticktById(@PathVariable("tid")  Long tid) {
		  logger.debug("Provider has received request to get ticket with id: " + tid);
		  return ticketService.findByTicketId(tid);
	    }
	
	/*
	   * Upadte the ackknowldeg fileds based on Asset id.
	   */
	 @RequestMapping(value=UPDATE_ASSET_BY_ID,method = RequestMethod.POST)
	 public TicketAsset updateAck(@PathVariable Long id, @PathVariable String ackFlag) {
		 logger.debug("Upadte ticket with id: " + id);
		 TicketAsset ticketAsset=null; 
		 
		 try {
			 
			 ticketAsset=ticketService.findByAssetId(id);
				ticketAsset.setAckFlag(ackFlag);
				ticketAsset=ticketService.update(ticketAsset);
			
			
		} catch (Exception e) {
			e.getMessage();
		} 
			
		return ticketAsset;
	 }
		
 		@RequestMapping(value = "/api/assetStatusUpdate", method = RequestMethod.POST)
	    public void updateStatus(@RequestBody TicketAsset ticketAsset){
 			TicketAsset newAsset =ticketService.findByAssetId(ticketAsset.getAssetId());
 			newAsset.setTicketStatusCurr(ticketAsset.getTicketStatusCurr());
 			ticketService.update(newAsset);
	   }
 		
 		
 		@RequestMapping(value="/api/UPDATE_SNOOZE_BY_ID/{id}/{snooze}", method = RequestMethod.POST)
 		@ResponseBody
 		public TicketAsset updateSnoozTime(@PathVariable("id")  Long idS, @PathVariable("snooze") String snooz) {
			logger.debug("Update the snooze time" + snooz +" for ticket: " + idS);
			TicketAsset ticketSnooz=null; 
			try {
					 
					ticketSnooz=ticketService.findByAssetId(idS);
					ticketSnooz.setSnooz(snooz);
					ticketSnooz=ticketService.updateSnooze(ticketSnooz);
				}
			catch(Exception e) {
					e.getMessage();
				}	
				return ticketSnooz;
		}
 		
 		@RequestMapping(value =GET_COMMENT_BY_INCIDENT_ID, method = RequestMethod.GET)
	    public List<TicketComment> findcommentById(@PathVariable  Long incidentid) {
	        return ticketService.findCommentByIncidentId(incidentid);

	    }
	
		@RequestMapping(value = "/getAllComments", method = RequestMethod.GET)
		public List<TicketComment> getAllComment(){
			return ticketService.FindAllComment();
		}
			 
			 
		@RequestMapping(value ="/createcomment", method = RequestMethod.POST)
	    public TicketComment createCommentById(@RequestBody TicketComment ticketComment) {
			return ticketService.createComment(ticketComment);
	    }
		
		@RequestMapping(value = "/api/openTicketList", method = RequestMethod.GET)
	     public List<Ticket> openTicket()
		{
			return ticketService.openTicketListStatus();
			
		}
	}



	
	
