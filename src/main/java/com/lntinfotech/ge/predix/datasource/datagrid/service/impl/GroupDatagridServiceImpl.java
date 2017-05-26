package com.lntinfotech.ge.predix.datasource.datagrid.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.BaseKpiDataGrid;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.Column;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.GroupKpiDataGrid;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SummaryKpiDataGridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.service.GroupDatagridService;
import com.lntinfotech.ge.predix.datasource.handlers.DataSourceHandler;

/**
 * DatagridService implementation to fetch data for the widgets : fetches the
 * list of tags for the asset that have KPI metrics
 * 
 * @author 212421693
 *
 */
@Service(value = "groupDatagridService")
public class GroupDatagridServiceImpl extends DatagridServiceImpl implements
		GroupDatagridService {

	private static Logger log = LoggerFactory
			.getLogger(GroupDatagridServiceImpl.class);

	@Autowired
	private
	DataSourceHandler groupDataSourceHandler;

	@Override
	public DatagridResponse getDatagridResponseObject() {
		DatagridResponse response = new DatagridResponse();
		response.getColumns().addAll(getGroupColumnData());
		return response;
	}

	private List<Column> getGroupColumnData() {

		List<Column> columns = new ArrayList<Column>();
		Column column = new Column("asset", "string", "text", new Double(25),  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
				ColumnsTags.getString("GroupDatagridServiceImpl.0")); //$NON-NLS-1$
		columns.add(column);
		column = new Column(ColumnsTags.getString("GroupDatagridServiceImpl.1"), ColumnsTags.getString("GroupDatagridServiceImpl.2"), ColumnsTags.getString("GroupDatagridServiceImpl.3"), new Double(20), ColumnsTags.getString("GroupDatagridServiceImpl.4")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		columns.add(column);
		column = new Column(ColumnsTags.getString("GroupDatagridServiceImpl.5"), ColumnsTags.getString("GroupDatagridServiceImpl.6"), ColumnsTags.getString("GroupDatagridServiceImpl.7"), new Double(20), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				ColumnsTags.getString("GroupDatagridServiceImpl.8")); //$NON-NLS-1$
		columns.add(column);
		column = new Column(ColumnsTags.getString("GroupDatagridServiceImpl.9"), ColumnsTags.getString("GroupDatagridServiceImpl.10"), ColumnsTags.getString("GroupDatagridServiceImpl.11"), new Double(10), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				ColumnsTags.getString("GroupDatagridServiceImpl.12")); //$NON-NLS-1$
		columns.add(column);
		column = new Column(ColumnsTags.getString("GroupDatagridServiceImpl.13"), ColumnsTags.getString("GroupDatagridServiceImpl.14"), ColumnsTags.getString("GroupDatagridServiceImpl.15"), new Double(10), ColumnsTags.getString("GroupDatagridServiceImpl.16")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		columns.add(column);
		column = new Column(ColumnsTags.getString("GroupDatagridServiceImpl.17"), ColumnsTags.getString("GroupDatagridServiceImpl.18"), ColumnsTags.getString("GroupDatagridServiceImpl.19"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				new Double(10), ColumnsTags.getString("GroupDatagridServiceImpl.20")); //$NON-NLS-1$
		columns.add(column);

		return columns;
	}

	
	@Override
	public DatagridResponse getDatagrid(String id, String appId, String authorization,
			String start_time, String end_time) throws Throwable {
		log.debug(ColumnsTags.getString("GroupDatagridServiceImpl.21")+id); //$NON-NLS-1$
		return getResponse(id, appId, authorization, start_time, end_time);
	}

	@Override
	public GroupKpiDataGrid getKpiDataGrid(BaseKpiDataGrid groupKpi) {
		return (GroupKpiDataGrid) groupKpi;
	}

	@Override
	public DataSourceHandler getDataSourceHandler() {
		return this.getGroupDataSourceHandler();
	}
	
	@Override
	public String ping() {
		return ColumnsTags.getString("GroupDatagridServiceImpl.22"); //$NON-NLS-1$
	}

	@Override
	public SummaryKpiDataGridResponse getDatagridSummary(String id, String appId,
			String authorization, String start_time, String end_time)
			throws Throwable {
		log.debug(ColumnsTags.getString("GroupDatagridServiceImpl.23")+id); //$NON-NLS-1$
		return getSummary(id,appId,authorization,start_time,end_time);

	}
	
	
	/**
	 * @param id : id 
	 * @param appId : appId
	 * @param authorization : authorization
	 * @param start_time : start_time
	 * @param end_time : end_time
	 * @return Response -
	 */
	public SummaryKpiDataGridResponse getSummary(String id,
			String appId, String authorization,
			String start_time, String end_time) {
	
		SummaryKpiDataGridResponse summarykpi = getDataSourceHandler().getSummary(id, start_time,end_time,authorization);
		return summarykpi;
	}

	/**
	 * @return the groupDataSourceHandler
	 */
	public DataSourceHandler getGroupDataSourceHandler() {
		return this.groupDataSourceHandler;
	}

	/**
	 * @param groupDataSourceHandler the groupDataSourceHandler to set
	 */
	public void setGroupDataSourceHandler(DataSourceHandler groupDataSourceHandler) {
		this.groupDataSourceHandler = groupDataSourceHandler;
	}

}
