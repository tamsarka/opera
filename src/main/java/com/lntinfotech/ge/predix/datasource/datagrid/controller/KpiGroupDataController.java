/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.service.KpiDatagridService;

/**
 * @author 10620506
 *
 */
@RestController
public class KpiGroupDataController {
	
	
	/**
	 * 
	 */
	public KpiGroupDataController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private KpiDatagridService kpiDatagridService;
	
	/**
	 * @param entityType -
	 * @param id -
	 * @param authorization -
	 * @return -
	 * @throws Throwable -
	 */
	@RequestMapping(value = "/kpi/{entityType}/{id}/", method = RequestMethod.GET)
	public DatagridResponse getDatagrid(@PathVariable("entityType") String entityType,@PathVariable("id") String id,
			@RequestHeader(value="authorization") String authorization) throws Throwable{
		return kpiDatagridService.getDatagrid(entityType, id, authorization);
	}
	
	/**
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/kpi/ping", method = RequestMethod.GET)
	public String ping(){
		return "Ping from KPI Group Data Controller";
	}

}
