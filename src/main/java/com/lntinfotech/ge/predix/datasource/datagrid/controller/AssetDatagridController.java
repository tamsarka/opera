/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SummaryKpiDataGridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.service.AssetDatagridService;

/**
 * @author 10620506
 *
 */
@RestController
@RequestMapping("/experience/datasource/datagrid/asset")
public class AssetDatagridController {
	
	@Autowired
	private AssetDatagridService assetDatagridService;
	
	/**
	 * API for the Asset GroupDataGrid to construct the widget
	 * 
	 * @param id : id
	 * @param appId : appId
	 * @param authorization : authorization
	 * @return Response 
	 * @throws Throwable throwable
	 */
	
	@RequestMapping(value = "/{id}/", method = RequestMethod.GET)
	public DatagridResponse getDatagrid(@PathVariable String id,
			@RequestParam("appId") String appId)
			throws Throwable{
		String authorization = null;
		return assetDatagridService.getDatagrid(id, appId, authorization);
	}

	/**
	 * 
	 * @return String
	 */
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping(){
		return "Hello Asset Data Grid String";
	}
	
	
	/**
	 * @param id : id
	 * @param appId : appId
	 * @param authorization : authorization
	 * @param start_time : start_time (Format: "1y-ago" )
	 * @param end_time : end_time (Format: "1y-ago")
	 * @return Response
	 * @throws Throwable : Throwable
	 */
	
	@RequestMapping(value = "/{id}/summary", method = RequestMethod.GET)
	public SummaryKpiDataGridResponse getDatagridSummary(@PathVariable String id,
	        @RequestParam("appId") String appId,   
	        @RequestParam("start_time") String start_time , 
	        @RequestParam("end_time") String end_time) throws Throwable{
		String authorization = null;
		return assetDatagridService.getDatagridSummary(id, appId, authorization, start_time, end_time);
	}

}
