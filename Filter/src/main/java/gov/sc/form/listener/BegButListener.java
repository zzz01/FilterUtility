package gov.sc.form.listener;

import gov.sc.file.ReadFile;
import gov.sc.file.WriteFile;
import gov.sc.filter.Cluster;
import gov.sc.form.Form;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class BegButListener implements ActionListener {

	private Form form;

	/**
	 * @param form
	 */
	public BegButListener(Form form) {
		this.form = form;
	}

	static class HandleThread extends Thread {

		private Form form;

		/**
		 * @param form
		 */
		public HandleThread(Form form) {
			this.form = form;
		}

		@Override
		public void run() {
			JButton begBut = form.begBut;
			String reFile = form.srcPthTxtFiled.getText().trim();
			String tarCol = form.tarColTxtFiled.getText().trim();
			String tarTim = form.tarTimTxtFiled.getText().trim();
			// TODO Auto-generated method stub
			if (reFile == "") {
				JOptionPane.showMessageDialog(null, "请选择Excel文件");
				return;
			}
			if (tarCol == "") {
				JOptionPane.showMessageDialog(null, "请输入列标");
				return;
			}

			try {
				ReadFile read = new ReadFile(reFile);
				LinkedHashMap<Integer, List<String>> map = read.getCells();
				int value = map.size();
				form.progressbar.setMaximum(value * 4);
				form.progressbar.setValue(value);
				Cluster cluster = new Cluster();
				List<List<String>> reList = cluster.getResultOfClusterAndSort(
						map, tarCol.toUpperCase().charAt(0) - 64);
				form.progressbar.setValue(value * 2);
				WriteFile write = new WriteFile(reFile.replace(".xls", "(过滤后所有数据).xls"));
				write.setFile(reFile.replace(".xls", "(过滤后所有数据).xls"));
				boolean isALlSuc = write.write(reList);
				form.progressbar.setValue(value * 3);
				write.setFile(reFile.replace(".xls", "(过滤后统计数据).xls"));
				reList = cluster.getResultOfCountAndTime(tarTim.toUpperCase()
						.charAt(0) - 64);
				boolean isStaSuc = write.write(reList);
				form.progressbar.setValue(value * 4);
				if (isALlSuc && isStaSuc) {
					JOptionPane.showMessageDialog(null, "解析成功");
					form.progressbar.setString("解析成功！！！");
					return;
				}
				form.progressbar.setString("解析失败！！！");
				JOptionPane.showMessageDialog(null, "解析失败");
				begBut.setEnabled(true);
				form.progressbar.setValue(0);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		HandleThread ht = new HandleThread(form);
		ht.start();
	}
}
