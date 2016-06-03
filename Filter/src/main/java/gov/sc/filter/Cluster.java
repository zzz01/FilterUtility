package gov.sc.filter;

import gov.sc.seg.Analysis;
import gov.sc.utils.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cluster {
	private List<String[]> cells;
	private int tarLine;
	private int timeLine;
	private List<String[]> list_seg;
	private Analysis analysis = Analysis.getInstance();
	private List<List<Integer>> result_int;
	private List<List<String[]>> result_all;
	private List<String[]> result_original;

	public Cluster(List<String[]> cells, int tarLine, int timeLine) {
		this.cells = cells;
		this.tarLine = tarLine;
		this.timeLine = timeLine;
		// list_seg = getSegmentList();
	}

	public Cluster() {
	}

	private List<String[]> getSegmentList() {
		if (cells == null || tarLine < 0 || cells.size() == 0) {
			throw new IllegalArgumentException("原始的list和目标列数据不合法");
		}
		List<String[]> list_seg = new ArrayList<String[]>();
		for (String[] row : cells) {
			list_seg.add(analysis.parse(row[tarLine]));
		}
		return list_seg;
	}

	private List<List<Integer>> cluster() {
		if (list_seg == null || list_seg.size() == 0) {
			throw new IllegalArgumentException("分词列表是空的");
		}
		List<List<Integer>> list_set = new ArrayList<List<Integer>>();
		for (int i = 0; i < list_seg.size(); i++) {
			int max_sim_set_index = -1;
			float max_sim = -1.0f;
			if (i == 0) {
				List<Integer> set = new ArrayList<Integer>();
				set.add(0);
				list_set.add(set);
				continue;
			}
			for (int j = 0; j < list_set.size(); j++) {
				float sim = getSim(list_seg.get(i), list_set.get(j));
				if (max_sim < sim) {
					max_sim = sim;
					max_sim_set_index = j;
				}
			}
			if (max_sim <= 0.40f) {
				List<Integer> set = new ArrayList<Integer>();
				set.add(i);
				list_set.add(set);
			} else {
				list_set.get(max_sim_set_index).add(i);
			}
		}
		return list_set;
	}

	private float getSim(String[] seg, List<Integer> set) {
		if (seg.length == 0 || set.size() == 0) {
			return 0.0f;
		}
		float sum = 0.0f;
		for (int i : set) {
			sum += getSim(seg, list_seg.get(i));
		}
		return sum / set.size();
	}

	private float getSim(String[] seg_1, String[] seg_2) {
		if (seg_1.length == 0 || seg_2.length == 0) {
			return 0.0f;
		}
		// int length = seg_1.length > seg_2.length ? seg_2.length :
		// seg_1.length;
		int count = 0;
		for (String word_1 : seg_1) {
			for (String word_2 : seg_2) {
				if (word_1.equals(word_2)) {
					count++;
					break;
				}
			}
		}
		float sim_1 = (float) count / (float) seg_1.length;
		count = 0;
		for (String word_2 : seg_2) {
			for (String word_1 : seg_1) {
				if (word_2.equals(word_1)) {
					count++;
					break;
				}
			}
		}
		float sim_2 = (float) count / (float) seg_2.length;

		return (sim_1 + sim_2) / 2.0f;
	}

	private List<List<String[]>> changeIntSetToStringSet() {
		if (result_int == null) {
			return null;
		}
		List<List<String[]>> list_result = new ArrayList<List<String[]>>();
		for (List<Integer> set : result_int) {
			List<String[]> list_result_set = new ArrayList<String[]>();
			for (int i : set) {
				list_result_set.add(cells.get(i));
			}
			Collections.sort(list_result_set, new Comparator<String[]>() {

				public int compare(String[] o1, String[] o2) {
					// TODO Auto-generated method stub
					return o1[tarLine].compareTo(o2[tarLine]);
				}
			});
			list_result.add(list_result_set);
		}
		Collections.sort(list_result, new Comparator<List<String[]>>() {

			public int compare(List<String[]> o1, List<String[]> o2) {
				// TODO Auto-generated method stub
				return o2.size() - o1.size();
			}

		});
		return list_result;
	}

	private void process_all() {
		list_seg = getSegmentList();
		result_int = cluster();
		result_all = changeIntSetToStringSet();
	}

	public List<List<String[]>> getResult_all() {
		if (result_all == null) {
			process_all();
		}
		return result_all;
	}

	private void process_original() {
		if (result_int == null) {
			process_all();
		}
		result_original = new ArrayList<String[]>();
		for (List<Integer> set : result_int) {
			int originalIndex = -1;
			String originalTime = "0000-00-00";
			if (set.size() == 1) {
				break;
			}
			for (int i : set) {
				String time = Time.convert(cells.get(i)[timeLine]);
				if (Time.compare(time, originalTime) > 0) {
					originalIndex = i;
					originalTime = cells.get(i)[timeLine];
				}
			}
			result_original.add(cells.get(originalIndex));
		}
	}

	public List<String[]> getResult_original() {
		if (result_original == null) {
			process_original();
		}
		return result_original;
	}

	public static void main(String[] args) {
		Cluster cluster = new Cluster();
		System.out.println(cluster.getSim(cluster.analysis.parse("必有路也许也就只"),
				cluster.analysis.parse("行约时候了他也许就能")));
	}
}
