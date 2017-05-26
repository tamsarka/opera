package com.lntinfotech.ge.predix.datasource.datagrid.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketComment;abstract


/* @author Devashree*/
@Repository(value="ticketCommentDAO")
public interface TicketCommentDAO extends JpaRepository<TicketComment, Long> {
	@Query("select t from TicketComment t where t.ticketincidentid =?1")
	List<TicketComment> findByIncidentID(Long incidentid);
}
