package gov.sc.cluster;

import gov.sc.model.TargetCell;

import java.util.List;
import java.util.Map;

public class Statistic extends Filter {

	/**
	 * 
	 */
	public Statistic() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param list
	 */
	public Statistic(List<TargetCell> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 该方法实现对list中数据的统计结果
	 * 
	 * @return 返回一个Map，key表示一个聚簇中有代表性的一个TargetCell，value表示这个聚簇中元素的个数
	 */
	public Map<TargetCell, Integer> getResult() {
		return null;
	}
}
