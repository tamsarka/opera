/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.dto;

/**
 * @author 10620506
 *
 */
public class PlantKpiDashboardValues {

	private String keyName;
	
	private String keyValue;

	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * @return the keyValue
	 */
	public String getKeyValue() {
		return keyValue;
	}

	/**
	 * @param keyValue the keyValue to set
	 */
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	/**
	 * @param keyName
	 * @param keyValue
	 */
	public PlantKpiDashboardValues(String keyName, String keyValue) {
		super();
		this.keyName = keyName;
		this.keyValue = keyValue;
	}
	
	
	
}
