package com.lntinfotech.ge.predix.datasource.datagrid.service;

import com.lntinfotech.ge.predix.datasource.datagrid.dto.DatagridResponse;
import com.lntinfotech.ge.predix.datasource.datagrid.dto.SummaryKpiDataGridResponse;

/**
 * 
 * @author 212421693
 *
 */
public interface GroupDatagridService
{

    /**
     * API for the Asset GroupDataGrid
     * 
     * @param id : id
     * @param appId : appid
     * @param authorization : authorization
     * @param start_time -
     * @param end_time -
     * @return Response
     * @throws Throwable Throwable
     */
    public DatagridResponse getDatagrid(String id, String appId,
            String authorization, String start_time,
            String end_time)
            throws Throwable;

    /**
     * 
     * @return String
     */
    public String ping();

    /**
     * 
     * @param id : id
     * @param appId : application Id
     * @param authorization : authorization
     * @param start_time : start time
     * @param end_time : end _time
     * @return Response
     * @throws Throwable Throwable
     */
    public SummaryKpiDataGridResponse getDatagridSummary(String id, String appId,
            String authorization, String start_time,
            String end_time)
            throws Throwable;
}
