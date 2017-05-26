package com.lntinfotech.ge.predix.datasource.datagrid.service;

/**
 * 
 * @author 10620506
 *
 */
public interface AssetServiceApi {
	
	public Object getCacheAsset(String authorization,
			String filter) throws Throwable ;
	
	public Object getCacheGroup(String authorization,
			String filter) throws Throwable;
	
	public Object getCacheAssetSolar(String authorization,
			String filter) throws Throwable;
	
	public Object getCacheClassification(String authorization,
			String filter) throws Throwable;

    public String greetings() ;
    
    public Object getCacheGroupNavigation(String authorization) throws Throwable;
    
}
