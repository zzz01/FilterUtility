package gov.sc.file;

/**
 * 该类是读写文件的父类，所有对文件进行操作的类，都需要继承这个类
 * @author Kevin
 *
 */
public class File {
	protected String sourceFile;

	/**
	 * @param fileName 文件名称，包含文件的绝对路径
	 */
	public File(String fileName) {
		this.sourceFile = fileName;
	}

	/**
	 * 无参构造方法
	 */
	public File() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return 返回文件名
	 */
	public String getSourceFile() {
		return sourceFile;
	}

	/**
	 * 设置文件名
	 * @param fileName 文件名
	 */
	public void setSourceFile(String fileName) {
		this.sourceFile = fileName;
	}

}
