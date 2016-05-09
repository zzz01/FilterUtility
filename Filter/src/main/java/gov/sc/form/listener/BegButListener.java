package gov.sc.form.listener;

import gov.sc.filter.Handler;
import gov.sc.form.Form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JProgressBar progressBar = form.progressbar;
		// progressbar.setMaximum(100);
		// progressbar.setMinimum(0);
		// progressbar.setValue(50);
		// progressbar.setString("test");
		progressBar.setValue(20);
		progressBar.updateUI();
		JButton begBut = form.begBut;
		String reFile = form.srcPthTxtFiled.getText().trim();
		String tarCol = form.tarColTxtFiled.getText().trim();
		String tarTim = form.tarTimTxtFiled.getText().trim();
		if (reFile == "") {
			JOptionPane.showMessageDialog(null, "请选择Excel文件");
			return;
		}
		if (tarCol == "") {
			JOptionPane.showMessageDialog(null, "请输入列标");
			return;
		}
		Handler handle = new Handler(reFile, tarCol, tarTim);
		handle.setProBar(progressBar);
		ExecutorService executor = Executors.newCachedThreadPool();

		Future<Boolean> future = executor.submit(handle);
		boolean result = false;
		try {
			result = future.get();
		} catch (Exception e1) {

		}
		if (result) {
			JOptionPane.showMessageDialog(null, "解析成功");
			progressBar.setString("解析成功！！！");
		} else {
			progressBar.setString("解析失败！！！");
			begBut.setEnabled(true);
			JOptionPane.showMessageDialog(null, "解析失败");
		}
	}

}
