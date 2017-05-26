package com.lntinfotech.ge.predix.datasource.datagrid.service;

import java.util.ArrayList;
import com.ge.predix.entity.timeseries.datapoints.queryresponse.DatapointsResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardData;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardGraphData;

public interface TimeseriesServiceApi {

	public String greetings();

	public DatapointsResponse getYearlyWindDataPoints(String id, String authorization, String starttime,
			String tagLimit, String tagorder);

	public DatapointsResponse getTagDataPoints(String id, String authorization, String starttime, String tagLimit,
			String tagorder);

	public DatapointsResponse getTagDataPointsTest(String id, String authorization);

	public DatapointsResponse getLatestSolarDataPoints(ArrayList<String> id, String authorization);

	public DatapointsResponse getLatestWindDataPoints(String id, String authorization);

	public String getWindDataTags(String authorization);

	public PlantKpiDashboardGraphData getKpiGraphTags(String location, String authorization);

	public PlantKpiDashboardData getKpiTags(String location, String authorization);

	public PlantKpiDashboardData getPstKpiTags(String location, String authorization);

}
