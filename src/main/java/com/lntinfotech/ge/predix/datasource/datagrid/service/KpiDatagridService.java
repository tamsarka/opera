package com.lntinfotech.ge.predix.datasource.datagrid.service;

import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;

/**
 * 
 * @author 10620506
 *
 */
public interface KpiDatagridService {

	/**
	 * @param entityType -
	 * @param id -
	 * @param authorization -
	 * @return -
	 * @throws Throwable -
	 */
	public DatagridResponse getDatagrid(String entityType,
			String id,
			String authorization) throws Throwable;
	
	/**
	 * 
	 * @return String
	 */
	public String ping();
	
}
