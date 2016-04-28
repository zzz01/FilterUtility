package gov.sc.model;

/**
 * 该JavaBean描述了目标列的信息，包括rowNum和content
 * 
 * @author Kevin
 *
 */
public class TargetCell {
	private final int rowNum;
	private final String content;

	/**
	 * @param rowNum
	 *            表示行号，为final类型
	 * @param content
	 *            表示cell中包含的内容，为final类型
	 */
	public TargetCell(int rowNum, String content) {
		this.rowNum = rowNum;
		this.content = content;
	}

	public int getRowNum() {
		return rowNum;
	}

	public String getContent() {
		return content;
	}
}
