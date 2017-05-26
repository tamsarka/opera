/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SummaryKpiDataGridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.service.GroupDatagridService;

/**
 * @author 10620506
 *
 */
@RestController
@RequestMapping("/experience/datasource/datagrid/group")
public class GroupDatagridController {
	
	@Autowired
	private GroupDatagridService groupDatagridService;
	
	/**
     * API for the Asset GroupDataGrid
     * 
     * @param id : id
     * @param appId : appid
     * @param authorization : authorization
     * @param start_time -
     * @param end_time -
     * @return Response
     * @throws Throwable Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DatagridResponse getDatagrid(@PathVariable("id") String id, @RequestParam("appId") String appId,
            @RequestHeader(value = "authorization") String authorization, @RequestParam("start_time") String start_time,
            @RequestParam("end_time") String end_time)
            throws Throwable{
    	return groupDatagridService.getDatagrid(id, appId, authorization, start_time, end_time);
    }

    /**
     * 
     * @return String
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping(){
    	return "Hello From Group Data Grid Service";
    }

    /**
     * 
     * @param id : id
     * @param appId : application Id
     * @param authorization : authorization
     * @param start_time : start time
     * @param end_time : end _time
     * @return Response
     * @throws Throwable Throwable
     */
    @RequestMapping(value = "/{id}/summary", method = RequestMethod.GET)
    public SummaryKpiDataGridResponse getDatagridSummary(@PathVariable("id") String id, @RequestParam("appId") String appId,
            @RequestHeader(value = "authorization") String authorization, @RequestParam("start_time") String start_time,
            @RequestParam("end_time") String end_time)
            throws Throwable{
    	return groupDatagridService.getDatagridSummary(id, appId, authorization, start_time, end_time);
    }

}
