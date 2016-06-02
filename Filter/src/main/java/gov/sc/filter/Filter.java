package gov.sc.filter;

import org.apache.log4j.Logger;

import gov.sc.file.ReadFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Filter.class);

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String file = "D:\\数据\\政法-法院-政法-检察-政法-公安-政法-司法.xls";
		ReadFile rf = new ReadFile(file);
		List<String[]> cells = rf.getCells();
		Cluster cluster = new Cluster(cells,2,3);
		List<List<String[]>> result = cluster.getResult_all();
		logger.info("总共有"+result.size()+"个类");
		Collections.sort(result, new Comparator<List<String[]>>() {

			public int compare(List<String[]> o1, List<String[]> o2) {
				// TODO Auto-generated method stub
				return o2.size()-o1.size();
			}

		});
		for (List<String[]> set : result) {
			for (String[] row : set) {
				logger.info(row[2]);
			}
			logger.info("");
		}
	}
}
