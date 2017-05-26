/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.UserAuthorizationDto;
import com.lntinfotech.ge.predix.datasource.datagrid.service.AuthenticationAuthorizationService;

/**
 * @author 10620506
 *
 */
@RestController
@RequestMapping(value="/auth/user/")
public class AuthenticationAuthorizationController {
	
	private static Logger log = Logger.getLogger(AuthenticationAuthorizationController.class);

	@Autowired
	public AuthenticationAuthorizationService authenticationAuthorizationService;
		
	
	/*@RequestMapping(value = "valid", method = { RequestMethod.GET })
    public String validateUser(@RequestParam("authCode") String authCode, HttpServletRequest request) throws Exception {
		
			// Get token based on the authorization code
			log.info("AuthenticationAuthorizationController: validateUser :: getting token based on the auth_code"+request.getParameter("authCode"));
			String auth = authenticationAuthorizationService.checkUserIsValid(authCode);
			return auth;
	}*/
	
	@RequestMapping(value = "validate", method = { RequestMethod.GET })
    public void validateUserByAuthCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
		
			// Get token based on the authorization code
			log.info("AuthenticationAuthorizationController: validateUser :: getting token based on the auth_code"+request.getParameter("code"));
			log.info("request URL" + request.getRequestURL());
			log.info("request URI " + request.getRequestURI());
			log.info(" redirect URL " + (request.getRequestURL().toString()).replaceAll(request.getRequestURI(), ""));
			String requestURL = (request.getRequestURL().toString()).replaceAll(request.getRequestURI(), "");
			String authorizationResponse = authenticationAuthorizationService.checkUserIsValid(code,requestURL);
			String errorJsonObject = "{error}";
			if(!authorizationResponse.equals(errorJsonObject)){
				ObjectMapper mapper = new ObjectMapper();
				UserAuthorizationDto userAuthDto = mapper.readValue(authorizationResponse, UserAuthorizationDto.class);
				HttpSession session = request.getSession();
				session.setAttribute("authorizedUserDetails", userAuthDto);
				session.setMaxInactiveInterval(Integer.valueOf(userAuthDto.getExpires_in()));
			}
			//return auth;
			httpResponse.sendRedirect(requestURL+"/#/dashboardPage");
	}
	
	
	@RequestMapping(value = "userIsLoggedIn", method = { RequestMethod.GET })
    public String userIsLoggedIn(HttpServletRequest request) throws Exception {
			String userIsLoggedIn = "false";
			// Get token based on the authorization code
			HttpSession session = request.getSession(false);
			if(session != null){
				UserAuthorizationDto userAuthDto =(UserAuthorizationDto) session.getAttribute("authorizedUserDetails");
				if(userAuthDto != null){
					if(userAuthDto.getAccess_token() != null && !(userAuthDto.getAccess_token()).equals("")){
						userIsLoggedIn = "true";
					}
				}
			}
			return userIsLoggedIn;
	}
	
}
