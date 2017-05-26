package com.lntinfotech.ge.predix.datasource.datagrid.service;

import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SummaryKpiDataGridResponse;

/**
 * 
 * @author 10620506
 *
 */
public interface AssetDatagridService {

	public DatagridResponse getDatagrid(String id, String appId, String authorization);

	public SummaryKpiDataGridResponse getDatagridSummary(String id, String appId, String authorization,
			String start_time, String end_time);

}
