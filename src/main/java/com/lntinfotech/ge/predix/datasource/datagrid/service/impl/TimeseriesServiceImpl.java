package com.lntinfotech.ge.predix.datasource.datagrid.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ge.predix.entity.timeseries.datapoints.queryrequest.DatapointsQuery;
import com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag;
import com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.DatapointsLatestQuery;
import com.ge.predix.entity.timeseries.datapoints.queryresponse.DatapointsResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardData;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardGraphData;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.PlantKpiDashboardValues;
import com.lntinfotech.ge.predix.datasource.datagrid.service.TimeseriesServiceApi;
import com.ge.predix.solsvc.restclient.impl.RestClient;
import com.ge.predix.solsvc.timeseries.bootstrap.config.TimeseriesRestConfig;
import com.ge.predix.solsvc.timeseries.bootstrap.factories.TimeseriesFactory;


@Service(value="timeseriesService")
public class TimeseriesServiceImpl implements TimeseriesServiceApi{
	private static Logger log = LoggerFactory.getLogger(TimeseriesServiceImpl.class);


	@Autowired
	private TimeseriesRestConfig timeseriesRestConfig;

	@Autowired
	private RestClient restClient;

	@Autowired
	private TimeseriesFactory timeseriesFactory;
	
	/*@PostConstruct
	public void init() {
		List<Header> headers = generateHeaders(authorization);
		headers.add(new BasicHeader("Origin", "http://predix.io"));
	}*/
	
	private List<Header> generateHeaders()
    {
        List<Header> headers = this.restClient.getSecureTokenForClientId();
        this.restClient.addZoneToHeaders(headers,this.timeseriesRestConfig.getZoneId());
		return headers;
    }
	
	private List<Header> generateHeaders(String authorization){
		List<Header> headers = new ArrayList<>();
		if(authorization != null && !(authorization.equals(""))){
			//log.info("++++++++++++++++++++++++++++++++++ inside header ++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			headers = this.restClient.addSecureTokenToHeaders(headers,authorization);
			headers = this.restClient.addZoneToHeaders(headers,this.timeseriesRestConfig.getZoneId());
		}else{
			//log.info("++++++++++++++++++++++++++++++++++ inside without header ++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			headers = generateHeaders();
		}
		/*log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		for(Header hd : headers){
			log.info(hd.toString());
		}
		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/
		return headers;
	}
	
	public DatapointsResponse getYearlyWindDataPoints(String id, String authorization,String starttime,String taglimit,String tagorder) {
		if (id == null) {
			return null;
		}
		
		List<Header> headers = generateHeaders(authorization);

		DatapointsQuery dpQuery = buildDatapointsQueryRequest(id, starttime,getInteger(taglimit),tagorder);
		DatapointsResponse response = this.timeseriesFactory
				.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(),
						dpQuery, headers);
		log.debug(response.toString());

		return response;
	}

	
	@Override
	public String greetings() {
		// TODO Auto-generated method stub
		return "Greetings from Solar Bean Rest Service " + new Date();
	}

	@Override
	public DatapointsResponse getLatestWindDataPoints(String id, String authorization) {
		//System.out.println("=============latest========="+id);
		//String s[]=id.split(",");
		if (id == null) {
			return null;
		}
		List<Header> headers = generateHeaders(authorization);
		//System.out.println("=============latest=========Split"+s[0]+"==="+s[1]);

		DatapointsLatestQuery dpQuery = buildLatestDatapointsQueryRequest(id);
		DatapointsResponse response = this.timeseriesFactory
				.queryForLatestDatapoint(
						this.timeseriesRestConfig.getBaseUrl(), dpQuery,
						headers);
		log.debug(response.toString());
		

		return response;	
	}

	@Override
	public String getWindDataTags(String authorization) {
		List<Header> headers = generateHeaders(authorization);
		CloseableHttpResponse httpResponse = null;
		String entity = null;
		try {
			httpResponse = this.restClient
					.get(this.timeseriesRestConfig.getBaseUrl() + "/v1/tags", headers, this.timeseriesRestConfig.getTimeseriesConnectionTimeout(), this.timeseriesRestConfig.getTimeseriesSocketTimeout()); //$NON-NLS-1$

			if (httpResponse.getEntity() != null) {
				try {
					entity = processHttpResponseEntity(httpResponse.getEntity());
					log.debug("HttpEntity returned from Tags" + httpResponse.getEntity().toString()); //$NON-NLS-1$
				} catch (IOException e) {
					throw new RuntimeException(
							"Error occured calling=" + this.timeseriesRestConfig.getBaseUrl() + "/v1/tags", e); //$NON-NLS-1$//$NON-NLS-2$
				}
			}
		} finally {
			if (httpResponse != null)
				try {
					httpResponse.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}

		return entity;
	}

	
	//Solar tag data
	@Override
	public DatapointsResponse getTagDataPoints(String id, String authorization, String starttime, String taglimit,
			String tagorder) {
		List<Header> headers = generateHeaders(authorization);

		DatapointsQuery dpQuery = buildDatapointsQueryRequest(id, starttime,getInteger(taglimit),tagorder);
		DatapointsResponse response = this.timeseriesFactory
				.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(),
						dpQuery, headers);
		log.debug(response.toString());

		return response;
		
	}

	// get Solar latest tag data	
	public DatapointsResponse getLatestSolarDataPoints(ArrayList<String> id, String authorization) {
		if (id == null) {
			return null;
		}
		List<Header> headers = generateHeaders(authorization);

		DatapointsLatestQuery dpQuery = buildLatestDatapointsQueryRequestSolar(id);
		DatapointsResponse response = this.timeseriesFactory
				.queryForLatestDatapoint(
						this.timeseriesRestConfig.getBaseUrl(), dpQuery,
						headers);
		log.debug(response.toString());

		return response;	
	}
	
	
	private int getInteger(String s) {
		int inValue = 25;
		try {
			inValue = Integer.parseInt(s);
			
		} catch (NumberFormatException ex) {
			// s is not an integer
		}
		return inValue;
	}
	
	
	
	
	
	/*@SuppressWarnings("javadoc")
	protected Response handleResult(Object entity) {
		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.type(MediaType.APPLICATION_JSON);
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}*/

	/*private Long generateTimestampsWithinYear(Long current) {
		long yearInMMS = Long.valueOf(31536000000L);
		return ThreadLocalRandom.current().nextLong(current - yearInMMS,
				current + 1);
	}*/
	
	
	private DatapointsQuery buildDatapointsQueryRequest(String id,
			String startDuration, int taglimit, String tagorder) {
		DatapointsQuery datapointsQuery = new DatapointsQuery();
		List<com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag> tags = new ArrayList<com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag>();
		datapointsQuery.setStart(startDuration);
		//datapointsQuery.setStart("1y-ago"); //$NON-NLS-1$
		String[] tagArray = id.split(","); //$NON-NLS-1$
		List<String> entryTags = Arrays.asList(tagArray);

		for (String entryTag : entryTags) {
			com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag tag = new com.ge.predix.entity.timeseries.datapoints.queryrequest.Tag();
			tag.setName(entryTag);
			tag.setLimit(taglimit);
			tag.setOrder(tagorder); 
			tags.add(tag);
		}
		datapointsQuery.setTags(tags);
		return datapointsQuery;
	}

	
	private DatapointsLatestQuery buildLatestDatapointsQueryRequest(String id) {
		DatapointsLatestQuery datapointsLatestQuery = new DatapointsLatestQuery();

		com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag tag = new com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag();
		tag.setName(id);
		List<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag> tags = new ArrayList<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag>();
		tags.add(tag);
		datapointsLatestQuery.setTags(tags);
		return datapointsLatestQuery;
	}
	
	//for fetching multiple tag values
	
	private DatapointsLatestQuery buildLatestDatapointsQueryRequestSolar(ArrayList<String> id) {
		DatapointsLatestQuery datapointsLatestQuery = new DatapointsLatestQuery();
		//ArrayList<String> addtags=new ArrayList<String>();
		com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag tag = new com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag();
		for(String timetag:id)
		{		
		
		tag.setName(timetag);
		
	}
		List<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag> tags = new ArrayList<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag>();
		tags.add(tag);
		datapointsLatestQuery.setTags(tags);
		return datapointsLatestQuery;
	}

	
	
	
	private String processHttpResponseEntity(org.apache.http.HttpEntity entity)
			throws IOException {
		if (entity == null)
			return null;
		if (entity instanceof GzipDecompressingEntity) {
			return IOUtils.toString(
					((GzipDecompressingEntity) entity).getContent(), "UTF-8");
		}
		return EntityUtils.toString(entity);
	}

	@Override
	public DatapointsResponse getTagDataPointsTest(String id, String authorization) {
		List<Header> headers = generateHeaders(authorization);

		DatapointsLatestQuery dpQuery = buildLatestDatapointsQueryRequest(id);
		DatapointsResponse response = this.timeseriesFactory.queryForLatestDatapoint(this.timeseriesRestConfig.getBaseUrl(), dpQuery, headers);
		log.debug(response.toString());

		return response;
	}

	@Override
	public PlantKpiDashboardGraphData getKpiGraphTags(String location, String authorization) {
		
		// current time
		Instant now = Instant.now();
		// in millis
		long toEpochMillisEndTime = now.toEpochMilli();
		// LocalDate/LocalTime <-> LocalDateTime
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.of(00, 00);
		// the current date
		LocalDateTime startLocalDateTime = LocalDateTime.of(date, time);
		Instant startDateTimeInstant = startLocalDateTime.toInstant(ZoneOffset.ofHours(0));
		long toEpochMillisStartTime = startDateTimeInstant.toEpochMilli();
		
		List<Header> headers = generateHeaders(authorization);
		DatapointsQuery datapointsQuery = new DatapointsQuery();
		
		datapointsQuery.setStart(toEpochMillisStartTime);
		datapointsQuery.setEnd(toEpochMillisEndTime);
		
		//log.info("*************************" + toEpochMillisStartTime + "***********************" + toEpochMillisEndTime);
		
		List<Tag> tags = new ArrayList<Tag>();
		
		Tag tagAgSgRatio = new Tag();
		tagAgSgRatio.setOrder("desc");
		tagAgSgRatio.setName("WinCC_OA.ChennaiSite.AG_SG");
		
		Tag tagAgValue = new Tag();
		tagAgValue.setOrder("desc");
		tagAgValue.setName("WinCC_OA.ChennaiSite.INST_AG");
		
		Tag tagSgValue = new Tag();
		tagSgValue.setOrder("desc");
		tagSgValue.setName("WinCC_OA.ChennaiSite.SG.SG");
		
		Tag tagUiValue = new Tag();
		tagUiValue.setOrder("desc");
		tagUiValue.setName("WinCC_OA.ChennaiSite.UI");
		
		tags.add(tagAgSgRatio);
		tags.add(tagAgValue);
		tags.add(tagSgValue);
		tags.add(tagUiValue);
		datapointsQuery.setTags(tags );
		
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag agSgResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag agResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag sgResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag uiResponseTag = null;
		try{
		DatapointsResponse response = this.timeseriesFactory.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(), datapointsQuery, headers);
		
		List<com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag> responseTagsList = response.getTags();
		
		agSgResponseTag = responseTagsList.stream() // Convert to steam
				.filter(t -> tagAgSgRatio.getName().equals(t.getName())) // we want "AgSgRatio" tag only
				.findAny()  // If 'findAny' then return found
				.orElse(null); // If not found, return null
		
		agResponseTag = responseTagsList.stream() // Convert to steam
				.filter(t -> tagAgValue.getName().equals(t.getName())) // we want "SG" tag only
				.findAny()  // If 'findAny' then return found
				.orElse(null); // If not found, return null
		
		sgResponseTag = responseTagsList.stream() // Convert to steam
				.filter(t -> tagSgValue.getName().equals(t.getName())) // we want "AG" tag only
				.findAny()  // If 'findAny' then return found
				.orElse(null); // If not found, return null
		
		uiResponseTag = responseTagsList.stream() // Convert to steam
				.filter(t -> tagUiValue.getName().equals(t.getName())) // we want "UI" tag only
				.findAny()  // If 'findAny' then return found
				.orElse(null); // If not found, return null
		}catch(RuntimeException runTimeExp){
			//log.info("=====================================================" + runTimeExp +"==========================================================");
		}
		
		List<Object> agSgResponseResultValuesList = new ArrayList<>();
		List<Object> agResponseResultValuesList = new ArrayList<>();
		List<Object> sgSgResponseResultValuesList = new ArrayList<>();
		List<Object> uiResponseResultValuesList = new ArrayList<>();
		if(null != agSgResponseTag){
			agSgResponseResultValuesList = agSgResponseTag.getResults().get(0).getValues();
		}
		if(null != agResponseTag){
			agResponseResultValuesList = agResponseTag.getResults().get(0).getValues();
		}
		if(null != sgResponseTag){
			sgSgResponseResultValuesList = sgResponseTag.getResults().get(0).getValues();
		}
		if(null != uiResponseTag){
			uiResponseResultValuesList = uiResponseTag.getResults().get(0).getValues();
		}
		
		PlantKpiDashboardGraphData plantKpiGraphData = new PlantKpiDashboardGraphData();
		plantKpiGraphData.setAgSgRatio(agSgResponseResultValuesList);
		plantKpiGraphData.setAgValue(agResponseResultValuesList);
		plantKpiGraphData.setSgValue(sgSgResponseResultValuesList);
		plantKpiGraphData.setUiValue(uiResponseResultValuesList);
		return plantKpiGraphData;
	}

	@Override
	public PlantKpiDashboardData getKpiTags(String location, String authorization) {
		// TODO Auto-generated method stub
		List<Header> headers = generateHeaders(authorization);
		generateHeaders(authorization);
		
		DatapointsLatestQuery dpQuery = new DatapointsLatestQuery();
		
		List<String> requesttagList = new ArrayList<>();
		requesttagList.add("WinCC_OA.ChennaiSite.AG_DC");
		requesttagList.add("WinCC_OA.ChennaiSite.AG_SG");
		requesttagList.add("WinCC_OA.ChennaiSite.DC");
		requesttagList.add("WinCC_OA.ChennaiSite.SG.SG");
		requesttagList.add("WinCC_OA.ChennaiSite.INST_AG");
		requesttagList.add("WinCC_OA.ChennaiSite.AVG_AG");
		requesttagList.add("WinCC_OA.ChennaiSite.UI");
		requesttagList.add("WinCC_OA.ChennaiSite.UI_RATE");
		requesttagList.add("WinCC_OA.ChennaiSite.NET_GAIN");
		requesttagList.add("WinCC_OA.ChennaiSite.AVG_DEVIATION");
		requesttagList.add("WinCC_OA.ChennaiSite.INST_DEVIATION");
		
		List<com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag> tags = new ArrayList<>();
		
		for(String requestTagName : requesttagList){
			com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag requestTag = new com.ge.predix.entity.timeseries.datapoints.queryrequest.latest.Tag();
			requestTag.setName(requestTagName);
			tags.add(requestTag);
		}
		
		dpQuery.setTags(tags );
		
		DatapointsResponse latestDataPointResponse = this.timeseriesFactory.queryForLatestDatapoint(this.timeseriesRestConfig.getBaseUrl(), dpQuery, headers);
		
		/*//log.info(" ********************************************* "+ latestDataPointResponse.getTags().size() + "****************************************");*/ 
		
		List<com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag> latestDataList = latestDataPointResponse.getTags();
		Map<String,String> responseMap = new HashMap<>();
		for(String responseTagName : requesttagList){
			com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag tagValues = latestDataList.stream() // Convert to steam
					.filter(t -> responseTagName.equals(t.getName())) // we want "AgSgRatio" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			String responseString = "";
			String responseValue = "";
			if(null != tagValues && tagValues.getResults().size() > 0 && tagValues.getResults().get(0).getValues().size() > 0){
				responseString = tagValues.getResults().get(0).getValues().get(0).toString();
				String[] valueArray = responseString.split(",");
				responseValue = valueArray[1];
			}
			log.info("*****" + responseTagName + "_______" + responseValue);
			responseMap.put(responseTagName, roundUpValue(responseValue));
		}
		
		
		// current time
		Instant now = Instant.now();
		
		// Changes
		//ZonedDateTime pstDateTime = ZonedDateTime.of(LocalDateTime.now().minusDays(1L), ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
		
		// in millis
		long toEpochMillisEndTime = now.toEpochMilli(); // CHANGES
		
		// LocalDate/LocalTime <-> LocalDateTime
		//LocalDate date = LocalDate.now(); CHANGES
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.of(00, 00);
		// the current date
		LocalDateTime startLocalDateTime = LocalDateTime.of(date, time);

		Instant startDateTimeInstant = startLocalDateTime.toInstant(ZoneOffset.ofHours(0));
		long toEpochMillisStartTime = startDateTimeInstant.toEpochMilli(); 
		//long toEpochMillisStartTime = toEpochMillisEndTime-38800000;// CHANGES
		
		long blockStartTime = startDateTimeInstant.toEpochMilli();
		
		List<Long> timeBlockList = new ArrayList<>();
		for(int i = 0; i < (24*4) ; i++){
			timeBlockList.add(blockStartTime);
			blockStartTime = blockStartTime + 900000;
		}
		
		DatapointsQuery datapointsQuery = new DatapointsQuery();
		//datapointsQuery.setStart(toEpochMillisStartTime);
		//log.info("----------start time -----------------" + (toEpochMillisStartTime) + "-------------------------------------------");
		//log.info("----------end time -----------------" + (toEpochMillisEndTime) + "-------------------------------------------");
		datapointsQuery.setStart(toEpochMillisStartTime); // CHANGES
		datapointsQuery.setEnd(toEpochMillisEndTime); // CHANGES
		List<Tag> tagsList = new ArrayList<Tag>();
		
		Tag tagAgSgRatio = new Tag();
		tagAgSgRatio.setOrder("desc");
		tagAgSgRatio.setName("WinCC_OA.ChennaiSite.AG_SG");
		
		tagsList.add(tagAgSgRatio);
		datapointsQuery.setTags(tagsList);
		
		DatapointsResponse response = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag agSgResponseTag = null;
		try{
			response = this.timeseriesFactory.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(), datapointsQuery, headers);
			
			/*//log.info("####### Response ######" + response + "##############");*/
			
			List<com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag> responseTagsList = response.getTags();
			
			agSgResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> tagAgSgRatio.getName().equals(t.getName())) // we want "AgSgRatio" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			//log.info("###### Tags Values #######" + agSgResponseTag + "#############");
		}catch(RuntimeException exp){
			//log.info("--------------------------------------- RUNTIME EXPECTION ------------------------------------------------------");
		}
		
		
		List<Object> agSgResponseResultValuesList = new ArrayList<>();
		if(null != agSgResponseTag){
			agSgResponseResultValuesList = agSgResponseTag.getResults().get(0).getValues();
		}
		
		Map<LocalDateTime,Double> timeValueList = new HashMap<>(); 
		
		//Output
		if (agSgResponseResultValuesList.size() > 0) {
			agSgResponseResultValuesList.forEach(itm -> {
				String responseString = itm.toString().replace("[", "").replace("]", "");
				String[] responseOutput = responseString.split(",");
				Instant fromEpochMilli = Instant.ofEpochMilli(Long.valueOf(responseOutput[0]));
				LocalDateTime mylocalDateTime = LocalDateTime.ofEpochSecond(fromEpochMilli.getEpochSecond(),0,ZoneOffset.UTC);
				int extraSeconds = mylocalDateTime.getSecond();
				mylocalDateTime = mylocalDateTime.minusSeconds(extraSeconds);
				timeValueList.put(mylocalDateTime, Double.valueOf(responseOutput[1]));
			});
		}
		
		List<Long> blocksTimeList = lastFiveBlocksTimestampList(toEpochMillisEndTime,timeBlockList);
		PlantKpiDashboardData plantKpiData = new PlantKpiDashboardData();
		List<PlantKpiDashboardValues> frequencyBlockList = new ArrayList<>();
		
		frequencyBlockList.add(new PlantKpiDashboardValues("Inst. Deviation", responseMap.get("WinCC_OA.ChennaiSite.INST_DEVIATION")+"%")); 
		frequencyBlockList.add(new PlantKpiDashboardValues("Avg. Deviation", responseMap.get("WinCC_OA.ChennaiSite.AVG_DEVIATION")+"%")); 
		plantKpiData.setFrequencyBlock(frequencyBlockList);
		
		List<PlantKpiDashboardValues> ratioBlockList = new ArrayList<>();
		ratioBlockList.add(new PlantKpiDashboardValues("AG/DC%", responseMap.get("WinCC_OA.ChennaiSite.AG_DC")+"%"));
		ratioBlockList.add(new PlantKpiDashboardValues("AG/SG%", responseMap.get("WinCC_OA.ChennaiSite.AG_SG")+"%"));
		plantKpiData.setRatioBlock(ratioBlockList);
		
		List<PlantKpiDashboardValues> plantCurrentBlockDataList = new ArrayList<>();
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Declared Capacity (kW)", responseMap.get("WinCC_OA.ChennaiSite.DC")));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Scheduled Generation (kW)", responseMap.get("WinCC_OA.ChennaiSite.SG.SG")));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Corrected Scheduled Generation (kW)", responseMap.get("WinCC_OA.ChennaiSite.SG.SG"))); // SG and CSG are same for now
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Inst. Actual Generation (kW)", responseMap.get("WinCC_OA.ChennaiSite.INST_AG")));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Avg. Actual Generation (kW)", responseMap.get("WinCC_OA.ChennaiSite.AVG_AG")));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Unscheduled Interchange (kW)", responseMap.get("WinCC_OA.ChennaiSite.UI")));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Unscheduled Interchange Rate(Rs)", responseMap.get("WinCC_OA.ChennaiSite.UI_RATE")));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("NET GAIN(Rs)", responseMap.get("WinCC_OA.ChennaiSite.NET_GAIN")));
		plantKpiData.setCurrentBlock(plantCurrentBlockDataList);
		
		List<PlantKpiDashboardValues> historyBlockList = new ArrayList<>();
		
		//log.info("******** blocksTimeList ******" + blocksTimeList );
		
		int blockCounter = 1;
		for(Long btl : blocksTimeList){
			Instant fromEpochMilliSconds = Instant.ofEpochMilli(btl);
			LocalDateTime mylocalDateTime2 = LocalDateTime.ofEpochSecond(fromEpochMilliSconds.getEpochSecond(),0,ZoneOffset.UTC);
			int extraSeconds2 = mylocalDateTime2.getSecond();
			mylocalDateTime2 = mylocalDateTime2.minusSeconds(extraSeconds2);
			if(timeValueList.get(mylocalDateTime2) != null){
				//log.info("----------------------" + mylocalDateTime2 + "----------------------------");
				historyBlockList.add(new PlantKpiDashboardValues("BLOCK "+blockCounter, roundUpValue(timeValueList.get(mylocalDateTime2))+"%"));
				blockCounter++;
			}
		};
		
		plantKpiData.setHistoryBlock(historyBlockList);
		return plantKpiData;
	}
	
	@Override
	public PlantKpiDashboardData getPstKpiTags(String location, String authorization) {
		// TODO Auto-generated method stub
		List<Header> headers = generateHeaders(authorization);
		
		DatapointsQuery datapointsQuery1 = new DatapointsQuery();
		// Changes
		/*ZonedDateTime pstDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ZoneId.SHORT_IDS.get("PST")));*/
		Instant now = Instant.now();
		
		// in millis
		long toEpochMillisEndTime = now.toEpochMilli() - 28800000;
		datapointsQuery1.setStart(toEpochMillisEndTime - 1200000);
		datapointsQuery1.setEnd(toEpochMillisEndTime);
		
		//log.info("--------------- end time -------------" + toEpochMillisEndTime);
		
		List<Tag> tags = new ArrayList<Tag>();
		
		Tag agDcRatiotag = new Tag();
		agDcRatiotag.setOrder("desc");
		agDcRatiotag.setName("WinCC_OA.ChennaiSite.AG_DC");
		
		Tag agSgRatiotag = new Tag();
		agSgRatiotag.setOrder("desc");
		agSgRatiotag.setName("WinCC_OA.ChennaiSite.AG_SG");
		
		Tag dcCurrenttag = new Tag();
		dcCurrenttag.setOrder("desc");
		dcCurrenttag.setName("WinCC_OA.ChennaiSite.DC");
		
		Tag sgCurrenttag = new Tag();
		sgCurrenttag.setOrder("desc");
		sgCurrenttag.setName("WinCC_OA.ChennaiSite.SG.SG");
		
		Tag instAGTag = new Tag();
		instAGTag.setOrder("desc");
		instAGTag.setName("WinCC_OA.ChennaiSite.INST_AG");
		
		Tag avgAGTag = new Tag();
		avgAGTag.setOrder("desc");
		avgAGTag.setName("WinCC_OA.ChennaiSite.AVG_AG");
		
		Tag uiTag = new Tag();
		uiTag.setOrder("desc");
		uiTag.setName("WinCC_OA.ChennaiSite.UI");
		
		Tag uiRateTag = new Tag();
		uiRateTag.setOrder("desc");
		uiRateTag.setName("WinCC_OA.ChennaiSite.UI_RATE");
		
		Tag netGainTag = new Tag();
		netGainTag.setOrder("desc");
		netGainTag.setName("WinCC_OA.ChennaiSite.NET_GAIN");
		
		Tag avgDeviationTag = new Tag();
		avgDeviationTag.setOrder("desc");
		avgDeviationTag.setName("WinCC_OA.ChennaiSite.AVG_DEVIATION");
		
		Tag instDeviationTag = new Tag();
		instDeviationTag.setOrder("desc");
		instDeviationTag.setName("WinCC_OA.ChennaiSite.INST_DEVIATION");
		
		tags.add(agDcRatiotag);
		tags.add(agSgRatiotag);
		tags.add(dcCurrenttag);
		tags.add(sgCurrenttag);
		tags.add(instAGTag);
		tags.add(avgAGTag);
		tags.add(uiTag);
		tags.add(uiRateTag);
		tags.add(netGainTag);
		tags.add(avgDeviationTag);
		tags.add(instDeviationTag);
		datapointsQuery1.setTags(tags);
		
		
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag agDcResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag agSgResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag dcResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag sgResponseTag = null;
		
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag instAGResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag avgAGResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag uiResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag uiRateResponseTag = null;
		
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag netGainResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag avgDeviationResponseTag = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag instDeviationResponseTag = null;
		
		
		try{
			DatapointsResponse response = this.timeseriesFactory.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(), datapointsQuery1, headers);
			
			List<com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag> responseTagsList = response.getTags();
			
			agDcResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> agDcRatiotag.getName().equals(t.getName())) // we want "AgSgRatio" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			agSgResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> agSgRatiotag.getName().equals(t.getName())) // we want "SG" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			dcResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> dcCurrenttag.getName().equals(t.getName())) // we want "AG" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			sgResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> sgCurrenttag.getName().equals(t.getName())) // we want "UI" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			instAGResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> instAGTag.getName().equals(t.getName())) // we want "AgSgRatio" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			avgAGResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> avgAGTag.getName().equals(t.getName())) // we want "SG" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			uiResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> uiTag.getName().equals(t.getName())) // we want "AG" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			uiRateResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> uiRateTag.getName().equals(t.getName())) // we want "UI" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			netGainResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> netGainTag.getName().equals(t.getName())) // we want "AgSgRatio" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			avgDeviationResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> avgDeviationTag.getName().equals(t.getName())) // we want "SG" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			instDeviationResponseTag = responseTagsList.stream() // Convert to steam
					.filter(t -> instDeviationTag.getName().equals(t.getName())) // we want "AG" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			
			
			}catch(RuntimeException runTimeExp){
				//log.info("=====================================================" + runTimeExp +"==========================================================");
			}
		
		String agDcResponseTagResult = "";
		String agSgResponseTagResult = "";
		String dcResponseTagResult = "";
		String sgResponseTagResult = "";
		String instAGResponseTagResult = "";
		String avgAGResponseTagResult = "";
		String uiResponseTagResult = "";
		String uiRateResponseTagResult = "";
		String netGainResponseTagResult = "";
		String avgDeviationResponseTagResult = "";
		String instDeviationResponseTagResult = "";
		
		String agDcRatio = "0";
		String agSgRatio = "0";
		String dcCurrent = "0";
		String sgCurrent = "0";
		String instAG = "0";
		String avgAG = "0";
		String ui = "0";
		String uiRate = "0";
		String netGain = "0";
		String avgDeviation = "0";
		String instDeviation = "0";
		
		if(null != agDcResponseTag && agDcResponseTag.getResults().size() > 0 && agDcResponseTag.getResults().get(0).getValues().size() > 0){
			agDcResponseTagResult = agDcResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = agDcResponseTagResult.split(",");
			agDcRatio = agDcRatioArray[1];
		}
		if(null != agSgResponseTag && agSgResponseTag.getResults().size() > 0 && agSgResponseTag.getResults().get(0).getValues().size() > 0){
			agSgResponseTagResult = agSgResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = agSgResponseTagResult.split(",");
			agSgRatio = agDcRatioArray[1];
		}
		if(null != dcResponseTag && dcResponseTag.getResults().size() > 0 && dcResponseTag.getResults().get(0).getValues().size() > 0){
			dcResponseTagResult = dcResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = dcResponseTagResult.split(",");
			dcCurrent = agDcRatioArray[1];
		}
		if(null != sgResponseTag && sgResponseTag.getResults().size() > 0 && sgResponseTag.getResults().get(0).getValues().size() > 0){
			sgResponseTagResult = sgResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = sgResponseTagResult.split(",");
			sgCurrent = agDcRatioArray[1];
		}
		if(null != instAGResponseTag && instAGResponseTag.getResults().size() > 0 && instAGResponseTag.getResults().get(0).getValues().size() > 0){
			instAGResponseTagResult = instAGResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = instAGResponseTagResult.split(",");
			instAG = agDcRatioArray[1];
		}
		if(null != avgAGResponseTag && avgAGResponseTag.getResults().size() > 0  && avgAGResponseTag.getResults().get(0).getValues().size() > 0){
			avgAGResponseTagResult = avgAGResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = avgAGResponseTagResult.split(",");
			avgAG = agDcRatioArray[1];
		}
		if(null != uiResponseTag && uiResponseTag.getResults().size() > 0 && uiResponseTag.getResults().get(0).getValues().size() > 0){
			uiResponseTagResult = uiResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = uiResponseTagResult.split(",");
			ui = agDcRatioArray[1];
		}
		if(null != uiRateResponseTag && uiRateResponseTag.getResults().size() > 0 && uiRateResponseTag.getResults().get(0).getValues().size() > 0){
			uiRateResponseTagResult = uiRateResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = uiRateResponseTagResult.split(",");
			uiRate = agDcRatioArray[1];
		}
		if(null != netGainResponseTag && netGainResponseTag.getResults().size() > 0 && netGainResponseTag.getResults().get(0).getValues().size() > 0){
			netGainResponseTagResult = netGainResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = netGainResponseTagResult.split(",");
			netGain = agDcRatioArray[1];
		}
		if(null != avgDeviationResponseTag && avgDeviationResponseTag.getResults().size() > 0 && avgDeviationResponseTag.getResults().get(0).getValues().size() > 0){
			avgDeviationResponseTagResult = avgDeviationResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = avgDeviationResponseTagResult.split(",");
			avgDeviation = agDcRatioArray[1];
		}
		if(null != instDeviationResponseTag && instDeviationResponseTag.getResults().size() > 0 && instDeviationResponseTag.getResults().get(0).getValues().size() > 0){
			instDeviationResponseTagResult = instDeviationResponseTag.getResults().get(0).getValues().get(0).toString();
			String[] agDcRatioArray = instDeviationResponseTagResult.split(",");
			instDeviation = agDcRatioArray[1];
		}
		
		// LocalDate/LocalTime <-> LocalDateTime
		//LocalDate date = LocalDate.now(); CHANGES
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.of(00, 00);
		// the current date
		LocalDateTime startLocalDateTime = LocalDateTime.of(date, time);

		Instant startDateTimeInstant = startLocalDateTime.toInstant(ZoneOffset.ofHours(0));
		//long toEpochMillisStartTime = startDateTimeInstant.toEpochMilli(); 
		//long toEpochMillisStartTime = toEpochMillisEndTime-38800000;// CHANGES
		
		long blockStartTime = startDateTimeInstant.toEpochMilli();
		
		List<Long> timeBlockList = new ArrayList<>();
		for(int i = 0; i < (24*4) ; i++){
			timeBlockList.add(blockStartTime);
			blockStartTime = blockStartTime + 900000;
		}
		
		DatapointsQuery datapointsQuery = new DatapointsQuery();
		//datapointsQuery.setStart(toEpochMillisStartTime);
		//log.info("----------start time -----------------" + (toEpochMillisStartTime) + "-------------------------------------------");
		//log.info("----------end time -----------------" + (toEpochMillisEndTime) + "-------------------------------------------");
		datapointsQuery.setStart(toEpochMillisEndTime-128800000); // CHANGES
		datapointsQuery.setEnd(toEpochMillisEndTime); // CHANGES
		List<Tag> tagsList = new ArrayList<Tag>();
		
		Tag tagAgSgRatio = new Tag();
		tagAgSgRatio.setOrder("desc");
		tagAgSgRatio.setName("WinCC_OA.ChennaiSite.AG_SG");
		
		tagsList.add(tagAgSgRatio);
		datapointsQuery.setTags(tagsList);
		
		DatapointsResponse response = null;
		com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag agSgResponseTag1 = null;
		try{
			response = this.timeseriesFactory.queryForDatapoints(this.timeseriesRestConfig.getBaseUrl(), datapointsQuery, headers);
			
			/*//log.info("####### Response ######" + response + "##############");*/
			
			List<com.ge.predix.entity.timeseries.datapoints.queryresponse.Tag> responseTagsList = response.getTags();
			
			agSgResponseTag1 = responseTagsList.stream() // Convert to steam
					.filter(t -> tagAgSgRatio.getName().equals(t.getName())) // we want "AgSgRatio" tag only
					.findAny()  // If 'findAny' then return found
					.orElse(null); // If not found, return null
			
			//log.info("###### Tags Values #######" + agSgResponseTag1 + "#############");
		}catch(RuntimeException exp){
			//log.info("--------------------------------------- RUNTIME EXPECTION ------------------------------------------------------");
		}
		
		
		List<Object> agSgResponseResultValuesList = new ArrayList<>();
		if(null != agSgResponseTag1){
			agSgResponseResultValuesList = agSgResponseTag1.getResults().get(0).getValues();
		}
		
		Map<LocalDateTime,Double> timeValueList = new HashMap<>(); 
		
		//Output
		if (agSgResponseResultValuesList.size() > 0) {
			agSgResponseResultValuesList.forEach(itm -> {
				String responseString = itm.toString().replace("[", "").replace("]", "");
				String[] responseOutput = responseString.split(",");
				Instant fromEpochMilli = Instant.ofEpochMilli(Long.valueOf(responseOutput[0]));
				LocalDateTime mylocalDateTime = LocalDateTime.ofEpochSecond(fromEpochMilli.getEpochSecond(),0,ZoneOffset.UTC);
				int extraSeconds = mylocalDateTime.getSecond();
				mylocalDateTime = mylocalDateTime.minusSeconds(extraSeconds);
				timeValueList.put(mylocalDateTime, Double.valueOf(responseOutput[1]));
			});
		}
		
		List<Long> blocksTimeList = lastFiveBlocksTimestampList(toEpochMillisEndTime,timeBlockList);
		
		PlantKpiDashboardData plantKpiData = new PlantKpiDashboardData();
		
		List<PlantKpiDashboardValues> frequencyBlockList = new ArrayList<>();
		
		//log.info("-------------------------------------------------------------------");
		//log.info("-----------------------"+instDeviation);
		//log.info("-----------------------"+avgDeviation);
		//log.info("-----------------------"+agDcRatio);
		//log.info("-----------------------"+agSgRatio);
		//log.info("-----------------------"+dcCurrent);
		//log.info("-----------------------"+sgCurrent);
		//log.info("-----------------------"+instAG);
		//log.info("-----------------------"+avgAG);
		//log.info("-----------------------"+ui);
		//log.info("-----------------------"+uiRate);
		//log.info("-----------------------"+netGain);
		//log.info("-------------------------------------------------------------------");
		
		frequencyBlockList.add(new PlantKpiDashboardValues("Inst. Deviation", roundUpValue(instDeviation)+"%")); 
		frequencyBlockList.add(new PlantKpiDashboardValues("Avg. Deviation", roundUpValue(avgDeviation)+"%")); 
		plantKpiData.setFrequencyBlock(frequencyBlockList);
		
		List<PlantKpiDashboardValues> ratioBlockList = new ArrayList<>();
		ratioBlockList.add(new PlantKpiDashboardValues("AG/DC%", roundUpValue(agDcRatio)+"%"));
		ratioBlockList.add(new PlantKpiDashboardValues("AG/SG%", roundUpValue(agSgRatio)+"%"));
		plantKpiData.setRatioBlock(ratioBlockList);
		
		List<PlantKpiDashboardValues> plantCurrentBlockDataList = new ArrayList<>();
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Declared Capacity (kW)", roundUpValue(dcCurrent)));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Scheduled Generation (kW)", roundUpValue(sgCurrent)));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Corrected Scheduled Generation (kW)", roundUpValue(sgCurrent)));  // Static Content
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Inst. Actual Generation (kW)", roundUpValue(instAG)));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Avg. Actual Generation (kW)", roundUpValue(avgAG)));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Unscheduled Interchange (kW)", roundUpValue(ui)));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Unscheduled Interchange Rate(Rs)", roundUpValue(uiRate)));
		plantCurrentBlockDataList.add(new PlantKpiDashboardValues("Net Gain(Rs)", roundUpValue(netGain)));
		plantKpiData.setCurrentBlock(plantCurrentBlockDataList);
		
		List<PlantKpiDashboardValues> historyBlockList = new ArrayList<>();
		
		//log.info("******** blocksTimeList ******" + blocksTimeList );
		
		int blockCounter = 1;
		for(Long btl : blocksTimeList){
			Instant fromEpochMilliSconds = Instant.ofEpochMilli(btl);
			LocalDateTime mylocalDateTime2 = LocalDateTime.ofEpochSecond(fromEpochMilliSconds.getEpochSecond(),0,ZoneOffset.UTC);
			int extraSeconds2 = mylocalDateTime2.getSecond();
			mylocalDateTime2 = mylocalDateTime2.minusSeconds(extraSeconds2);
			if(timeValueList.get(mylocalDateTime2) != null){
				//log.info("----------------------" + mylocalDateTime2 + "----------------------------");
				historyBlockList.add(new PlantKpiDashboardValues("BLOCK "+blockCounter, roundUpValue(timeValueList.get(mylocalDateTime2))+"%"));
				blockCounter++;
			}
		};
		
		plantKpiData.setHistoryBlock(historyBlockList);
		return plantKpiData;
	}

	
	public static List<Long> lastFiveBlocksTimestampList(long of, List<Long> in) {
		long min = Long.MAX_VALUE;
		long closest = of;
		long diff = 0;

		for (long v : in) {
			diff = of - v;
			if (diff > 0) {
				if (diff < min) {
					min = diff;
					closest = v;
				}
			}
		}
		List<Long> lastFiveBlocksTimestamps = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			lastFiveBlocksTimestamps.add(closest);
			closest = closest - 900000;
		}

		return lastFiveBlocksTimestamps;
	}
	
	public static String roundUpValue(Object object){
		String roundUpValue = "";
		String stringValueOf = object.toString();
		if(!(stringValueOf.isEmpty() && stringValueOf.equals(""))){
			roundUpValue = (int)Math.round(Double.parseDouble(stringValueOf))+"";
		}else{
			roundUpValue = "0";
		}
		return roundUpValue;
	}
	
	public static String roundUpValue(String value){
		String roundUpValue = "";
		String stringValueOf = value;
		if(!(stringValueOf.isEmpty() && stringValueOf.equals(""))){
			roundUpValue = (int)Math.round(Double.parseDouble(stringValueOf))+"";
		}else{
			roundUpValue = "0";
		}
		return roundUpValue;
	}
		
}
