/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import java.security.Principal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.entity.timeseries.datapoints.queryresponse.DatapointsResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardData;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardGraphData;
import com.lntinfotech.ge.predix.datasource.datagrid.service.TimeseriesServiceApi;

/**
 * @author 10620506
 *
 */
@RestController
@RequestMapping(value = "/services/solar")
public class TimeseriesController {
	
	@Autowired
	private TimeseriesServiceApi timeseriesService;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String greetings(){
		return timeseriesService.greetings();
	}

	@RequestMapping(value = "/yearly_data/sensor_id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DatapointsResponse getYearlyWindDataPoints(@PathVariable String id,			
			@RequestParam(value = "starttime", required = false, defaultValue = "1y-ago") String starttime,
			@RequestParam(value = "taglimit", required = false, defaultValue = "25") String tagLimit,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String tagorder,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getYearlyWindDataPoints(id, authorization, starttime, tagLimit, tagorder);
	}

	
	
	@RequestMapping(value = "/tag_data/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DatapointsResponse getTagDataPoints(@PathVariable String id,
			@RequestParam(value = "starttime", required = false, defaultValue = "1y-ago") String starttime,
			@RequestParam(value = "taglimit", required = false, defaultValue = "1") String tagLimit,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String tagorder,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getTagDataPoints(id, authorization, starttime, tagLimit, tagorder);
	}
	
	
	//Test with request mapping
	@RequestMapping(value = "/solarTimeseries/datapoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DatapointsResponse getTagDataPointsTest(@RequestParam("id") String id,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getTagDataPointsTest(id, authorization);
	}
	
	
	//latest tag values for solar
	@RequestMapping(value = "/latest_data/{{id}}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DatapointsResponse getLatestSolarDataPoints(@PathVariable ArrayList<String> id,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getLatestSolarDataPoints(id, authorization);
	}

	
	@RequestMapping(value = "/latest_data/sensor_id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DatapointsResponse getLatestWindDataPoints(@PathVariable String id,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getLatestWindDataPoints(id, authorization);
	}
	
	
	
	@RequestMapping(value = "/tags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getWindDataTags(Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getWindDataTags(authorization);
	}
	
	
	
	@RequestMapping(value = "/kpi_graph_data/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlantKpiDashboardGraphData getKpiGraphTags(@PathVariable("location") String location,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getKpiGraphTags(location, authorization);
	}
	
	
	@RequestMapping(value = "/kpi_data_org/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlantKpiDashboardData getKpiTags(@PathVariable("location") String location,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getKpiTags(location, authorization);
	}
	
	
	@RequestMapping(value = "/kpi_data/{location}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlantKpiDashboardData getPstKpiTags(@PathVariable("location") String location,Principal principal){
		String authorization = getAccessTokenByPrincipal(principal);
		return timeseriesService.getPstKpiTags(location, authorization);
	}
	
	private String getAccessTokenByPrincipal(Principal principal){
		String info = "";
		StringBuilder assetJSON = new StringBuilder();
		assetJSON.append("hello ");
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			//Object to JSON in String
			String jsonInString = mapper.writeValueAsString(principal);
			info = jsonInString.substring(jsonInString.indexOf("tokenValue") + 13, jsonInString.indexOf("tokenType") - 3);
			  }
		catch(Exception e)
		{
			assetJSON.append("json errorrrr  " + e);
		}
		String accessToken = "Bearer "+info;
		return accessToken;
	}

}
