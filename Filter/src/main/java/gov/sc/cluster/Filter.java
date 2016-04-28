package gov.sc.cluster;

import gov.sc.model.TargetCell;

import java.util.List;

public class Filter {
	private List<TargetCell> list;

	/**
	 * 
	 */
	public Filter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param list
	 */
	public Filter(List<TargetCell> list) {
		super();
		this.list = list;
	}

	public List<TargetCell> getList() {
		return list;
	}

	public void setList(List<TargetCell> list) {
		this.list = list;
	}

}
