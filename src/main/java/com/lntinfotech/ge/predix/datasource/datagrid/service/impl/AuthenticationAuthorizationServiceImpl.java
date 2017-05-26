/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.lntinfotech.ge.predix.datasource.datagrid.service.AuthenticationAuthorizationService;

/**
 * @author 10620506
 *
 */
@Service(value="authenticationAuthorizationService")
public class AuthenticationAuthorizationServiceImpl implements AuthenticationAuthorizationService{
	
	private static Logger log = LoggerFactory.getLogger(AuthenticationAuthorizationServiceImpl.class);
	
	@Value("${basicAuthorization}")
	private String authorization;
	
	@Value("${accessTokenEndpointUrl}")
	private String accessTokenEndpointUrl;
	
	@Value("${accessTokenRedirectUrl}")
	private String accessTokenRedirectUrl;

	public String checkUserIsValid(String authCode, String requestURL) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", authorization);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// create form parameters as a MultiValueMap
		final MultiValueMap<String, String> formVars = new LinkedMultiValueMap<>();
		formVars.add( "grant_type", "authorization_code" );
		formVars.add( "response_type", "token" );
		//formVars.add( "redirect_uri", accessTokenRedirectUrl );
		formVars.add( "redirect_uri", "http://localhost:9092/auth/user/validate" );
		formVars.add( "code", authCode);
		
		
		HttpEntity<MultiValueMap<String,String>> requestEntity=new HttpEntity<MultiValueMap<String,String>>(formVars,headers);
		String validUser = "{error}";
		try{
			ResponseEntity<String> response = restTemplate.exchange(accessTokenEndpointUrl,HttpMethod.POST, requestEntity, String.class);
			log.info("Status Code " + response.getStatusCode());
			if("200".equals(response.getStatusCode().toString())){
				log.info("inside equals method");
				validUser = response.getBody();
			}
			log.info("------------ http response -----------" + response.getBody());
		}
		catch(Exception exp){
			log.error(exp.getMessage());
		}
		return validUser;
	}
	
	
	

}
