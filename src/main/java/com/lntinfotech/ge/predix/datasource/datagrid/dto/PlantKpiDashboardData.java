/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.dto;

import java.util.List;

/**
 * @author 10620506
 *
 */
public class PlantKpiDashboardData {
	
	private List<PlantKpiDashboardValues> frequencyBlock;
	
	private List<PlantKpiDashboardValues> currentBlock;
	
	private List<PlantKpiDashboardValues> ratioBlock;
	
	private List<PlantKpiDashboardValues> historyBlock;

	/**
	 * @return the frequencyBlock
	 */
	public List<PlantKpiDashboardValues> getFrequencyBlock() {
		return frequencyBlock;
	}

	/**
	 * @param frequencyBlock the frequencyBlock to set
	 */
	public void setFrequencyBlock(List<PlantKpiDashboardValues> frequencyBlock) {
		this.frequencyBlock = frequencyBlock;
	}

	/**
	 * @return the currentBlock
	 */
	public List<PlantKpiDashboardValues> getCurrentBlock() {
		return currentBlock;
	}

	/**
	 * @param currentBlock the currentBlock to set
	 */
	public void setCurrentBlock(List<PlantKpiDashboardValues> currentBlock) {
		this.currentBlock = currentBlock;
	}

	/**
	 * @return the ratioBlock
	 */
	public List<PlantKpiDashboardValues> getRatioBlock() {
		return ratioBlock;
	}

	/**
	 * @param ratioBlock the ratioBlock to set
	 */
	public void setRatioBlock(List<PlantKpiDashboardValues> ratioBlock) {
		this.ratioBlock = ratioBlock;
	}

	/**
	 * @return the historyBlock
	 */
	public List<PlantKpiDashboardValues> getHistoryBlock() {
		return historyBlock;
	}

	/**
	 * @param historyBlock the historyBlock to set
	 */
	public void setHistoryBlock(List<PlantKpiDashboardValues> historyBlock) {
		this.historyBlock = historyBlock;
	}
	

}
