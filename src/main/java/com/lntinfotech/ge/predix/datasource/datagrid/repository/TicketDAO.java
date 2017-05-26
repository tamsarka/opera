/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.Ticket;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketAsset;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketComment;

/**
 * @author Virendra
 *
 */
@Repository(value="ticketDAO")
public interface TicketDAO extends JpaRepository<Ticket, Long>{
	@Query(nativeQuery = true, 
	value="select * from INCIDENT_DETAILS where TICKETSTATUSCURR ='open'")
	List<Ticket> openTicketList();
	

	
}