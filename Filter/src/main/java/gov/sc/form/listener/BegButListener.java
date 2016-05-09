package gov.sc.form.listener;

import org.apache.log4j.Logger;

import gov.sc.file.ReadFile;
import gov.sc.file.WriteFile;
import gov.sc.filter.Cluster;
import gov.sc.form.Form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		/**
		 * Logger for this class
		 */
		private static final Logger logger = Logger
				.getLogger(HandleThread.class);

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
			JProgressBar proBar = form.progressbar;
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

			ReadFile read = new ReadFile(reFile);
			LinkedHashMap<Integer, List<String>> map;
			int proBarStep;
			try {
				map = read.getCells();
				proBarStep = map.size();
				proBar.setMaximum(proBarStep * 4);
				proBar.setValue(proBarStep);
			} catch (Exception e) {
				logger.info(e);
				form.progressbar.setString("读取文件失败！！！");
				JOptionPane.showMessageDialog(null, "读取文件失败");
				return;
			}
			List<List<String>> reList;
			Cluster cluster = new Cluster();
			try {
				reList = cluster.getResultOfClusterAndSort(map, tarCol
						.toUpperCase().charAt(0) - 64);
				proBar.setValue(proBarStep * 2);
			} catch (Exception e) {
				logger.info(e);
				form.progressbar.setString("过滤数据失败！！！");
				JOptionPane.showMessageDialog(null, "过滤数据失败");
				return;
			}
			WriteFile write = new WriteFile();
			boolean isSucceed = true;
			try {
				write.setFile(reFile.replace(".xls", "(过滤后所有数据).xls"));
				write.write(reList);
				proBar.setValue(proBarStep * 3);
			} catch (Exception e) {
				logger.info(e);
				form.progressbar.setString("过滤后所有数据写入失败！！！");
				JOptionPane.showMessageDialog(null, "过滤后所有数据写入失败");
				isSucceed = false;
			}
			try {
				write.setFile(reFile.replace(".xls", "(过滤后统计数据).xls"));
				reList = cluster.getResultOfCountAndTime(tarTim.toUpperCase()
						.charAt(0) - 64);
				write.write(reList);
				proBar.setValue(proBarStep * 4);
			} catch (Exception e) {
				logger.info(e);
				form.progressbar.setString("过滤后统计数据写入失败！！！");
				JOptionPane.showMessageDialog(null, "过滤后统计数据写入写入失败");
				isSucceed = false;
			}
			if (isSucceed) {
				JOptionPane.showMessageDialog(null, "解析成功");
				form.progressbar.setString("解析成功！！！");
				logger.info("succeed");
			} else {
				form.progressbar.setString("解析失败！！！");
				JOptionPane.showMessageDialog(null, "解析失败");
			}
			begBut.setEnabled(true);
			form.progressbar.setValue(0);
		}

	}

	public void actionPerformed(ActionEvent e) {
		HandleThread ht = new HandleThread(form);
		ht.start();
	}
}
