package gov.sc.file;

import gov.sc.model.TargetCell;

import java.util.List;

/**
 * 该类继承自File类
 * @author Kevin
 *
 */
public class ReadFile extends File {
	/**
	 * 该方法返回从file中读取得到的目标列数据,每一个cell的数据都将保存为一个TargetCell对象，并保存在一个List中。
	 * 
	 * @param column_num
	 *            目标列的列号
	 * @return 返回得到的list
	 */
	public List<TargetCell> readTarCells(String column_num) {
		return null;
	}

	/**
	 * 该方法返回从file中读取得到的全部数据,每一行数据保存在一个String数组中，并将所有数组保存到一个list中
	 * 
	 * @return 返回得到的list
	 */
	public List<String[]> readCells() {
		return null;
	}
}
