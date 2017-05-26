/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.service;

/**
 * @author 10620506
 *
 */
public interface AuthenticationAuthorizationService {

	public String checkUserIsValid(String authCode, String requestURL);
}
