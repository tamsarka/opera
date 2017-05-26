/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.Ticket;
import com.lntinfotech.ge.predix.datasource.datagrid.entity.TicketAsset;

/**
 * @author Virendra
 *
 */
@Repository(value="assetListDAO")
public interface AssetListDAO extends JpaRepository<TicketAsset, Long>{


}