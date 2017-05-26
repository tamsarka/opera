package com.lntinfotech.ge.predix.datasource.datagrid.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketAsset;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TicketRepository {

    public static final String TICKETS = "tickets";

    @PersistenceContext
    private EntityManager em;


   public void createAssetValues(TicketAsset ticketAssest){
	   
	   em.merge(ticketAssest);
	   
   }
   
   /* Update the passed user in the database.
   */
  public void update(TicketAsset ticketAssest) {
	  em.merge(ticketAssest);
    return;
  }
   
   
   /* public TicketAsset createAssetName(String assetName,String functionId,String serialNumber,
    		 String modelNumber,Date occurenceTime,String priority,String equipmentStatus,String ticketStatusCurr,String actualFailureArea,
    		 String indicatedFailureArea,String resolutionComments,String comments)
    {
    	TicketAsset ticketasset = new TicketAsset();
    	ticketasset.setAssetName(assetName);
    	ticketasset.setFunctionalID(functionId);
    	ticketasset.setSerialNumber(serialNumber);
    	ticketasset.setModelNumber(modelNumber);
    	ticketasset.setOccurenceTime((java.sql.Date) occurenceTime);
    	ticketasset.setPriority(priority);
    	ticketasset.setEquipmentStatus(equipmentStatus);
    	ticketasset.setTicketStatusCurr(ticketStatusCurr);
    	ticketasset.setActualFailureArea(actualFailureArea);
    	ticketasset.setIndicatedFailureArea(indicatedFailureArea);
    	ticketasset.setResolutionComments(resolutionComments);
    	ticketasset.setComments(comments);
    	   	
    	System.out.println("Virendra>>>>>>>>>>>>>>>>");
    	em.persist(ticketasset);
         return ticketasset;
    	
    	
    }*/
   
    
    
}