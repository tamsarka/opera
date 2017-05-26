/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.schedulers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.ge.predix.solsvc.restclient.impl.RestClient;

/**
 * @author 10620506
 *
 */
@Component
public class ScheduledTasks {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	@Autowired
	private RestClient restClient;
	
	@Value("${spring.profiles.active}")
	String profile;
	
	/*private static final String kpiOrchId = "86549721-9e76-4f4a-bdae-9f2f28f9fba9";
	private static final String aprOprOrchId = "e54f2ea2-d2f3-48f8-b8a2-d5541899fc35";
	private static final String actualExpectedAprOrchId = "8876295c-00bf-4b76-a3dc-2bfa559a49e5";
	private static final String actualExpectedAprOrchIdDec = "c20b69a5-13b2-41dd-8e41-e2eea0f97786";
	private static final String analyticsExecutionUrl ="https://predix-analytics-execution-release.run.aws-usw02-pr.ice.predix.io/api/v2/execution";
	private static final String analyticsExecutionZoneId ="52826a21-9c97-47cb-83eb-bec26aa636ca";*/
	
	@Value("${kpiOrchId}")
	String kpiOrchId;
	
	@Value("${aprOprOrchId}")
	String aprOprOrchId;

	@Value("${actualExpectedAprOrchId}")
	String actualExpectedAprOrchId;

	@Value("${actualExpectedAprOrchIdDec}")
	String actualExpectedAprOrchIdDec;

	@Value("${analyticsExecutionUrl}")
	String analyticsExecutionUrl;

	@Value("${analyticsExecutionZoneId}")
	private String analyticsExecutionZoneId;

    //@Scheduled(cron = "0 0/1 * * * *")
    public void runKPIAnalyticsOrchestrationTime() {
        log.info("####### The time is now : " +  LocalDateTime.now() +"====="+profile+"===="+ kpiOrchId);
        List<Header> headers = this.restClient.getSecureTokenForClientId();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("Predix-Zone-Id", analyticsExecutionZoneId));
        System.out.println(headers);
        
        CloseableHttpResponse httpResponse = null;
		String entity = null;
		try {
			String inputJson = "{\"orchestrationConfigurationId\": \""+kpiOrchId+"\","
									+ "\"assetDataFieldsMap\": {  \"power\": \"WinCC_OA.ChennaiSite.EM.Power\","
									+ "\"sg\": \"WinCC_OA.ChennaiSite.SG.SG\","
									+ "\"inst_ag\" : \"WinCC_OA.ChennaiSite.INST_AG\","
									+ "\"avg_ag\" : \"WinCC_OA.ChennaiSite.AVG_AG\","
									+ "\"ui\" : \"WinCC_OA.ChennaiSite.UI\",\"net_gain\" : \"WinCC_OA.ChennaiSite.NET_GAIN\","
									+ "\"ag_dc_ratio\" : \"WinCC_OA.ChennaiSite.AG_DC\","
									+ "\"ag_sg_ratio\" : \"WinCC_OA.ChennaiSite.AG_SG\","
									+ "\"avg_deviation\" : \"WinCC_OA.ChennaiSite.AVG_DEVIATION\","
									+ "\"inst_deviation\" : \"WinCC_OA.ChennaiSite.INST_DEVIATION\","
									+ "\"ui_rate\" : \"WinCC_OA.ChennaiSite.UI_RATE\","
									+ "\"dc\" : \"WinCC_OA.ChennaiSite.DC\"}}";
			httpResponse = this.restClient.post(analyticsExecutionUrl, inputJson, headers, 999999999, 99999999);
			
			if (httpResponse.getEntity() != null) {
				try {
					entity = processHttpResponseEntity(httpResponse.getEntity());
					//log.debug("HttpEntity returned from Tags" + httpResponse.getAllHeaders().toString()); //$NON-NLS-1$
					log.info("output : " + entity);
					/*Header[] responseHeaders = httpResponse.getAllHeaders();
					for (Header header : responseHeaders) {
						System.out.println("Response headers : " + header.getName() + " : " + header.getValue());
					}*/
					
				} catch (IOException e) {
					throw new RuntimeException(
							"Error occured calling= "+analyticsExecutionUrl, e); //$NON-NLS-1$//$NON-NLS-2$
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
    }
    
    //@Scheduled(cron = "25 0/15 * * * *")
    public void runAprOprAnalyticsOrchestrationTime() {
        log.info("The time is now : ", LocalDateTime.now());
        List<Header> headers = this.restClient.getSecureTokenForClientId();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("Predix-Zone-Id", analyticsExecutionZoneId));
        System.out.println(headers);
        
        CloseableHttpResponse httpResponse = null;
		String entity = null;
		try {
			String inputJson = "{\"orchestrationConfigurationId\": \""+aprOprOrchId+"\","
								+ "\"assetDataFieldsMap\": {\"energy\": \"WinCC_OA.ChennaiSite.EM.Energy\","
								+ "\"insolation\": \"WinCC_OA.ChennaiSite.WSTN.Irradiance\","
								+ "\"irradianceTempProduct\": \"WinCC_OA.ChennaiSite.WSTN.AmbientTemp\","
								+ "\"apr\": \"WinCC_OA.ChennaiSite.APR\","
								+ "\"opr\": \"WinCC_OA.ChennaiSite.OPR\"}}";
			httpResponse = this.restClient.post(analyticsExecutionUrl, inputJson, headers, 99999999, 99999999);
			
			if (httpResponse.getEntity() != null) {
				try {
					entity = processHttpResponseEntity(httpResponse.getEntity());
					//log.debug("HttpEntity returned from Tags" + httpResponse.getAllHeaders().toString()); //$NON-NLS-1$
					log.info("output : " + entity);
					/*Header[] responseHeaders = httpResponse.getAllHeaders();
					for (Header header : responseHeaders) {
						System.out.println("Response headers : " + header.getName() + " : " + header.getValue());
					}*/
					
				} catch (IOException e) {
					throw new RuntimeException(
							"Error occured calling= "+analyticsExecutionUrl, e); //$NON-NLS-1$//$NON-NLS-2$
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
    }
    
    //@Scheduled(cron = "12 0/1 * * * *")
    public void runActualExpectedAprAnalyticsOrchestrationTime() {
        log.info("The time is now : ", LocalDateTime.now());
        List<Header> headers = this.restClient.getSecureTokenForClientId();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("Predix-Zone-Id", analyticsExecutionZoneId));
        System.out.println(headers);
        
        CloseableHttpResponse httpResponse = null;
		String entity = null;
		try {
			String inputJson = "{\"orchestrationConfigurationId\": \""+actualExpectedAprOrchId+"\","
								+ "\"assetDataFieldsMap\": {\"energy\": \"WinCC_OA.ChennaiSite.EM.Energy\","
								+ "\"insolation\": \"WinCC_OA.ChennaiSite.WSTN.Irradiance\","
								+ "\"irradianceTempProduct\": \"WinCC_OA.ChennaiSite.WSTN.AmbientTemp\","
								+ "\"actualEnergy\": \"WinCC_OA.ChennaiSite.ActualEnergy\","
								+ "\"expectedEnergy\": \"WinCC_OA.ChennaiSite.ExpectedEnergy\","
								+ "\"actualPerformanceRatio\": \"WinCC_OA.ChennaiSite.ActualPR\"}}";
			httpResponse = this.restClient.post(analyticsExecutionUrl, inputJson, headers, 99999, 99999);
			
			if (httpResponse.getEntity() != null) {
				try {
					entity = processHttpResponseEntity(httpResponse.getEntity());
					//log.debug("HttpEntity returned from Tags" + httpResponse.getAllHeaders().toString()); //$NON-NLS-1$
					log.info("output : " + entity);
					/*Header[] responseHeaders = httpResponse.getAllHeaders();
					for (Header header : responseHeaders) {
						System.out.println("Response headers : " + header.getName() + " : " + header.getValue());
					}*/
					
				} catch (IOException e) {
					throw new RuntimeException(
							"Error occured calling= "+analyticsExecutionUrl, e); //$NON-NLS-1$//$NON-NLS-2$
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
    }
    
    //@Scheduled(cron = "40 0/1 * * * *")
    public void runActualExpectedAprAnalyticsOrchestrationTimeDec() {
        log.info("The time is now : ", LocalDateTime.now());
        List<Header> headers = this.restClient.getSecureTokenForClientId();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("Predix-Zone-Id", analyticsExecutionZoneId));
        System.out.println(headers);
        
        CloseableHttpResponse httpResponse = null;
		String entity = null;
		try {
			String inputJson = "{\"orchestrationConfigurationId\": \""+actualExpectedAprOrchIdDec+"\","
								+ "\"assetDataFieldsMap\": {\"energy\": \"WinCC_OA.ChennaiSite.EM.Energy\","
								+ "\"insolation\": \"WinCC_OA.ChennaiSite.WSTN.Irradiance\","
								+ "\"irradianceTempProduct\": \"WinCC_OA.ChennaiSite.WSTN.AmbientTemp\","
								+ "\"actualEnergy\": \"WinCC_OA.ChennaiSite.ActualEnergy.DEC\","
								+ "\"expectedEnergy\": \"WinCC_OA.ChennaiSite.ExpectedEnergy.DEC\","
								+ "\"actualPerformanceRatio\": \"WinCC_OA.ChennaiSite.ActualPR.DEC\"}}";
			httpResponse = this.restClient.post(analyticsExecutionUrl, inputJson, headers, 99999, 99999);
			
			if (httpResponse.getEntity() != null) {
				try {
					entity = processHttpResponseEntity(httpResponse.getEntity());
					//log.debug("HttpEntity returned from Tags" + httpResponse.getAllHeaders().toString()); //$NON-NLS-1$
					log.info("output : " + entity);
					/*Header[] responseHeaders = httpResponse.getAllHeaders();
					for (Header header : responseHeaders) {
						System.out.println("Response headers : " + header.getName() + " : " + header.getValue());
					}*/
					
				} catch (IOException e) {
					throw new RuntimeException(
							"Error occured calling= "+analyticsExecutionUrl, e); //$NON-NLS-1$//$NON-NLS-2$
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

}
