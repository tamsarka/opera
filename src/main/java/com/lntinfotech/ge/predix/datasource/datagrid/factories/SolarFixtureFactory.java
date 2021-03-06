/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ge.predix.solsvc.bootstrap.ams.common.AssetRestConfig;
import com.ge.predix.solsvc.bootstrap.ams.dto.CustomModel;
import com.ge.predix.solsvc.bootstrap.ams.integration.HttpResponseException;
import com.ge.predix.solsvc.bootstrap.ams.integration.RestConstants;
import com.ge.predix.solsvc.ext.util.JsonMapper;
import com.ge.predix.solsvc.restclient.config.IOauthRestConfig;
import com.ge.predix.solsvc.restclient.impl.RestClient;
import com.lntinfotech.ge.predix.datasource.common.SolarRestMarshal;

/**
 * @author 10620506
 *
 */
public abstract class SolarFixtureFactory implements RestConstants {
	private static Logger log = LoggerFactory.getLogger(SolarFixtureFactory.class);

	@Autowired
	private SolarRestMarshal config;
	/**
	 * 
	 */
	@Autowired
	protected AssetRestConfig assetRestConfig;

	/**
	 * 
	 */
	@Autowired
	protected RestClient restClient;

	@Autowired
	private IOauthRestConfig restConfig;

	/**
	 * 
	 */
	protected Set<String> toDelete = new HashSet<>();

	/**
	 * 
	 */
	@Autowired
	protected JsonMapper jsonMapper;

	/**
	 * -
	 */
	@PostConstruct
	public void init() {
		this.jsonMapper.addAllXmlSeeAlsoSubtypes(CustomModel.class);
	}

	/**
	 * @return -
	 */
	protected String generateName() {
		return getClass().getSimpleName() + "_" + UUID.randomUUID(); //$NON-NLS-1$
	}

	/**
	 * @return the restClient
	 */
	public RestClient getRestClient() {
		return this.restClient;
	}

	/**
	 * @param restClient
	 *            the restClient to set
	 */
	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	/**
	 * @return the restConfig
	 */
	public IOauthRestConfig getRestConfig() {
		return this.restConfig;
	}

	/**
	 * @param restConfig
	 *            the restConfig to set
	 */
	public void setRestConfig(IOauthRestConfig restConfig) {
		this.restConfig = restConfig;
	}

	/**
	 * @return the toDelete
	 */
	public Set<String> getToDelete() {
		return this.toDelete;
	}

	/**
	 * @param toDelete
	 *            the toDelete to set
	 */
	public void setToDelete(Set<String> toDelete) {
		this.toDelete = toDelete;
	}

	/**
	 * @param clazz
	 *            -
	 * @param httpResponse
	 *            -
	 * @return -
	 * @throws IOException
	 *             -
	 * @throws ParseException-
	 */
	@SuppressWarnings("nls")
	protected <T> List<T> getObjectFromJson(Class<T> clazz, HttpResponse httpResponse)
			throws ParseException, IOException {
		org.apache.http.HttpEntity responseEntity = httpResponse.getEntity();
		String json = EntityUtils.toString(responseEntity);
		log.debug("getObjectFromJson clazz=" + clazz + " response=" + json);
		System.out.println("getObjectFromJson clazz=" + clazz + " response=" + json);
		return this.jsonMapper.fromJsonArray(json, clazz);
	}

	/**
	 * @param object
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	public <T> CloseableHttpResponse create(T object, List<Header> headers) {
		if (object == null)
			return null;
		List<T> list = new ArrayList<>();
		list.add(object);
		return create(list, headers);
	}

	/**
	 * This is the original method for Classification, Groups etc TODO: delete
	 * this and the "map" (see this.config.getPath()) in favor of
	 * createCustomModels
	 * 
	 * @param objects
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	@SuppressWarnings("nls")
	public <T> CloseableHttpResponse create(List<T> objects, List<Header> headers) {
		if (objects == null || objects.get(0) == null)
			return null;
		List<Header> localheaders = ensureDefaultHeadersArePresent(headers);
		String url = this.config.getPath(objects.get(0).getClass());
		String body = this.jsonMapper.toJson(objects);
		log.debug("url=" + url + " create json=" + body);
		CloseableHttpResponse response = this.restClient.post(url, body, localheaders,
				this.assetRestConfig.getAssetConnectionTimeout(), this.assetRestConfig.getAssetSocketTimeout());
		return response;
	}

	/**
	 * @param objects
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	@SuppressWarnings({ "nls" })
	public <T> CloseableHttpResponse createCustomModels(List<T> objects,List<Header> headers) {
		if (objects == null || objects.get(0) == null)
			return null;
		List<Header> localheaders = ensureDefaultHeadersArePresent(headers);
		String url = this.config.createCustomModelUrlForPost(objects.get(0));
		String json = this.jsonMapper.toJson(objects);
		log.debug("url=" + url + " create json=" + json);
		CloseableHttpResponse response = this.restClient.post(url, json, localheaders,
				this.assetRestConfig.getAssetConnectionTimeout(), this.assetRestConfig.getAssetSocketTimeout());

		return response;
	}

	/**
	 * @param objects
	 *            -
	 * @param jsonString
	 *            body
	 * @param headers
	 *            -
	 * @return HttpResponse
	 */
	public <T> CloseableHttpResponse createCustomModels(List<T> objects, String jsonString, List<Header> headers) {
		List<Header> localheaders = ensureDefaultHeadersArePresent(headers);
		String url = this.config.createCustomModelUrlForPost(objects.get(0));
		CloseableHttpResponse response = this.restClient.post(url, jsonString, headers,
				this.assetRestConfig.getAssetConnectionTimeout(), this.assetRestConfig.getAssetSocketTimeout());
		return response;
	}

	/**
	 * @param object
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	@SuppressWarnings("nls")
	public <T> CloseableHttpResponse updateCustomModel(T object, List<Header> headers) {
		if (object == null)
			return null;

		// String url = this.config.createCustomModelUrlForPost(objects.get(0));
		String url = this.config.createCustomModelUrlForPut(object);
		List<Header> localheaders = ensureDefaultHeadersArePresent(headers);
		String json = this.jsonMapper.toJson(Arrays.asList(object));
		log.debug("url=" + url + " create json=" + json);
		CloseableHttpResponse response = this.restClient.put(url, json, localheaders,
				this.assetRestConfig.getAssetConnectionTimeout(), this.assetRestConfig.getAssetSocketTimeout());

		return response;
	}

	/**
	 * @param object
	 *            -
	 * @param complexType
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	@SuppressWarnings("nls")
	public <T> CloseableHttpResponse updateCustomModel(T object, String complexType, List<Header> headers) {
		if (object == null)
			return null;

		// String url = this.config.createCustomModelUrlForPost(objects.get(0));
		String url = this.config.createCustomModelUrlForPut(object, complexType);
		List<Header> localheaders = ensureDefaultHeadersArePresent(headers);
		String json = this.jsonMapper.toJson(Arrays.asList(object));
		log.debug("UUUurl=" + url + " create json=" + json);
		CloseableHttpResponse response = this.restClient.put(url, json, localheaders,
				this.assetRestConfig.getAssetConnectionTimeout(), this.assetRestConfig.getAssetSocketTimeout());

		return response;
	}

	private List<Header> ensureDefaultHeadersArePresent(List<Header> headers) {
		List<Header> localHeaders = headers;
		boolean contentTypeFound = false;
		for (Header header : headers) {
			if (header.getName().equals("Content-Type")) { //$NON-NLS-1$
				contentTypeFound = true;
				break;
			}
		}
		if (!contentTypeFound) {
			localHeaders.add(new BasicHeader("Content-Type", "application/json")); //$NON-NLS-1$//$NON-NLS-2$
		}
		return localHeaders;
	}

	/**
	 * @param clazz
	 *            Class
	 * @param uriSegment
	 *            uRL
	 * @param jsonString
	 *            body
	 * @param headers
	 *            -
	 * @return HttpResponse
	 */
	public CloseableHttpResponse create(Class<?> clazz, String uriSegment, String jsonString, List<Header> headers) {
		// config.setupRestMarshal(context);
		String url = this.config.getPath(clazz) + uriSegment;
		CloseableHttpResponse response = this.restClient.post(url, jsonString, headers,
				this.assetRestConfig.getAssetConnectionTimeout(), this.assetRestConfig.getAssetSocketTimeout());
		return response;
	}

	/**
	 * 
	 * @param object
	 *            T
	 * @param headers
	 *            -
	 * @return <T>
	 */
	public <T> HttpResponse update(T object, List<Header> headers) {
		if (object == null)
			return null;
		List<T> list = new ArrayList<>();
		list.add(object);
		return update(list, headers);
	}

	/**
	 * 
	 * @param clazz
	 *            Class<?>
	 * @param headers
	 *            -
	 * @return HttpResponse
	 */
	public CloseableHttpResponse get(Class<?> clazz, List<Header> headers) {
		// config.setupRestMarshal(context);
		String url = this.config.getPath(clazz);
		return this.restClient.get(url, headers, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	/**
	 * 
	 * @param clazz
	 *            Class<?>
	 * @param pathParam
	 *            String
	 * @param headers
	 *            headers
	 * @return HttpResponse
	 */
	@SuppressWarnings("nls")
	public CloseableHttpResponse get(Class<?> clazz, String pathParam, List<Header> headers) {
		// config.setupRestMarshal(context);
		String url = this.config.getPath(clazz);
		if (pathParam != null)
			url += "/" + pathParam; //$NON-NLS-1$
		if (!this.restClient.hasToken(headers))
			throw new UnsupportedOperationException("calls to Predix Asset require an Authorization token header");
		if (!this.restClient.hasZoneId(headers))
			throw new UnsupportedOperationException("calls to Predix Asset require an Predix-Zone-Id header");
		log.debug("Calling URL From Asset BootStrap -->" + url);
		return this.restClient.get(url, headers, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	/*
	 * @param clazz -
	 * 
	 * @param pathParam -
	 * 
	 * @param filter -
	 * 
	 * @param value -
	 * 
	 * @param headers -
	 * 
	 * @return -
	 */
	public CloseableHttpResponse get(Class<?> clazz, String pathParam, String filter, String value,
			List<Header> headers) {
		String pathSegment = pathParam;
		if (pathParam == null) {
			pathSegment = new String(""); //$NON-NLS-1$
		}
		String url = this.config.getPath(clazz) + "/" + pathSegment + "?filter=" //$NON-NLS-1$//$NON-NLS-2$
				+ filter + "=" + value; //$NON-NLS-1$
		System.out.println("======" + url);
		return this.restClient.get(url, headers, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	// url to get solar classification attributes
	// https://predix-asset.run.aws-usw02-pr.ice.predix.io/classification?filter=assetId=INV1https://predix-asset.run.aws-usw02-pr.ice.predix.io/classification?filter=assetId=INV1>classification&fields=attributes
	public CloseableHttpResponse getSolarAttributes(Class<?> clazz, String pathParam, String filter, String value,
			List<Header> headers) {
		String pathSegment = pathParam;
		if (pathParam == null) {
			pathSegment = new String(""); //$NON-NLS-1$
		}
		String url = this.config.getPath(clazz) + "/" + pathSegment + "?filter=" //$NON-NLS-1$//$NON-NLS-2$
				+ filter + "=" + value + "%3E" + "classification&fields=attributes"; //$NON-NLS-1$
		/*
		 * System.out.println("======" + url); try { url =
		 * URLEncoder.encode(url,"UTF-8"); } catch (UnsupportedEncodingException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		return this.restClient.get(url, headers, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	/**
	 * @param filterUri
	 *            -
	 * @param model
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	@SuppressWarnings("nls")
	public CloseableHttpResponse getCustomModels(String filterUri, String model, List<Header> headers) {
		List<Header> localHeaders = ensureDefaultHeadersArePresent(headers);
		if (!this.restClient.hasToken(headers))
			throw new UnsupportedOperationException("calls to Predix Asset require an Authorization token header");
		if (!this.restClient.hasZoneId(headers))
			throw new UnsupportedOperationException("calls to Predix Asset require an Predix-Zone-Id header");
		String assetUri = this.assetRestConfig.getAssetUri();
		if (!assetUri.endsWith("/") && !filterUri.startsWith("/"))
			assetUri += "/" + filterUri;
		else
			assetUri += filterUri;
		return this.restClient.get(assetUri, localHeaders, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	/**
	 * 
	 * @param clazz
	 *            Class<?>
	 * @param pathParam
	 *            pathParam
	 * @param headers
	 *            -
	 * @return HttpResponse
	 */
	public CloseableHttpResponse delete(Class<?> clazz, String pathParam, List<Header> headers) {
		// config.setupRestMarshal(context);
		String url = this.config.getPath(clazz) + "/" + pathParam; //$NON-NLS-1$
		return this.restClient.delete(url, headers, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	/**
	 * @param uri
	 *            -
	 * @param headers
	 *            -
	 * @return -
	 */
	public CloseableHttpResponse delete(String uri, List<Header> headers) {
		return this.restClient.delete(uri, headers, this.assetRestConfig.getAssetConnectionTimeout(),
				this.assetRestConfig.getAssetSocketTimeout());
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(SolarRestMarshal config) {
		this.config = config;
	}

	/**
	 * @param object
	 *            -
	 * @param headers
	 *            -
	 * @param response
	 *            -
	 */
	@SuppressWarnings("nls")
	protected void handleException(Object object, Object headers, HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String responseBody = null;
		try {
			responseBody = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			log.error("Unable to parse HttpResponse Body, swallowing error since we are throwing a few lines down", e);
		} catch (IOException e) {
			log.error("Unable to parse HttpResponse Body, swallowing error since we are throwing a few lines down", e);
		}
		throw new HttpResponseException((response == null ? "No HttpResponse" : response.toString()) + " Response Body="
				+ responseBody + " Entity=" + object + " Headers=" + headers, response);
	}

}
