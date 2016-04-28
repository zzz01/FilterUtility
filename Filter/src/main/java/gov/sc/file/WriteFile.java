package gov.sc.file;

import java.util.List;

/**
 * 该类继承自File类
 * 
 * @author Kevin
 *
 */
public class WriteFile extends File {

	private String targetFile;

	/**
	 * @param targetFile
	 */
	public WriteFile(String targetFile) {
		this.targetFile = targetFile;
	}

	/**
	 * 
	 */
	public WriteFile() {
		// TODO Auto-generated constructor stub
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	/**
	 * 该方法用于将目标list写入targetFile中
	 * 
	 * @param list
	 *            list中的每一个元素都是String数组，表示excel中的一行数据
	 * @return 如果写入成功返回true，如果写入失败，返回false
	 */
	public boolean write(List<String[]> list) {
		return false;
	}
}
