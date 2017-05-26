/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lntinfotech.ge.predix.datasource.datagrid.service.AssetServiceApi;

/**
 * @author 10620506
 *
 */
@RestController
@RequestMapping(value = "/services")
public class AssetController {
	
	@Autowired
	private AssetServiceApi assetService;
	
	@RequestMapping(value = "/asset", method = RequestMethod.GET)
	/*API to returned Cached Asset") */
	public Object getCacheAsset(@RequestParam("filter") String filter,Principal principal) throws Throwable {
		String authorization = getAccessTokenByPrincipal(principal);
		return assetService.getCacheAsset(authorization, filter);
	}
	
	
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	/*API list of Cached Asset by groups") */
	public Object getCacheGroup(@RequestParam("filter") String filter,Principal principal) throws Throwable{
		String authorization = getAccessTokenByPrincipal(principal);
		return assetService.getCacheGroup(authorization, filter);
	}
	
	
	@RequestMapping(value = "/assetSolar", method = RequestMethod.GET)
	/*API to returned Cached Asset") */
	public Object getCacheAssetSolar(
			@RequestParam("filter") String filter,Principal principal) throws Throwable{
		String authorization = getAccessTokenByPrincipal(principal);
		return assetService.getCacheAssetSolar(authorization, filter);
	}
	
	
	@RequestMapping(value = "/classification", method = RequestMethod.GET)
	/*API list of Cached Asset by classification") */
	public Object getCacheClassification(
			@RequestParam("filter") String filter,Principal principal) throws Throwable{
		String authorization = getAccessTokenByPrincipal(principal);
		return assetService.getCacheClassification(authorization, filter);
	}
	
	
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String greetings() {
    	return "Greetings from Asset Service";
    }
    
    
	@RequestMapping(value = "/group/nav", method = RequestMethod.GET)
	/*API list of Cached Asset tree by groups") */
    public Object getCacheGroupNavigation(Principal principal) throws Throwable{
		String authorization = getAccessTokenByPrincipal(principal);
		return assetService.getCacheGroupNavigation(authorization);
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
