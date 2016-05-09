package gov.sc.filter;

import gov.sc.file.ReadFile;
import gov.sc.file.WriteFile;

import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JProgressBar;

public class Handler{
	private JProgressBar proBar;
	private String file;
	private String tarCol;
	private String tarTim;

	public String getTarTim() {
		return tarTim;
	}

	public void setTarTim(String tarTim) {
		this.tarTim = tarTim;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getTarCol() {
		return tarCol;
	}

	public void setTarCol(String tarCol) {
		this.tarCol = tarCol;
	}

	public JProgressBar getProBar() {
		return proBar;
	}

	public void setProBar(JProgressBar proBar) {
		this.proBar = proBar;
	}

	public Handler(String file, String tarCol, String tarTim) {
		this.file = file;
		this.tarCol = tarCol;
		this.tarTim = tarTim;

	}

	synchronized public boolean handle() throws Exception {
		ReadFile read = new ReadFile(file);
		LinkedHashMap<Integer, List<String>> map = read.getCells();
		int value = map.size() + proBar.getMinimum();
		int rowLength = proBar.getMaximum() / 4;
		Cluster cluster = new Cluster();
		List<List<String>> reList = cluster.getResultOfClusterAndSort(map,
				tarCol.toUpperCase().charAt(0) - 64);
		value += rowLength;
		proBar.setValue(value * 2);

		WriteFile write = new WriteFile(file.replace(".xls", "(过滤后所有数据).xls"));
		write.setFile(file.replace(".xls", "(过滤后所有数据).xls"));
		boolean isALlSuc = write.write(reList);
		value += rowLength;
		proBar.setValue(value * 3);
		write.setFile(file.replace(".xls", "(过滤后统计数据).xls"));
		reList = cluster
				.getResultOfCountAndTime(tarTim.toUpperCase().charAt(0) - 64);
		boolean isStaSuc = write.write(reList);
		proBar.setValue(value * 4);
		if (isALlSuc && isStaSuc) {
			proBar.setValue(0);
			return true;
		}
		proBar.setValue(0);
		return false;
	}
}
