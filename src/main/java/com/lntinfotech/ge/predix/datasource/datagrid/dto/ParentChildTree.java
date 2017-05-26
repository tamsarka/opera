/**
 * 
 */
package com.lntinfotech.ge.predix.datasource.datagrid.dto;

import java.util.List;

/**
 * @author 10620506
 *
 */
public class ParentChildTree {
	
	private String name;
	
	private List<ParentChildTree> children;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the children
	 */
	public List<ParentChildTree> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<ParentChildTree> children) {
		this.children = children;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ParentChildTree)) {
			return false;
		}
		ParentChildTree other = (ParentChildTree) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	

}
