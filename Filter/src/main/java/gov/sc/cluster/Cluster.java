package gov.sc.cluster;

import gov.sc.model.TargetCell;

import java.util.List;

public class Cluster extends Filter{

	
	/**
	 * 
	 */
	public Cluster() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param list
	 */
	public Cluster(List<TargetCell> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 该方法实现对list中数据的聚类结果
	 * @return 返回聚类后的list
	 */
	public List<TargetCell> getResult(){
		return null;
	}
}
