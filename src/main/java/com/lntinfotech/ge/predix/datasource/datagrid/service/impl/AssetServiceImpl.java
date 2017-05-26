package com.lntinfotech.ge.predix.datasource.datagrid.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;*/
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;

/*import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;*/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.solsvc.bootstrap.ams.dto.Group;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SolarAsset;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SolarClassification;
import com.lntinfotech.ge.predix.datasource.datagrid.service.AssetServiceApi;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.BaseKpiDataGrid;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.GroupNode;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.GroupNodeList;
import com.lntinfotech.ge.predix.datasource.handlers.DataSourceHandler;

@Service(value = "assetService")
public class AssetServiceImpl extends DataSourceHandler implements AssetServiceApi {

	private static Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);
	private ObjectMapper mapper = new ObjectMapper();

	@Value("${accessTokenEndpointUrl}")
	String accessTokenEndpointUrl;

	@Value("${clientId}")
	String clientId;

	@Value("${clientSecret}")
	String clientSecret;

	/**
	 * @return the accessTokenEndpointUrl
	 */
	public String getAccessTokenEndpointUrl() {
		return accessTokenEndpointUrl;
	}

	/**
	 * @param accessTokenEndpointUrl
	 *            the accessTokenEndpointUrl to set
	 */
	public void setAccessTokenEndpointUrl(String accessTokenEndpointUrl) {
		this.accessTokenEndpointUrl = accessTokenEndpointUrl;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret
	 *            the clientSecret to set
	 */
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Object getCacheAsset(String authorization, String filter) throws Throwable {
		log.debug("Get Asset=====================Enter into Authorization=======================" + authorization);
		String assetIdentifier = "/asset/?filter=" + filter; //$NON-NLS-1$
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName(); // get logged in username
		System.out.println("authentication username1 " + username);

		//OAuth2RestTemplate restTemplate = getRestTemplate(username);
		try {
			if (this.getAssetMap().get(assetIdentifier) == null) {
				List<Header> headers = null;
				if (authorization == null) {

					headers = this.restClient.getSecureTokenForClientId();
					this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
				} else {
					headers = new ArrayList<Header>();
					this.restClient.addSecureTokenToHeaders(headers, authorization);

					this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
					log.info("going into else when authorization From UI " + authorization);
					// log.debug("going intoelse when authorization from UI
					// "+authorization);
				}

				byte[] passwordDecoded = Base64.decodeBase64(authorization.getBytes());
				System.out.println("username password from auth" + passwordDecoded);

				// User user =
				// (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				// String name = user.getUsername();
				// System.out.println("user details:"+user);
				// System.out.println("username"+name);

				// MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				// String latestUrl =
				// this.context.getRequestURL().toString().replace("/solar/acs",
				// "/validateuser");
				// String latestUrl = this.context.getRequestURL().toString();
				// log.info("XXXCalling LATEST URL::::::::: " + latestUrl);
				String token = null;
				// 3. Once the policy is evaluated to PERMIT , then the call is
				// passed
				// forward to get the token based on client credentials and call
				// retrieveLatestPoints()

				// token = restTemplate.postForObject(new URI(Url), new
				// HttpEntity<>(map), String.class);

				log.info("AUTH TOKEN FROM USER LEVEL >>>>>>>>>>>>" + token);

				// /group?filter=parent=/group/root
				String[] filtervalue = filter.split("="); //$NON-NLS-1$
				log.info("filtervalue[0] >>>>>>>>>>>>" + filtervalue[0]);
				log.info("filtervalue[1] >>>>>>>>>>>>" + filtervalue[1]);
				List<SolarAsset> assets = this.getSolarAssetFactory().getAssetsByFilterSolar(null, filtervalue[0],
						filtervalue[1], headers);

				this.getAssetMap().put(assetIdentifier, assets);

				log.debug("END getAsset from getCache " + assetIdentifier); //$NON-NLS-1$

			}

		}
		// 4. If the policy evaluated condition is resolved to DENY, this
		// then raises an OAuth2AccessDeniedException and the same exception
		// is returned back as response.
		catch (OAuth2AccessDeniedException e) {
			log.error(
					"Error validating user " + username + " with following error " + e.getCause() + e.getMessage() + e);
			throw new RuntimeException(e);

		}
		return this.getAssetMap().get(assetIdentifier);

	}

	/*private OAuth2RestTemplate getRestTemplate(String username) {
		// get token here based on username password;
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		// resourceDetails.setUsername(username);
		// resourceDetails.setPassword(password);

		// String url = this.accessTokenEndpointUrl;
		String url = "https://predix-acs.run.aws-usw02-pr.ice.predix.io/v1/policy-evaluation";
		resourceDetails.setAccessTokenUri(url);
		resourceDetails.setClientId(this.clientId);
		resourceDetails.setClientSecret(this.clientSecret);

		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);

		return restTemplate;
	}*/

	/*private String retrieveAcs(String authorization, String id) {

		String acsUri = "https://predix-acs.run.aws-usw02-pr.ice.predix.io/v1/policy-evaluation";
		String acsZoneId = "15e2add0-f31a-4fb4-93d8-5debac13fef4";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Predix-Zone-Id", acsZoneId);
		headers.add("Authorization", authorization);
		headers.add("Content-Type", "application/json");

		String body = "{" +

				" 'action': 'GET'" + "," +

				" 'resourceIdentifier':'/asset'" + "," + "'subjectIdentifier':'floor-manager-1'" + "}";
		MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
		postParameters.add("content", body);

		try {

			HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
			HttpEntity<String> response = restTemplate.exchange(acsUri, HttpMethod.POST, requestEntity, String.class);

			log.info("TimeseriesServiceImpl :: retrieveLatestPoints : response ==============================> "
					+ response.getBody());

			return response.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
*/
	@Override
	public Object getCacheGroup(String authorization, String filter) throws Throwable {

		String groupIdentifier = "/group/?filter=" + filter; //$NON-NLS-1$
		log.info("Get AssetGRoup=====================Enter into Authorization=======================" + authorization);

		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (this.getAssetMap().get(groupIdentifier) == null) {
			List<Header> headers = null;
			if (authorization == null) {
				/*String username = auth.getName(); // get logged in username
				log.info("authentication username1 without auth " + username + " ----- " + authorization);
				log.info("auth " + auth);
				log.info("authentication username1 without auth " + username + " ----- " + authorization);
				log.info("auth " + auth);*/
				headers = this.restClient.getSecureTokenForClientId();
				headers = this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
				/*log.info("going into if when authorization is " + authorization);
				log.debug("going into if when authorization is " + authorization);*/

			} else {

				// Authentication auth =
				// SecurityContextHolder.getContext().getAuthentication();
				/*String username = auth.getName(); // get logged in username
				log.info("authentication username else condition " + username);*/
				headers = new ArrayList<Header>();
				headers = this.restClient.addSecureTokenToHeaders(headers, authorization);
				headers = this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
				/*log.info("going into else when authorization provided by UI " + authorization);
				log.info("going into else when authorization provided by UI " + authorization);
				log.info("authentication username1 with auth " + username + " ----- " + authorization);
				log.info("auth " + auth);*/

			}
			Long startTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("Callling getAssetGroup from getCache " + groupIdentifier); //$NON-NLS-1$
			}
			// /group?filter=parent=/group/root
			String[] filtervalue = null;
			List<Group> groups = new ArrayList<>();
			if (filter != null) {
				filtervalue = filter.split("="); //$NON-NLS-1$
				groups = this.groupFactory.getGroupsByFilter(null, filtervalue[0], filtervalue[1], headers);
			} else {
				filtervalue = new String[10];
				groups = this.groupFactory.getAllGroups(headers);
			}

			this.getAssetMap().put(groupIdentifier, groups);
			if (log.isTraceEnabled()) {
				log.debug("Total Group call time = " + (System.currentTimeMillis() - startTime) / 1000); //$NON-NLS-1$
				log.debug("END getAssetGroup from getCache " + groupIdentifier); //$NON-NLS-1$

			}
		}
		return this.getAssetMap().get(groupIdentifier);
	}

	@Override
	public Object getCacheAssetSolar(String authorization, String filter) throws Throwable {
		String assetIdentifier = "/asset/?filter=" + filter; //$NON-NLS-1$
		if (this.getAssetMap().get(assetIdentifier) == null) {
			List<Header> headers = null;
			if (authorization == null) {
				// Note in your app you may want to throw an exception rather
				// than
				// use the info in the property file
				headers = this.restClient.getSecureTokenForClientId();
				this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
			} else {
				headers = new ArrayList<Header>();
				this.restClient.addSecureTokenToHeaders(headers, authorization);

				this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());

			}
			Long startTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("Callling getAssetGroup from getCache " + assetIdentifier); //$NON-NLS-1$
			}
			// /group?filter=parent=/group/root
			String[] filtervalue = filter.split("="); //$NON-NLS-1$
			// List<Asset> assets =
			// this.getAssetFactory().getAssetsByFilter(null, filtervalue[0],
			// filtervalue[1],headers);
			// assets = this.getAssetFactory().getAssetsByFilterSolar(null,
			// filtervalue[0], filtervalue[1],headers);
			List<SolarAsset> assets = this.getSolarAssetFactory().getAssetsByFilterSolar(null, filtervalue[0],
					filtervalue[1] + "&fields=parameters", headers);
			this.getAssetMap().put(assetIdentifier, assets);
			if (log.isTraceEnabled()) {
				log.debug("Total Group call time = " + (System.currentTimeMillis() - startTime) / 1000); //$NON-NLS-1$
				log.debug("END getAssetGroup from getCache " + assetIdentifier); //$NON-NLS-1$

			}

		}
		return this.getAssetMap().get(assetIdentifier);

	}

	@Override
	public Object getCacheClassification(String authorization, String filter) throws Throwable {
		String classificationIdentifier = "/classification/?filter=" + filter; //$NON-NLS-1$
		if (this.getClassificationMap().get(classificationIdentifier) == null) {
			List<Header> headers = null;
			if (authorization == null) {
				headers = this.restClient.getSecureTokenForClientId();
				this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
			} else {
				headers = new ArrayList<Header>();
				this.restClient.addSecureTokenToHeaders(headers, authorization);
				this.restClient.addZoneToHeaders(headers, this.assetRestConfig.getZoneId());
			}
			//Long startTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("Callling getClassification Cache " + classificationIdentifier); //$NON-NLS-1$
			}
			// /group?filter=parent=/group/root
			String[] filtervalue = filter.split("=");

			System.out.println("=====filter split====" + filtervalue[0] + filtervalue[1]);
			log.info("===========>" + headers);

			List<SolarClassification> classification = this.getSolarClassificationFactory()
					.getAttributesByFilterSolar(null, filtervalue[0], filtervalue[1], headers);
			// List<SolarClassification> classification
			// =this.getClassificationFactory().getAttributesByFilterSolar(null,
			// filtervalue[0], filtervalue[1],filtervalue1[1],filtervalue[3],
			// headers);

			// List<SolarClassification> classification
			// =this.getClassificationFactory().getClassificationsByFilterSolar(null,
			// filtervalue[0],
			// filtervalue[1]+">classification&fields=attributes", headers);

			this.getClassificationMap().put(classificationIdentifier,classification);

		}
		return this.getClassificationMap().get(classificationIdentifier);
	}

	@Override
	public List<BaseKpiDataGrid> getWidgetData(String id, String start_time, String end_time, String authorization) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param entity
	 *            to be wrapped into JSON response
	 * @param textPlainType
	 *            -
	 * @return JSON response with entity wrapped
	 */
	/*protected Response handleResult(Object entity, MediaType textPlainType) {
		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.type(textPlainType);
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}*/

	/**
	 * -
	 * 
	 * @return string
	 */

	public String greetings() {
		return "{\"status\":\"up\", \"date\": \" " + new Date() + "\"}";
	}

	@Override
	public Object getCacheGroupNavigation(String authorization) throws Throwable {
		String jsonGroupString = JSONObject.valueToString((getCacheGroup(authorization, null)));
		jsonGroupString = "{\"groupNodeList\" : " + jsonGroupString + "}";
		List<GroupNode> groupNodes = mapper.readValue(jsonGroupString, GroupNodeList.class).getGroupNodeList();
		List<GroupNode> rootNodeList = addChildNode(groupNodes);
		String jsonGroupNavString = createGroupNavJson(mapper, rootNodeList);
		System.out.println(jsonGroupNavString);
		return jsonGroupNavString;
	}

	private String createGroupNavJson(ObjectMapper mapper, List<GroupNode> rootNodeList)
			throws JsonProcessingException {
		String jsonGroupNavString = "[";
		for (GroupNode rootNode : rootNodeList) {
			if (jsonGroupNavString != "[") {
				jsonGroupNavString = jsonGroupNavString + ",";
			}
			jsonGroupNavString = jsonGroupNavString.concat(mapper.writeValueAsString(rootNode));
		}
		jsonGroupNavString = jsonGroupNavString + "]";
		return jsonGroupNavString;
	}

	private List<GroupNode> addChildNode(List<GroupNode> groupNodes) {
		List<GroupNode> rootNodeList = new ArrayList<>();
		for (GroupNode node : groupNodes) {
			GroupNode nodeParent = new GroupNode();
			nodeParent.setUri(node.getParent());
			node.setRoleName(node.getName());
			if (groupNodes.indexOf(nodeParent) > -1) {
				GroupNode parent = groupNodes.get(groupNodes.indexOf(nodeParent));
				parent.getChildren().add(node);
			} else {
				node.setRoot(true);
				rootNodeList.add(node);
			}
		}
		return rootNodeList;
	}

}
