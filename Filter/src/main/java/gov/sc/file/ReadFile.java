package gov.sc.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 该类继承自File类
 * 
 * @author Kevin
 *
 */
public class ReadFile {

	private String file;
	private LinkedHashMap<Integer, List<String>> cells;

	/**
	 * @param file
	 */
	public ReadFile(String file) {
		this.file = file;
	}

	/**
	 * 该方法返回从file中读取得到的目标列数据,每一个cell的数据都将保存为一个TargetCell对象，并保存在一个List中。
	 * 
	 * @param column_num
	 *            目标列的列号
	 * @return 返回得到的list
	 */
	public List<String> readTarCells(String column_num) {
		return null;
	}

	/**
	 * 该方法返回从file中读取得到的全部数据,每一行数据保存在一个String数组中，并将所有数组保存到一个list中
	 * 
	 * @return 返回得到的list
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readCells() throws FileNotFoundException, IOException {
		cells = new LinkedHashMap<Integer, List<String>>();
		Workbook workbook;
		if (file.endsWith(".xls")) {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		}
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		int colNum = sheet.getRow(0).getPhysicalNumberOfCells();
		for (int i = 0; i < rowNum; i++) {
			List<String> rowStr = new ArrayList<String>();
			for (int j = 0; j < colNum; j++) {
				rowStr.add(sheet.getRow(i).getCell(j).toString());

			}
			cells.put(i, rowStr);
		}
		workbook.close();
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public LinkedHashMap<Integer, List<String>> getCells()
			throws FileNotFoundException, IOException {
		if (cells == null) {
			readCells();
		}
		return cells;
	}
}
